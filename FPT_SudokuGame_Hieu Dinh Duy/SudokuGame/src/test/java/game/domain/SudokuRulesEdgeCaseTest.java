package game.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SudokuRulesEdgeCaseTest {

	@Test
	void shouldRejectNumberAlreadyInSameRow() {
		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 5);
		board.set(0, 5, 5);

		boolean valid = SudokuRules.isValidNumber(board, 0, 2, 5);

		assertFalse(valid);
	}

	@Test
	void shouldRejectNumberAlreadyInSameColumn() {
		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 7);
		board.set(5, 0, 7);

		boolean valid = SudokuRules.isValidNumber(board, 2, 0, 7);

		assertFalse(valid);
	}

	@Test
	void shouldRejectNumberAlreadyInSameBox() {
		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 9);
		board.set(1, 1, 9);

		boolean valid = SudokuRules.isValidNumber(board, 2, 2, 9);

		assertFalse(valid);
	}

	@Test
	void shouldRejectInvalidNumberOutOfRange() {
		Board board = new Board(new Cell[9][9]);

		assertFalse(SudokuRules.isValidNumber(board, 0, 0, 0));
		assertFalse(SudokuRules.isValidNumber(board, 0, 0, 10));
	}

	@Test
	void shouldAllowValidPlacement() {
		Board board = new Board(new Cell[9][9]);

		boolean valid = SudokuRules.isValidNumber(board, 0, 0, 5);

		assertTrue(valid);
	}
}