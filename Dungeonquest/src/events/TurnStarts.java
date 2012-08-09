package events;

import player.Player;

public class TurnStarts {

	private final Player player;

	public TurnStarts(Player player) {
		super();
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
