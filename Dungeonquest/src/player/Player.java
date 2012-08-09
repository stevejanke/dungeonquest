package player;

import static com.google.common.base.Preconditions.checkNotNull;
import hero.Hero;
import main.ClientFactory;

import com.google.common.eventbus.Subscribe;

import events.GameEnds;
import events.GameStarts;
import events.TurnEnds;
import events.TurnStarts;

public class Player {

	private final ClientFactory clientFactory;

	private String name = null;
	private boolean currentPlayer = false;
	private boolean turnIncrementingPlayer = false;
	private boolean gameOver = false;

	private Hero hero = null;

	private Player playerOnLeft = null;

	public Player(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
		clientFactory.getEventBus().register(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(boolean currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getPlayerOnLeft() {
		return playerOnLeft;
	}

	public void setPlayerOnLeft(Player playerOnLeft) {
		this.playerOnLeft = playerOnLeft;
	}

	public boolean isTurnIncrementingPlayer() {
		return turnIncrementingPlayer;
	}

	public void setTurnIncrementingPlayer(boolean turnIncrementingPlayer) {
		this.turnIncrementingPlayer = turnIncrementingPlayer;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	@Subscribe
	public void handleGameStarts(GameStarts gameStarts) {
		checkNotNull(hero);
		gameOver = false;
		if (this.isCurrentPlayer()) {
			clientFactory.getEventBus().post(new TurnStarts(this));
		}
	}

	@Subscribe
	public void handleTurnStarts(TurnStarts turnStarts) {
		if (turnStarts.getPlayer() == this) {
			if (getHero().isAlive()) {
				setCurrentPlayer(true);
			} else {
				if (!gameOver) {
					clientFactory.getEventBus().post(
							new TurnStarts(getPlayerOnLeft()));
				}
			}
		}
	}

	@Subscribe
	public void handleGameEnds(GameEnds gameEnds) {
		gameOver = true;
	}

	@Subscribe
	public void handleTurnEnds(TurnEnds turnEnds) {
		if (isCurrentPlayer()) {
			setCurrentPlayer(false);
			clientFactory.getEventBus().post(new TurnStarts(getPlayerOnLeft()));
		}
	}
}
