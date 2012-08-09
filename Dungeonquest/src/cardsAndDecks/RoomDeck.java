package cardsAndDecks;

import static com.google.common.base.Preconditions.checkArgument;

public class RoomDeck extends AbstractDeck {

	private RoomDeck() {
		super();
	}

	private static RoomDeck instance;

	public static RoomDeck get() {
		if (instance == null) {
			instance = new RoomDeck();
		}
		return instance;
	}

	@Override
	public void addCard(Card card) {
		checkArgument(card instanceof RoomCard,
				"Card \"%s\": Inserting a %s into %s", card.getName(), card
						.getClass().getSimpleName(), this.getClass()
						.getSimpleName());
		super.addCard(card);
	}

}
