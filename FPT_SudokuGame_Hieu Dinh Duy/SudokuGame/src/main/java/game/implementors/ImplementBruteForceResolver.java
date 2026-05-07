package game.implementors;

import game.domain.Board;
import game.domain.SudokuRules;
import game.ports.IDisplay;
import game.ports.IResolver;

public class ImplementBruteForceResolver implements IResolver {

	private final IDisplay displayer;

	public ImplementBruteForceResolver(IDisplay displayer) {
		this.displayer = displayer;
	}

	@Override
	public boolean resolve(Board board) {
		int[] stepCount = {0};
		return resolveRecursive(board, stepCount);
	}
	
	private boolean resolveRecursive(Board board, int[] stepCount) {
		for (int row = 0; row < SudokuRules.GRID_SIZE; row++) {
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++) {
				if (board.get(row, column) == 0) {
					for (int number = 1; number <= SudokuRules.GRID_SIZE; number++) {
						if (SudokuRules.isValidNumber(board, row, column, number) && !board.isFixed(row, column)) {
							board.set(row, column, number);
							stepCount[0]++;
 
							if (displayer != null) {
								displayer.display(board, stepCount[0]);
							}
 
							if (resolveRecursive(board, stepCount))
								return true;
							
							if(!board.isFixed(row, column))
								board.set(row, column, 0);
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String hint(Board board, Board solution) {
		for (int row = 0; row < SudokuRules.GRID_SIZE; row++) {
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++) {
				if (board.get(row, column) == 0) {
					int correctValue = solution.get(row, column);
					return String.format("Hint: Cell %c%d = %d", (char) (row + 'A'), column + 1, correctValue);
				}
			}
		}
		return "Board already complete!";
	}

	@Override
	public String check(Board board, int row, int column, int number) {
		if (SudokuRules.isValidNumberInput(number)) {
			if (SudokuRules.countOccurrencesInRowOrColumn(board, row, number, SudokuRules.CellField.ROW) > 1) {
				return String.format("Number %d already exists in Row %c.", number, (char) (row + 'A'));
			} else if (SudokuRules.countOccurrencesInRowOrColumn(board, column, number, SudokuRules.CellField.COLUMN) > 1) {
				return String.format("Number %d already exists in Column %d.", number, column + 1);
			} else if (SudokuRules.countOccurrencesInBox(board, row, column, number) > 1) {
				return String.format("Number %d already exists in the same 3x3 subgrid.", number);
			}
		}
		return "No rule violations detected...";
	}
}