package cardsAndDecks;

import cardsAndDecks.functions.CardType;

public interface Card {

	void returnToDeck();

	String getName();

	CardType getCardFunction();

}
