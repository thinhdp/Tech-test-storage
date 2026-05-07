package game.implementors;

import game.domain.Board;
import game.domain.SudokuRules;
import game.ports.IDisplay;

public class ImplementConsoleDisplay implements IDisplay {
	private static final String[] ROW_NAME = new String[] {"A ", "B ", "C ", "D ", "E ", "F ", "G ", "H ", "I "};
	@Override
	public void display(Board board) {
		for (int row = 0; row < SudokuRules.GRID_SIZE; row++) {
			if (row == 0)
				System.out.print("  1 2 3 4 5 6 7 8 9\n");
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++) {
				if(column == 0)
					System.out.print(ROW_NAME[row]);
				if (board.get(row, column) == 0)
					System.out.print("_ ");
				else
					System.out.print(board.get(row, column) + " ");
			}
			System.out.println();
		}
	}

	@Override
	public void display(Board board, int stepCount) {
		System.out.printf("\n== Step %d ==\n", stepCount);
		display(board);
	}
}
