package board;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.google.common.collect.Maps;

public class Board extends JPanel implements MouseListener, MouseWheelListener,
		MouseMotionListener, Observer {

	public class Zoomer extends Observable {

		private boolean enabled = false;
		private float zoomLevel = 1.0f;

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public float getZoomLevel() {
			return zoomLevel;
		}

		public void adjustZoomLevel(float zoomMovement) {
			if (isEnabled()) {
				float oldZoomLevel = zoomLevel;
				this.zoomLevel = max(min(this.zoomLevel + zoomMovement, 3.0f),
						1.0f);
				if (oldZoomLevel != zoomLevel) {
					setChanged();
				}
				notifyObservers();
			}
		}

	}

	private final Zoomer zoomer = new Zoomer();

	public enum Direction {
		NORTH, SOUTH, EAST, WEST;

		public Direction getOpposite() {
			Direction result = null;
			switch (this) {
			case EAST:
				result = WEST;
				break;
			case NORTH:
				result = SOUTH;
				break;
			case SOUTH:
				result = NORTH;
				break;
			case WEST:
				result = EAST;
				break;
			}
			return result;
		}
	};

	public static final int MIN_ROW = 1;
	public static final int MAX_ROW = 10;
	public static final int MIN_COL = 1;
	public static final int MAX_COL = 13;

	public static final int NUM_ROWS = MAX_ROW - MIN_ROW + 1;
	public static final int NUM_COLS = MAX_COL - MIN_COL + 1;

	private final int VIEW_WINDOW_WIDTH = 1024;

	private final int VIEW_WINDOW_HEIGHT;

	private int gameBoardWidth = 0;
	private int gameBoardHeight = 0;

	private Image gameBoardImage = null;
	private int gameBoardStartX = 0;
	private int gameBoardStartY = 0;
	private int gameBoardUnscaledCentreX = 0;
	private int gameBoardUnscaledCentreY = 0;

	public static final int SQUARE_EDGE = 71;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gameBoardImage, 0, 0, VIEW_WINDOW_WIDTH,
				VIEW_WINDOW_HEIGHT, gameBoardStartX, gameBoardStartY,
				gameBoardStartX + gameBoardWidth, gameBoardStartY
						+ gameBoardHeight, null);
		if (!board.values().isEmpty()) {
			final int zoomedSquareEdge = (int) (SQUARE_EDGE * zoomer
					.getZoomLevel());
			for (Square square : board.values()) {
				if (square.getTile() != null) {
					final int adjustedX = (int) (((square.getReference()
							.getCol() - 1) * SQUARE_EDGE + 50) * zoomer
								.getZoomLevel()) - (int) (gameBoardStartX / zoomer
										.getZoomLevel());
					final int adjustedY = (int) (((square.getReference()
							.getRow() - 1) * SQUARE_EDGE + 50) * zoomer
								.getZoomLevel());

					System.out.println("zl " + zoomer.getZoomLevel());
					System.out.println("gameBoardStartX " + gameBoardStartX);
					System.out.println("adjustedX " + adjustedX);
					System.out.println("gameBoardStartY " + gameBoardStartY);
					System.out.println("adjustedY " + adjustedY);

					Graphics2D gTile = (Graphics2D) g.create();
					// switch (square.getTile().getEntryDirection()) {
					// case EAST:
					// gTile.rotate(Math.toRadians(90), SQUARE_EDGE / 2,
					// SQUARE_EDGE / 2);
					// break;
					// case WEST:
					// gTile.rotate(Math.toRadians(-90), SQUARE_EDGE / 2,
					// SQUARE_EDGE / 2);
					// break;
					// case SOUTH:
					// gTile.rotate(Math.toRadians(180), SQUARE_EDGE / 2,
					// SQUARE_EDGE / 2);
					// break;
					// case NORTH:
					// break;
					// }
					g.drawImage(square.getTile().getCardImage(), adjustedX,
							adjustedY, adjustedX + zoomedSquareEdge, adjustedY
									+ zoomedSquareEdge, 0, 0, square.getTile()
									.getCardImage().getWidth(null), square
									.getTile().getCardImage().getHeight(null),
							null);
					gTile.dispose();
				}
			}
		}
	}

	private static final long serialVersionUID = 7122598008306748396L;

	private Map<Reference, Square> board = Maps.newHashMap();

	public static Reference TOWER_NW;
	public static Reference TOWER_NE;
	public static Reference TOWER_SE;
	public static Reference TOWER_SW;

	public static Reference TREASURE_N;
	public static Reference TREASURE_S;

	private Board() {
		Reference.buildReferences(NUM_ROWS, NUM_COLS);

		TOWER_NW = Reference.at(MIN_ROW, MIN_COL);
		TOWER_NW.setFixedReference(true);

		TOWER_NE = Reference.at(MIN_ROW, MAX_COL);
		TOWER_NE.setFixedReference(true);

		TOWER_SE = Reference.at(MAX_ROW, MAX_COL);
		TOWER_SE.setFixedReference(true);

		TOWER_SW = Reference.at(MAX_ROW, MIN_COL);
		TOWER_SW.setFixedReference(true);

		TREASURE_N = Reference.at(5, 7);
		TREASURE_N.setFixedReference(true);

		TREASURE_S = Reference.at(6, 7);
		TREASURE_S.setFixedReference(true);

		this.gameBoardImage = new ImageIcon(getClass().getResource(
				"/resources/gameboard.png")).getImage();

		VIEW_WINDOW_HEIGHT = (int) ((float) VIEW_WINDOW_WIDTH
				/ gameBoardImage.getWidth(null) * gameBoardImage
				.getHeight(null));
		setPreferredSize(new Dimension(VIEW_WINDOW_WIDTH, VIEW_WINDOW_HEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.black));

		for (int r = 1; r <= NUM_ROWS; r++) {
			for (int c = 1; c <= NUM_COLS; c++) {
				final Reference loc = Reference.at(r, c);
				board.put(loc, new Square(loc));
			}
		}

		for (int r = 1; r <= NUM_ROWS; r++) {
			for (int c = 1; c <= NUM_COLS; c++) {
				for (Direction forDirection : Direction.values()) {
					board.get(Reference.at(r, c)).setPassage(forDirection);
				}
			}
		}

		gameBoardWidth = gameBoardImage.getWidth(null);
		gameBoardHeight = gameBoardImage.getHeight(null);

		gameBoardUnscaledCentreX = gameBoardWidth / 3;
		gameBoardUnscaledCentreY = gameBoardHeight / 3;

		mapCoordinates();

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);

		zoomer.addObserver(this);
	}

	private static Board instance = null;

	public static Board get() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}

	public void initialize() {

	}

	public Zoomer getZoomer() {
		return zoomer;
	}

	public Square getSquare(int row, int col) {
		return getSquare(Reference.at(row, col));
	}

	public Square getSquare(Reference location) {
		checkNotNull(location);
		if (!board.containsKey(location)) {
			board.put(location, new Square(location));
		}
		return board.get(location);
	}

	private void mapCoordinates() {
		gameBoardWidth = (int) (gameBoardImage.getWidth(null) * (1.0 / zoomer
				.getZoomLevel()));
		gameBoardHeight = (int) (gameBoardImage.getHeight(null) * (1.0 / zoomer
				.getZoomLevel()));

		gameBoardStartX = max(gameBoardUnscaledCentreX - gameBoardWidth / 2, 0);
		gameBoardStartY = max(gameBoardUnscaledCentreY - gameBoardHeight / 2, 0);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		gameBoardUnscaledCentreX = (int) (gameBoardImage.getWidth(null) * ((1.0f * e
				.getX()) / (1.0f * VIEW_WINDOW_WIDTH)));
		gameBoardUnscaledCentreY = (int) (gameBoardImage.getHeight(null) * ((1.0f * e
				.getY()) / (1.0f * VIEW_WINDOW_HEIGHT)));

		zoomer.adjustZoomLevel(-0.1f * e.getWheelRotation());
	}

	@Override
	public void update(Observable o, Object arg) {
		mapCoordinates();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("drag button " + e.getButton());
		System.out.println("drag x " + e.getX());
		System.out.println("drag y " + e.getY());

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("click button " + e.getButton());
		if (e.getButton() == 2) {

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("press button " + e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		zoomer.setEnabled(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		zoomer.setEnabled(false);
	}

}
