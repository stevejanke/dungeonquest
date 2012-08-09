package cardsAndDecks;

import static com.google.common.base.Preconditions.checkArgument;

public class TilePile extends AbstractDeck {

	private TilePile() {
		super();
	}

	private static TilePile instance;

	public static TilePile get() {
		if (instance == null) {
			instance = new TilePile();
		}
		return instance;
	}

	@Override
	public void addCard(Card card) {
		checkArgument(card instanceof Tile,
				"Card \"%s\": Inserting a %s into %s", card.getName(), card
						.getClass().getSimpleName(), this.getClass()
						.getSimpleName());
		super.addCard(card);
	}

}
