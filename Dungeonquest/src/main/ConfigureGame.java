package main;

import hero.Hero;

import javax.swing.SwingWorker;

import player.Player;
import board.Board;
import events.RegisterPlayer;

public class ConfigureGame extends SwingWorker<Void, Void> {

	private final ClientFactory clientFactory;

	public ConfigureGame(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	protected Void doInBackground() throws Exception {
		Player bob = new Player(clientFactory);
		// Player tom = new Player(clientFactory);
		// Player sam = new Player(clientFactory);

		bob.setName("Bob");
		// tom.setName("Tom");
		// sam.setName("Sam");

		bob.setPlayerOnLeft(bob);

		// bob.setPlayerOnLeft(tom);
		// tom.setPlayerOnLeft(sam);
		// sam.setPlayerOnLeft(bob);

		bob.setCurrentPlayer(true);
		bob.setTurnIncrementingPlayer(true);

		bob.setHero(Hero.named("Adoran", "Adoran", "The Gnasher").setStrenght(6)
				.setArmour(5).setLuck(7).setMaxHp(12).build());
		// tom.setHero(Hero.named("Yak", "Yakum", "The Grinder").setStrenght(6)
		// .setArmour(5).setLuck(7).setMaxHp(12).build());
		// sam.setHero(Hero.named("Zem", "Zemoid",
		// "The Scratcher").setStrenght(6)
		// .setArmour(5).setLuck(7).setMaxHp(12).build());

		bob.getHero().reset();
		// tom.getHero().reset();
		// sam.getHero().reset();

		clientFactory.getEventBus().post(new RegisterPlayer(bob));
		// clientFactory.getEventBus().post(new RegisterPlayer(tom));
		// clientFactory.getEventBus().post(new RegisterPlayer(sam));

		Board.get().getSquare(Board.TOWER_NW).setHero(bob.getHero());
		// Board.get().getSquare(Board.TOWER_NE).setHero(tom.getHero());
		// Board.get().getSquare(Board.TOWER_SE).setHero(sam.getHero());

		bob.getHero().setSquare(Board.get().getSquare(Board.TOWER_NW));
		// tom.getHero().setSquare(Board.get().getSquare(Board.TOWER_NE));
		// sam.getHero().setSquare(Board.get().getSquare(Board.TOWER_SE));

		return null;
	}

}
