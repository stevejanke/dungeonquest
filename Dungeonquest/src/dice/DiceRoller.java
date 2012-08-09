package dice;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DiceRoller {

	private final static Random randomizer = new Random();

	private final List<Integer> recentRolls = new ArrayList<>();

	private int numberOfRolls;
	private int totalModification;
	private int numberOfSides;

	private DiceRoller(int numberOfSides) {
		super();
		numberOfRolls = 1;
		totalModification = 0;
		this.numberOfSides = numberOfSides;
	}

	public static DiceRoller usingDice(int numberOfSides) {
		checkArgument(numberOfSides > 0);
		return new DiceRoller(numberOfSides);
	}

	public DiceRoller setNumberOfRolls(int numberOfRolls) {
		checkArgument(numberOfRolls > 0);
		this.numberOfRolls = numberOfRolls;
		return this;
	}

	public DiceRoller modifyUp(int modification) {
		this.totalModification += modification;
		return this;
	}

	public DiceRoller modifyDown(int modification) {
		this.totalModification -= modification;
		return this;
	}

	public void doRolls() {
		checkState(numberOfRolls > 0);
		recentRolls.clear();
		for (int i = 0; i < numberOfRolls; i++) {
			recentRolls.add(randomizer.nextInt(numberOfSides) + 1);
		}
	}

	public int getRollResult() {
		checkState(!recentRolls.isEmpty());

		int result = 0;

		Iterator<Integer> iter = recentRolls.iterator();
		while (iter.hasNext()) {
			result += iter.next();
		}

		return max(0, result + totalModification);
	}

	public Iterator<Integer> getRolls() {
		return recentRolls.iterator();
	}

}
