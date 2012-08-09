package cardsAndDecks;

import static com.google.common.base.Preconditions.checkArgument;

public class SearchDeck extends AbstractDeck {

	private SearchDeck() {
		super();
	}

	private static SearchDeck instance;

	public static SearchDeck get() {
		if (instance == null) {
			instance = new SearchDeck();
		}
		return instance;
	}

	@Override
	public void addCard(Card card) {
		checkArgument(card instanceof SearchCard,
				"Card \"%s\": Inserting a %s into %s", card.getName(), card
						.getClass().getSimpleName(), this.getClass()
						.getSimpleName());
		super.addCard(card);
	}

}
