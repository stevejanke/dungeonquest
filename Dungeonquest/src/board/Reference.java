package board;

import static com.google.common.base.Preconditions.checkState;
import board.Board.Direction;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

public class Reference {

	private static ImmutableTable<Integer, Integer, Reference> referenceCache;
	private static boolean builtReferences = false;

	private boolean fixedReference = false;

	private final int row;
	private final int col;

	private static int MAX_ROW;
	private static int MAX_COL;

	private Reference(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.fixedReference = false;
	}

	public static void buildReferences(int numRows, int numCols) {
		checkState(!builtReferences);
		MAX_ROW = numRows;
		MAX_COL = numCols;
		Table<Integer, Integer, Reference> cacheBuilder = HashBasedTable
				.create();
		for (int row = 1; row <= numRows; row++) {
			for (int col = 1; col <= numCols; col++) {
				checkState(!cacheBuilder.contains(row, col));
				cacheBuilder.put(row, col, new Reference(row, col));
			}
		}
		referenceCache = new ImmutableTable.Builder<Integer, Integer, Reference>()
				.putAll(cacheBuilder).build();
		builtReferences = true;
	}

	public static Reference at(int row, int col) {
		checkState(builtReferences);
		return referenceCache.get(row, col);
	}

	public int getRow() {
		checkState(builtReferences);
		return row;
	}

	public int getCol() {
		checkState(builtReferences);
		return col;
	}

	public Optional<Reference> getAdjacent(Direction direction) {
		Optional<Reference> result = Optional.absent();
		switch (direction) {
		case NORTH:
			if (getRow() > 1) {
				result = Optional.of(at(getRow() - 1, getCol()));
			}
			break;
		case SOUTH:
			if (getRow() < MAX_ROW) {
				result = Optional.of(at(getRow() + 1, getCol()));
			}
			break;
		case WEST:
			if (getCol() > 1) {
				result = Optional.of(at(getRow(), getCol() - 1));
			}
			break;
		case EAST:
			if (getCol() < MAX_COL) {
				result = Optional.of(at(getRow(), getCol() + 1));
			}
			break;
		}
		return result;
	}

	public boolean isFixedReference() {
		checkState(builtReferences);
		return fixedReference;
	}

	public void setFixedReference(boolean fixedReference) {
		checkState(builtReferences);
		this.fixedReference = fixedReference;
	}

	@Override
	public String toString() {
		checkState(builtReferences);
		return "[R" + row + "|C" + col + "]";
	}

}
