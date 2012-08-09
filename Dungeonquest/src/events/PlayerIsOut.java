package events;

import player.Player;

public class PlayerIsOut {

	private final Player player;

	public PlayerIsOut(Player player) {
		super();
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
