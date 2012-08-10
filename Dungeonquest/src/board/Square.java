package board;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import hero.Hero;

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
	public static final int HERO_INDENT = 12;

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
			Graphics2D gTile = (Graphics2D) g.create();
			switch (getTile().getEntryDirection()) {
			case EAST:
				gTile.rotate(Math.toRadians(90), SQUARE_EDGE / 2,
						SQUARE_EDGE / 2);
				break;
			case WEST:
				gTile.rotate(Math.toRadians(-90), SQUARE_EDGE / 2,
						SQUARE_EDGE / 2);
				break;
			case SOUTH:
				gTile.rotate(Math.toRadians(180), SQUARE_EDGE / 2,
						SQUARE_EDGE / 2);
				break;
			case NORTH:
				break;
			}
			gTile.drawImage(getTile().getCardImage(), 0, 0, SQUARE_EDGE,
					SQUARE_EDGE, null);
			gTile.dispose();
		}
		if (getHero() != null) {
			Graphics2D gHero = (Graphics2D) g.create();
			gHero.drawImage(getHero().getHeroImage(), HERO_INDENT, HERO_INDENT,
					SQUARE_EDGE - 2 * HERO_INDENT, SQUARE_EDGE - 2
							* HERO_INDENT, null);
			gHero.dispose();
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
