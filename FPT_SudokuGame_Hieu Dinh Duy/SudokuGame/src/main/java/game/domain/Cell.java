package game.domain;

public class Cell {
	private final int row;
	private final int column;
	private final boolean fixed;
	private final int value;

	public Cell(int row, int column, boolean fixed, int value) {
		this.row = row;
		this.column = column;
		this.fixed = fixed;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getValue() {
		return value;
	}

	public boolean isFixed() {
		return fixed;
	}

	public Cell withValue(int newValue) {
		if (fixed) {
			throw new UnsupportedOperationException("Cell is fixed");
		}
		return new Cell(row, column, false, newValue);
	}
}