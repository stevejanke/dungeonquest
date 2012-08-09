package board;

import static com.google.common.base.Preconditions.checkArgument;

public class Passage {

	private final Reference endPoint1;
	private final Reference endPoint2;

	private Side side1 = Side.OPENING;
	private Side side2 = Side.OPENING;

	public Passage(Reference endPoint1, Reference endPoint2) {
		super();
		this.endPoint1 = endPoint1;
		this.endPoint2 = endPoint2;
	}

	public void setSide(Side side, Reference reference) {
		checkArgument((reference == endPoint1) || reference == endPoint2);
		if (reference == endPoint1) {
			side1 = side;
		} else {
			side2 = side;
		}
	}

	public Side getSide(Reference reference) {
		checkArgument((reference == endPoint1) || reference == endPoint2);
		if (reference == endPoint1) {
			return side1;
		} else {
			return side2;
		}
	}

}
