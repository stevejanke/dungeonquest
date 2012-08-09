package cardsAndDecks.functions;

import cardsAndDecks.qualities.HasValue;

public class CardTypeTreasure implements CardType, HasValue {

	private final int value;

	private CardTypeTreasure(int value) {
		super();
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

}
