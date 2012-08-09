package cardsAndDecks;

import java.awt.Image;

import cardsAndDecks.functions.CardType;

public abstract class AbstractCard implements Card {

	private final String name;
	private final Deck deck;
	private final CardType cardFunction;
	private final Image cardImage;

	protected AbstractCard(String name, Image cardImage, Deck deck,
			CardType cardFunction) {
		super();
		this.name = name;
		this.deck = deck;
		this.cardFunction = cardFunction;
		this.cardImage = cardImage;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void returnToDeck() {
		deck.addCard(this);
	}

	@Override
	public CardType getCardFunction() {
		return cardFunction;
	}

	public Image getCardImage() {
		return cardImage;
	}

}
