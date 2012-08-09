package main;

import static javax.swing.SwingUtilities.invokeLater;

import javax.swing.JFrame;

import events.GameStarts;

public class DqMain {

	private static final ClientFactory clientFactory = new ClientFactoryImpl();

	private static final ConfigureGame configureGame = new ConfigureGame(
			clientFactory) {
		@Override
		protected void done() {
			clientFactory.getEventBus().post(new GameStarts());
		}
	};
	private static final LoadGamePieces loadGamePieces = new LoadGamePieces(
			clientFactory) {
		@Override
		protected void done() {
			//RoomDeck.get().dump();
			//TilePile.get().dump();
			configureGame.execute();
		}
	};

	private static void createAndShowGUI() {
		MainFrame mainFrame = clientFactory.getMainFrame();

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private static void loadGamePiecesAndStartGame() {
		loadGamePieces.execute();
	}

	public static void main(String[] args) {
		invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
		invokeLater(new Runnable() {
			public void run() {
				loadGamePiecesAndStartGame();
			}
		});
	}
}
