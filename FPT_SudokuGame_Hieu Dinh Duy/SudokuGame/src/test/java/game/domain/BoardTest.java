package game.domain;

import org.junit.jupiter.api.Test;

import game.TestUtility;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

	@Test
	void shouldSetAndGetValue() {
		Board board = TestUtility.emptyBoard();

		board.set(0, 0, 5);

		assertEquals(5, board.get(0, 0));
	}

	@Test
	void shouldThrowWhenSetFixedCell() {
		Cell[][] grid = new Cell[9][9];
		grid[0][0] = new Cell(0, 0, true, 5);

		Board board = new Board(grid);

		assertThrows(UnsupportedOperationException.class, () -> board.set(0, 0, 9));
	}

	@Test
	void shouldReturnDeepCopyFromGetGrid() {
		Board board = TestUtility.emptyBoard();

		Cell[][] copy = board.getGrid();
		copy[0][0] = new Cell(0, 0, false, 9);

		assertEquals(0, board.get(0, 0)); // original unchanged
	}

	@Test
	void shouldInitializeNullCellsSafely() {
		Cell[][] grid = new Cell[9][9]; // all null

		Board board = new Board(grid);

		assertEquals(0, board.get(0, 0));
		assertFalse(board.isFixed(0, 0));
	}

	@Test
	void shouldReplaceCellInsteadOfMutating() {
		Cell[][] grid = new Cell[9][9];
		grid[0][0] = new Cell(0, 0, false, 1);

		Board board = new Board(grid);

		board.set(0, 0, 5);

		assertEquals(5, board.get(0, 0));
	}

	@Test
	void shouldThrowWhenSettingFixedCell() {
		Cell[][] grid = new Cell[9][9];
		grid[0][0] = new Cell(0, 0, true, 1);

		Board board = new Board(grid);

		assertThrows(UnsupportedOperationException.class, () -> board.set(0, 0, 5));
	}

	@Test
	void shouldDeepCopyGrid() {
		Cell[][] grid = new Cell[9][9];
		grid[0][0] = new Cell(0, 0, false, 1);

		Board board = new Board(grid);

		Cell[][] copy = board.getGrid();
		copy[0][0] = new Cell(0, 0, false, 9);

		assertEquals(1, board.get(0, 0)); // original unchanged
	}
}