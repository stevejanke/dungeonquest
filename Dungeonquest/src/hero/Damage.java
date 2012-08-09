package hero;

import dice.DiceRoller;

public class Damage {

	private DiceRoller damageRoll = null;
	private boolean armourProtects = false;
	private boolean luckProtects = false;

	public Damage(DiceRoller damageRoll, boolean armourProtects,
			boolean luckProtects) {
		super();
		this.damageRoll = damageRoll;
		this.armourProtects = armourProtects;
		this.luckProtects = luckProtects;
	}

	public int getBaseDamage() {
		damageRoll.doRolls();
		return damageRoll.getRollResult();
	}

	public boolean isArmourProtects() {
		return armourProtects;
	}

	public boolean isLuckProtects() {
		return luckProtects;
	}

}
