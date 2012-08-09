package hero;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.Math.max;

import java.util.List;

import board.Square;
import cardsAndDecks.Card;
import cardsAndDecks.qualities.HasValue;
import cardsAndDecks.qualities.IsCollectible;

import com.google.common.collect.Lists;

public class Hero {

	private boolean isBuilt = false;

	private String shortName = null;
	private String longName = null;
	private String title = null;

	private List<Card> inventory = Lists.newArrayList();

	private int maxHp = 0;
	private int currentHp = 0;

	private int strength = 0;
	private int armour = 0;
	private int luck = 0;

	private Square square = null;

	private Hero(String shortName, String longName, String title) {
		super();
		this.shortName = shortName;
		this.longName = longName;
		this.title = title;
		isBuilt = false;
	}

	public static Hero named(String shortName, String longName, String title) {
		return new Hero(shortName, longName, title);
	}

	public Hero setStrenght(int strength) {
		checkState(!isBuilt);
		this.strength = strength;
		return this;
	}

	public Hero setArmour(int armour) {
		checkState(!isBuilt);
		this.armour = armour;
		return this;
	}

	public Hero setLuck(int luck) {
		checkState(!isBuilt);
		this.luck = luck;
		return this;
	}

	public Hero setMaxHp(int maxHp) {
		checkState(!isBuilt);
		this.maxHp = maxHp;
		return this;
	}

	public String getShortName() {
		checkState(isBuilt);
		return shortName;
	}

	public String getLongName() {
		checkState(isBuilt);
		return longName;
	}

	public String getTitle() {
		checkState(isBuilt);
		return title;
	}

	public int getMaxHp() {
		checkState(isBuilt);
		return maxHp;
	}

	public int getCurrentHp() {
		checkState(isBuilt);
		return currentHp;
	}

	public int getStrength() {
		checkState(isBuilt);
		return strength;
	}

	public int getArmour() {
		checkState(isBuilt);
		return armour;
	}

	public int getLuck() {
		checkState(isBuilt);
		return luck;
	}

	public Hero build() {
		checkState(strength != 0);
		checkState(armour != 0);
		checkState(luck != 0);
		checkState(maxHp != 0);
		checkNotNull(shortName);
		checkNotNull(longName);
		checkNotNull(title);
		isBuilt = true;
		return this;
	}

	public void reset() {
		checkState(isBuilt);
		currentHp = maxHp;
		for (Card c : inventory) {
			c.returnToDeck();
		}
		inventory.clear();
	}

	public void addToInventory(Card card) {
		checkState(isBuilt);
		checkArgument(card instanceof IsCollectible);
		inventory.add(card);
	}

	public int getWealth() {
		checkState(isBuilt);
		int totalWealth = 0;
		for (Card c : inventory) {
			if (c instanceof HasValue) {
				totalWealth += ((HasValue) c).getValue();
			}
		}
		return totalWealth;
	}

	public void inflictDamage(Damage damage) {
		checkState(isBuilt);
		int damageInflicted = damage.getBaseDamage();
		if (damage.isArmourProtects()) {
			damageInflicted = max(0, damageInflicted - getArmour());
		}
		if (damage.isLuckProtects()) {
			damageInflicted = max(0, damageInflicted - getLuck());
		}
		currentHp = max(0, currentHp - damageInflicted);
	}

	public void killHero() {
		checkState(isBuilt);
		currentHp = 0;
	}

	public boolean isAlive() {
		return currentHp > 0;
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	@Override
	public String toString() {
		return "Hero [shortName=" + shortName + "]";
	}

}
