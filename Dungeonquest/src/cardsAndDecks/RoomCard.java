package cardsAndDecks;

import java.awt.Image;

import cardsAndDecks.functions.CardType;

public class RoomCard extends AbstractCard {

	public RoomCard(String name, Image cardImage, CardType cardFunction) {
		super(name, cardImage, RoomDeck.get(), cardFunction);
	}

}
