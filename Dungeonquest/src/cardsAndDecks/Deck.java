package cardsAndDecks;

import main.ClientFactory;

public interface Deck {

	void shuffle();

	Card drawCard();

	void dump();

	void addCard(Card c);

	void setClientFactory(ClientFactory clientFactory);
}
