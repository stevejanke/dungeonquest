package main;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import cardsAndDecks.Tile;
import cardsAndDecks.TilePile;

public class LoadGamePieces extends SwingWorker<Void, Void> {

	private final ClientFactory clientFactory;

	public LoadGamePieces(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	int currentCard = 0;

	@Override
	protected Void doInBackground() throws Exception {
		String[] tileFiles = new File(getClass().getResource(
				"/resources/tiles/").toURI()).list();

		TilePile.get().setClientFactory(clientFactory);

		for (String tileFile : tileFiles) {
			Tile tile = new Tile(tileFile, new ImageIcon(getClass()
					.getResource("/resources/tiles/" + tileFile)).getImage(),
					null);
			TilePile.get().addCard(tile);
		}
		TilePile.get().shuffle();

		return null;
	}

}
