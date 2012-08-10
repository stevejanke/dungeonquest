package main;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import player.Player;
import board.Board;
import board.Board.Direction;
import board.Side;
import board.Square;

import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;

import events.GameEnds;
import events.GameStarts;
import events.PlayerIsOut;
import events.RegisterPlayer;
import events.TurnEnds;
import events.TurnStarts;

public class MainFrame extends JFrame {

	private int currentTurn = 0;

	private final ClientFactory clientFactory;

	private static final long serialVersionUID = 2630322136338626255L;

	private Set<Player> activePlayers = Sets.newHashSet();
	private Player activePlayer = null;

	private JMenuBar mainMenu = new JMenuBar();

	class NextTurnAction extends AbstractAction {
		private static final long serialVersionUID = 8755398648792203349L;

		public NextTurnAction() {
			super("Turn Ends");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			clientFactory.getEventBus().post(new TurnEnds());
		}
	}

	private AbstractAction searchAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			clientFactory.getEventBus().post(new TurnEnds());
		}

		{
			putValue(NAME, "Search");
			putValue(MNEMONIC_KEY, KeyEvent.VK_R);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R,
					ActionEvent.SHIFT_MASK));
		}
	};

	private AbstractAction skipTurnAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			clientFactory.getEventBus().post(new TurnEnds());
		}

		{
			putValue(NAME, "Skip Turn");
			putValue(MNEMONIC_KEY, KeyEvent.VK_K);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_K,
					ActionEvent.SHIFT_MASK));
		}
	};

	private void moveActivePlayer(Direction direction) {
		final Square currentSquare = activePlayer.getHero().getSquare();
		final Square nextSquare = Board.get().getSquare(
				currentSquare.getReference().getAdjacent(direction).get());

		currentSquare.setHero(null);
		nextSquare.setHero(activePlayer.getHero());

		activePlayer.getHero().setSquare(nextSquare);

		if ((!nextSquare.getReference().isFixedReference())
				&& (nextSquare.getTile() == null)) {
			nextSquare.setTile(direction.getOpposite());
		}

		Board.get().repaint();
		
		clientFactory.getEventBus().post(new TurnEnds());
	}

	private AbstractAction moveNorthAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			moveActivePlayer(Direction.NORTH);
		}

		{
			putValue(NAME, "North");
			putValue(MNEMONIC_KEY, KeyEvent.VK_N);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N,
					ActionEvent.SHIFT_MASK));
		}
	};

	private AbstractAction moveSouthAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			moveActivePlayer(Direction.SOUTH);
		}

		{
			putValue(NAME, "South");
			putValue(MNEMONIC_KEY, KeyEvent.VK_S);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
					ActionEvent.SHIFT_MASK));
		}
	};

	private AbstractAction moveEastAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			moveActivePlayer(Direction.EAST);
		}

		{
			putValue(NAME, "East");
			putValue(MNEMONIC_KEY, KeyEvent.VK_E);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,
					ActionEvent.SHIFT_MASK));
		}
	};

	private AbstractAction moveWestAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			moveActivePlayer(Direction.WEST);
		}

		{
			putValue(NAME, "West");
			putValue(MNEMONIC_KEY, KeyEvent.VK_W);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W,
					ActionEvent.SHIFT_MASK));
		}
	};

	public MainFrame(ClientFactory clientFactory, String title) {
		super(title);
		this.clientFactory = clientFactory;

		setResizable(false);

		JMenu moveMenu = new JMenu("Action");
		moveMenu.setMnemonic(KeyEvent.VK_M);

		moveMenu.add(moveNorthAction);
		moveMenu.add(moveSouthAction);
		moveMenu.add(moveEastAction);
		moveMenu.add(moveWestAction);
		moveMenu.addSeparator();
		moveMenu.add(searchAction);
		moveMenu.addSeparator();
		moveMenu.add(skipTurnAction);

		mainMenu.add(moveMenu);

		setJMenuBar(mainMenu);

		getContentPane().add(Board.get());
		clientFactory.getEventBus().register(this);

		currentTurn = 0;
	}

	@Subscribe
	public void handleGameStarts(GameStarts gameStarts) {
		Board.get().repaint();
	}

	@Subscribe
	public void handleGameEnds(GameEnds gameEnds) {
		moveNorthAction.setEnabled(false);
		moveSouthAction.setEnabled(false);
		moveEastAction.setEnabled(false);
		moveWestAction.setEnabled(false);
		searchAction.setEnabled(false);
		skipTurnAction.setEnabled(false);
	}

	@Subscribe
	public void handleTurnStarts(TurnStarts turnStarts) {
		if (turnStarts.getPlayer().isTurnIncrementingPlayer()) {
			if (currentTurn == 26) {
				clientFactory.getEventBus().post(new GameEnds(null));
				return;
			} else {
				currentTurn++;
			}
		}
		if (turnStarts.getPlayer().getHero().isAlive()) {
			checkNotNull(turnStarts.getPlayer().getHero().getSquare());
			activePlayer = turnStarts.getPlayer();

			moveNorthAction
					.setEnabled(activePlayer.getHero().getSquare()
							.getPassage(Direction.NORTH).isPresent()
							&& (activePlayer
									.getHero()
									.getSquare()
									.getPassage(Direction.NORTH)
									.get()
									.getSide(
											activePlayer.getHero().getSquare()
													.getReference()) != Side.WALL)
							&& (activePlayer
									.getHero()
									.getSquare()
									.getPassage(Direction.NORTH)
									.get()
									.getSide(
											activePlayer
													.getHero()
													.getSquare()
													.getReference()
													.getAdjacent(
															Direction.NORTH)
													.get()) != Side.WALL));

			moveSouthAction
					.setEnabled(activePlayer.getHero().getSquare()
							.getPassage(Direction.SOUTH).isPresent()
							&& (activePlayer
									.getHero()
									.getSquare()
									.getPassage(Direction.SOUTH)
									.get()
									.getSide(
											activePlayer.getHero().getSquare()
													.getReference()) != Side.WALL)
							&& (activePlayer
									.getHero()
									.getSquare()
									.getPassage(Direction.SOUTH)
									.get()
									.getSide(
											activePlayer
													.getHero()
													.getSquare()
													.getReference()
													.getAdjacent(
															Direction.SOUTH)
													.get()) != Side.WALL));

			moveEastAction
					.setEnabled(activePlayer.getHero().getSquare()
							.getPassage(Direction.EAST).isPresent()
							&& (activePlayer
									.getHero()
									.getSquare()
									.getPassage(Direction.EAST)
									.get()
									.getSide(
											activePlayer.getHero().getSquare()
													.getReference()) != Side.WALL)
							&& (activePlayer
									.getHero()
									.getSquare()
									.getPassage(Direction.EAST)
									.get()
									.getSide(
											activePlayer
													.getHero()
													.getSquare()
													.getReference()
													.getAdjacent(Direction.EAST)
													.get()) != Side.WALL));

			moveWestAction
					.setEnabled(activePlayer.getHero().getSquare()
							.getPassage(Direction.WEST).isPresent()
							&& (activePlayer
									.getHero()
									.getSquare()
									.getPassage(Direction.WEST)
									.get()
									.getSide(
											activePlayer.getHero().getSquare()
													.getReference()) != Side.WALL)
							&& (activePlayer
									.getHero()
									.getSquare()
									.getPassage(Direction.WEST)
									.get()
									.getSide(
											activePlayer
													.getHero()
													.getSquare()
													.getReference()
													.getAdjacent(Direction.WEST)
													.get()) != Side.WALL));

		}
	}

	@Subscribe
	public void handlePlayerIsOut(PlayerIsOut playerIsOut) {
		activePlayers.remove(playerIsOut.getPlayer());
		if (activePlayers.isEmpty()) {
			clientFactory.getEventBus().post(
					new GameEnds(playerIsOut.getPlayer()));
		}
	}

	@Subscribe
	public void handleRegisterPlayer(RegisterPlayer registerPlayer) {
		activePlayers.add(registerPlayer.getPlayer());
	}

}
