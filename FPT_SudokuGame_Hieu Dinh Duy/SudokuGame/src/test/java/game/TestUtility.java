package game;

import game.domain.Board;
import game.domain.Cell;

public class TestUtility {
	
	public static final int NUMBER_OF_PRE_FILL_CELLS = 30;
	
	public static Board emptyBoard() {
		Cell[][] grid = new Cell[9][9];
		for (int row = 0; row < 9; row++)
			for (int column = 0; column < 9; column++)
				grid[row][column] = new Cell(row, column, false, 0);
		return new Board(grid);
	}
}
