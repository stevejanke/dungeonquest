package events;

import player.Player;

public class RegisterPlayer {

	private final Player player;

	public RegisterPlayer(Player player) {
		super();
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

}
