package cardsAndDecks.functions;

import hero.Damage;
import cardsAndDecks.qualities.DoesDamage;

public class CardTypeDamage implements DoesDamage {

	private final Damage damage;

	private CardTypeDamage(Damage damage) {
		super();
		this.damage = damage;
	}

	@Override
	public Damage getDamage() {
		return damage;
	}

}
