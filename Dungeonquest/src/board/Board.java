package board;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.google.common.collect.Maps;

public class Board extends JPanel {

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

	private final static int ON_SCREEN_WIDTH = 1024;

	private final int onScreenHeight;

	private Image gameBoardImage = null;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(gameBoardImage, 0, 0, ON_SCREEN_WIDTH, onScreenHeight, null);
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

		onScreenHeight = (int) ((float) ON_SCREEN_WIDTH
				/ gameBoardImage.getWidth(null) * gameBoardImage
				.getHeight(null));
		setPreferredSize(new Dimension(ON_SCREEN_WIDTH, onScreenHeight));
		setBorder(BorderFactory.createLineBorder(Color.black));

		GridLayout gridLayout = new GridLayout(NUM_ROWS, NUM_COLS);
		JPanel tileSquares = new JPanel();
		tileSquares.setOpaque(false);
		tileSquares.setLayout(gridLayout);
		tileSquares.setPreferredSize(new Dimension(NUM_COLS
				* Square.SQUARE_EDGE, NUM_ROWS * Square.SQUARE_EDGE));
		tileSquares.setMaximumSize(new Dimension(NUM_COLS * Square.SQUARE_EDGE,
				NUM_ROWS * Square.SQUARE_EDGE));
		tileSquares.setMinimumSize(new Dimension(NUM_COLS * Square.SQUARE_EDGE,
				NUM_ROWS * Square.SQUARE_EDGE));

		GroupLayout groupLayout = new GroupLayout(this);
		setLayout(groupLayout);

		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGap(50).addComponent(tileSquares));
		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				.addGap(50).addComponent(tileSquares));

		for (int r = 1; r <= NUM_ROWS; r++) {
			for (int c = 1; c <= NUM_COLS; c++) {
				final Reference loc = Reference.at(r, c);
				board.put(loc, new Square(loc));
				tileSquares.add(board.get(loc));
			}
		}

		for (int r = 1; r <= NUM_ROWS; r++) {
			for (int c = 1; c <= NUM_COLS; c++) {
				for (Direction forDirection : Direction.values()) {
					board.get(Reference.at(r, c)).setPassage(forDirection);
				}
			}
		}

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

}
