package game.domain;

public class Board {

	private final Cell[][] grid;

	public Board(Cell[][] input) {

		if (input.length != SudokuRules.GRID_SIZE || input[0].length != SudokuRules.GRID_SIZE) {
			throw new IllegalArgumentException("Board must be 9x9");
		}

		this.grid = new Cell[SudokuRules.GRID_SIZE][SudokuRules.GRID_SIZE];

		for (int row = 0; row < SudokuRules.GRID_SIZE; row++) {
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++) {
				Cell cell = input[row][column];
				if (cell == null) {
					this.grid[row][column] = new Cell(row, column, false, 0);
				} else {
					this.grid[row][column] = new Cell(cell.getRow(), cell.getColumn(), cell.isFixed(), cell.getValue());
				}
			}
		}
	}

	public int get(int row, int column) {
		return grid[row][column].getValue();
	}

	public void set(int row, int column, int value) {
		Cell current = grid[row][column];

		if (current.isFixed()) {
			throw new UnsupportedOperationException("Cell is fixed");
		}

		grid[row][column] = current.withValue(value);
	}

	public boolean isEmpty(int row, int column) {
		return grid[row][column].getValue() == 0;
	}

	public boolean isFixed(int row, int column) {
		return grid[row][column].isFixed();
	}

	public Cell[][] getGrid() {
		Cell[][] copy = new Cell[SudokuRules.GRID_SIZE][SudokuRules.GRID_SIZE];

		for (int row = 0; row < SudokuRules.GRID_SIZE; row++) {
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++) {
				Cell cell = grid[row][column];
				copy[row][column] = new Cell(cell.getRow(), cell.getColumn(), cell.isFixed(), cell.getValue());
			}
		}

		return copy;
	}
}