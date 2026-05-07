package game.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SudokuRulesTest {

	@Test
	void shouldValidateCorrectInput() {
		assertTrue(SudokuRules.isValidUserInput("A1 5"));
		assertTrue(SudokuRules.isValidUserInput("quit"));
	}

	@Test
	void shouldRejectInvalidInput() {
		assertFalse(SudokuRules.isValidUserInput("Z9 10"));
		assertFalse(SudokuRules.isValidUserInput(""));
		assertFalse(SudokuRules.isValidUserInput(null));
	}

	@Test
	void shouldValidateNumberRange() {
		assertTrue(SudokuRules.isValidNumberInput(5));
		assertFalse(SudokuRules.isValidNumberInput(0));
		assertFalse(SudokuRules.isValidNumberInput(10));
	}

	@Test
	void shouldDetectInvalidRow() {
		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 5);
		board.set(0, 1, 5);

		int count = SudokuRules.countOccurrencesInRowOrColumn(board, 0, 5, SudokuRules.CellField.ROW);

		assertTrue(count > 1);
	}

	@Test
	void shouldDetectInvalidColumn() {
		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 5);
		board.set(1, 0, 5);

		int count = SudokuRules.countOccurrencesInRowOrColumn(board, 0, 5, SudokuRules.CellField.COLUMN);

		assertTrue(count > 1);
	}

	@Test
	void shouldDetectInvalidBox() {
		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 5);
		board.set(1, 1, 5);

		int count = SudokuRules.countOccurrencesInBox(board, 0, 0, 5);

		assertTrue(count > 1);
	}
}