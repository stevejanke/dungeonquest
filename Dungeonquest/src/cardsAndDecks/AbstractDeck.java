package cardsAndDecks;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;

import main.ClientFactory;

public abstract class AbstractDeck implements Deck {

	private final ArrayList<Card> deck = new ArrayList<>();
	private ClientFactory clientFactory = null;

	public AbstractDeck() {
		super();
	}

	@Override
	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void addCard(Card card) {
		deck.add(card);
	}

	@Override
	public void shuffle() {
		Collections.shuffle(deck);
	}

	@Override
	public Card drawCard() {
		return deck.remove(0);
	}

	@Override
	public void dump() {
		checkNotNull(clientFactory);
		for (Card c : deck) {
			System.out.println(c.getName());
		}
	}

}
