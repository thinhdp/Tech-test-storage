package game.implementors;

import java.util.Random;

import game.domain.Board;
import game.domain.Cell;
import game.domain.Game;
import game.domain.SudokuRules;
import game.ports.IGenerator;
import game.ports.IRandom;

public class ImplementBasicGenerator implements IGenerator {
	private final IRandom random;

	public ImplementBasicGenerator(IRandom random) {
		this.random = random;
	}

	// Generate a Sudoku grid with x pre-fill cells
	@Override
	public Game generate(int numberOfPreFillCells) {
		Cell[][] grid = createEmptyGrid();
		Board board = new Board(grid);
		Random randomSource = random.random();
		
		fillDiagonal(board, randomSource);
		fillRemaining(board, 0, 0);

		Board solutionBoard = new Board(board.getGrid());
		board = markAllCellsFixed(board);
		board = removeXDigits(board, numberOfPreFillCells, randomSource);
		
		return new Game(board, solutionBoard);
	}

	private Cell[][] createEmptyGrid() {
		Cell[][] grid = new Cell[SudokuRules.GRID_SIZE][SudokuRules.GRID_SIZE];

		for (int row = 0; row < SudokuRules.GRID_SIZE; row++)
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++)
				grid[row][column] = new Cell(row, column, false, 0);

		return grid;
	}

	private void fillBox(Board board, int row, int column, Random random) {
		int number;
		for (int rowIncrease = 0; rowIncrease < SudokuRules.BOX_SIZE; rowIncrease++) {
			for (int columnIncrease = 0; columnIncrease < SudokuRules.BOX_SIZE; columnIncrease++) {
				do {
					number = random.nextInt(SudokuRules.GRID_SIZE) + 1;
				} while (SudokuRules.countOccurrencesInBox(board, row, column, number) > 0);
				board.set(row + rowIncrease, column + columnIncrease, number);
			}
		}
	}

	// Fill the diagonal 3x3 matrices
	// The diagonal blocks are filled to simplify the process
	private void fillDiagonal(Board board, Random random) {
		for (int i = 0; i < SudokuRules.GRID_SIZE; i = i + SudokuRules.BOX_SIZE) {
			// Fill each 3x3 subGrid diagonally
			fillBox(board, i, i, random);
		}
	}

	// Fill remaining blocks in the board
	private boolean fillRemaining(Board board, int row, int column) {

		if (row == SudokuRules.GRID_SIZE) {
			return true;
		}

		if (column == SudokuRules.GRID_SIZE) {
			return fillRemaining(board, row + 1, 0);
		}

		if (board.get(row, column) != 0) {
			return fillRemaining(board, row, column + 1);
		}

		for (int number = 1; number <= SudokuRules.GRID_SIZE; number++) {
			if (SudokuRules.isValidNumber(board, row, column, number)) {
				board.set(row, column, number);
				if (fillRemaining(board, row, column + 1)) {
					return true;
				}
				board.set(row, column, 0);
			}
		}
		return false;
	}

	private Board markAllCellsFixed(Board board) {

		Cell[][] newGrid = new Cell[SudokuRules.GRID_SIZE][SudokuRules.GRID_SIZE];

		for (int row = 0; row < SudokuRules.GRID_SIZE; row++) {
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++) {

				int value = board.get(row, column);

				newGrid[row][column] = new Cell(row, column, true, value);
			}
		}

		return new Board(newGrid);
	}

	// Remove x digits randomly from the board
	private Board removeXDigits(Board board, int numberOfPreFillCell, Random random) {
		Cell[][] grid = board.getGrid();
		int remaining = SudokuRules.GRID_SIZE*SudokuRules.GRID_SIZE - numberOfPreFillCell;

		while (remaining > 0) {
			int cellPosition = random.nextInt(SudokuRules.GRID_SIZE*SudokuRules.GRID_SIZE);

			int row = cellPosition / SudokuRules.GRID_SIZE;
			int column = cellPosition % SudokuRules.GRID_SIZE;

			if (grid[row][column].getValue() != 0) {
				grid[row][column] = new Cell(row, column, false, 0);

				// Decrease the count of digits to remove
				remaining--;
			}
		}
		return new Board(grid);
	}

}