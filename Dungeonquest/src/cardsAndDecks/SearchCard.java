package cardsAndDecks;

import java.awt.Image;

import cardsAndDecks.functions.CardType;

public class SearchCard extends AbstractCard {

	public SearchCard(String name, Image cardImage, CardType cardFunction) {
		super(name, cardImage, SearchDeck.get(), cardFunction);
	}

}
