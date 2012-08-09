package board;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import hero.Hero;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import board.Board.Direction;
import cardsAndDecks.Tile;
import cardsAndDecks.TilePile;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

public class Square extends JLabel {

	private static final long serialVersionUID = 2886677788368889113L;

	public static final int SQUARE_EDGE = 71;

	private Tile tile = null;
	private Hero hero = null;
	private final Reference reference;

	private Map<Board.Direction, Optional<Passage>> passages = Maps
			.newEnumMap(Board.Direction.class);

	private final Optional<Passage> noPassage = Optional.absent();

	public void setPassage(Board.Direction forDirection) {
		if (reference.getAdjacent(forDirection).isPresent()) {
			passages.put(
					forDirection,
					Optional.of(new Passage(reference, reference.getAdjacent(
							forDirection).get())));
		} else {
			passages.put(forDirection, noPassage);
		}
	}

	public Square(Reference reference) {
		super();
		checkNotNull(reference);
		this.reference = reference;
		setSize(SQUARE_EDGE, SQUARE_EDGE);
		setPreferredSize(new Dimension(SQUARE_EDGE, SQUARE_EDGE));
		setMinimumSize(new Dimension(SQUARE_EDGE, SQUARE_EDGE));
		setMaximumSize(new Dimension(SQUARE_EDGE, SQUARE_EDGE));
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getTile() != null) {
			// {
			// Graphics2D g1 = (Graphics2D) g.create();
			// g1.setColor(Color.DARK_GRAY);
			// g1.drawRect(0, 0, SQUARE_EDGE, SQUARE_EDGE);
			// g1.dispose();
			// }

			{
				Graphics2D g2 = (Graphics2D) g.create();
				switch (tile.getEntryDirection()) {
				case EAST:
					g2.rotate(Math.toRadians(90), SQUARE_EDGE / 2,
							SQUARE_EDGE / 2);
					break;
				case WEST:
					g2.rotate(Math.toRadians(-90), SQUARE_EDGE / 2,
							SQUARE_EDGE / 2);
					break;
				case SOUTH:
					g2.rotate(Math.toRadians(180), SQUARE_EDGE / 2,
							SQUARE_EDGE / 2);
					break;
				case NORTH:
					break;
				}
				g2.drawImage(tile.getCardImage(), 0, 0, SQUARE_EDGE,
						SQUARE_EDGE, null);
				g2.dispose();
			}
			{
				Graphics2D g4 = (Graphics2D) g.create();
				if (getTile() != null) {
					g4.setColor(Color.RED);
					switch (tile.getSideFacingDirection(Direction.NORTH)) {
					case DOOR:
						g4.drawString("nDD", 35, 8);
						break;
					case WALL:
						g4.drawString("nWW", 35, 8);
						break;
					case PORTCULLIS:
						g4.drawString("nPP", 35, 8);
						break;
					case OPENING:
						g4.drawString("n--", 35, 8);
						break;
					}
					switch (tile.getSideFacingDirection(Direction.SOUTH)) {
					case DOOR:
						g4.drawString("sDD", 35, 63);
						break;
					case WALL:
						g4.drawString("sWW", 35, 63);
						break;
					case PORTCULLIS:
						g4.drawString("sPP", 35, 63);
						break;
					case OPENING:
						g4.drawString("s--", 35, 63);
						break;
					}
					switch (tile.getSideFacingDirection(Direction.EAST)) {
					case DOOR:
						g4.drawString("eDD", 55, 35);
						break;
					case WALL:
						g4.drawString("eWW", 55, 35);
						break;
					case PORTCULLIS:
						g4.drawString("ePP", 55, 35);
						break;
					case OPENING:
						g4.drawString("e--", 55, 35);
						break;
					}
					switch (tile.getSideFacingDirection(Direction.WEST)) {
					case DOOR:
						g4.drawString("wDD", 8, 35);
						break;
					case WALL:
						g4.drawString("wWW", 8, 35);
						break;
					case PORTCULLIS:
						g4.drawString("wPP", 8, 35);
						break;
					case OPENING:
						g4.drawString("w--", 8, 35);
						break;
					}
				}
				g4.dispose();
			}
		}
		{
			Graphics2D g3 = (Graphics2D) g.create();
			if (getHero() != null) {
				g3.setColor(Color.YELLOW);
				g3.drawString(getHero().getShortName(), 25, 25);
			}
			g3.dispose();
		}
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
		this.repaint();
	}

	public Reference getReference() {
		return reference;
	}

	public Optional<Passage> getPassage(Direction forDirection) {
		return passages.get(forDirection);
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Direction entryFrom) {
		checkState(!getReference().isFixedReference());
		tile = (Tile) TilePile.get().drawCard();
		tile.setEntryDirection(entryFrom);
		if (passages.get(Direction.NORTH).isPresent()) {
			passages.get(Direction.NORTH)
					.get()
					.setSide(tile.getSideFacingDirection(Direction.NORTH),
							getReference());
		}
		if (passages.get(Direction.SOUTH).isPresent()) {
			passages.get(Direction.SOUTH)
					.get()
					.setSide(tile.getSideFacingDirection(Direction.SOUTH),
							getReference());
		}
		if (passages.get(Direction.EAST).isPresent()) {
			passages.get(Direction.EAST)
					.get()
					.setSide(tile.getSideFacingDirection(Direction.EAST),
							getReference());
		}
		if (passages.get(Direction.WEST).isPresent()) {
			passages.get(Direction.WEST)
					.get()
					.setSide(tile.getSideFacingDirection(Direction.WEST),
							getReference());
		}
	}

	@Override
	public String toString() {
		if (getReference() == Board.TOWER_NE) {
			return "Northeast Tower Room [" + reference + "]";
		} else if (getReference() == Board.TOWER_NW) {
			return "Northwest Tower Room [" + reference + "]";
		} else if (getReference() == Board.TOWER_SE) {
			return "Southeast Tower Room [" + reference + "]";
		} else if (getReference() == Board.TOWER_SW) {
			return "Southwest Tower Room [" + reference + "]";
		} else if (getReference() == Board.TREASURE_N) {
			return "North Treasure Room [" + reference + "]";
		} else if (getReference() == Board.TREASURE_S) {
			return "South Treasure Room [" + reference + "]";
		} else {
			return "Square [" + reference + "]";
		}
	}

}
