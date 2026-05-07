package game.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class SudokuRulesParameterizedTest {

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9 })
	void shouldAcceptValidNumbers(int number) {
		assertTrue(SudokuRules.isValidNumberInput(number));
	}

	@ParameterizedTest
	@ValueSource(ints = { -1, 0, 10, 99 })
	void shouldRejectInvalidNumbers(int number) {
		assertFalse(SudokuRules.isValidNumberInput(number));
	}

	@ParameterizedTest
	@ValueSource(strings = { "A1 1", "B2 9", "C3 clear", "quit", "hint", "resolve", "generate", "check" })
	void shouldAcceptValidCommands(String input) {
		assertTrue(SudokuRules.isValidUserInput(input));
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "Z9 5", "A10 1", "A1 10", "random text" })
	void shouldRejectInvalidCommands(String input) {
		assertFalse(SudokuRules.isValidUserInput(input));
	}

	@ParameterizedTest
	@CsvSource({ "0,0,4,true", "0,1,5,false", // duplicate in row
			"1,0,5,false" // duplicate in column
	})
	void shouldValidateNumberPlacement(int row, int column, int number, boolean expected) {
		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 5);

		boolean result = SudokuRules.isValidNumber(board, row, column, number);

		assertEquals(expected, result);
	}
	
	static Stream<Object[]> invalidBoards() {
		return Stream.of(new Object[] { 0, 0, 5, false }, // valid
				new Object[] { 0, 1, 5, false }, // row conflict
				new Object[] { 1, 0, 5, false } // column conflict
		);
	}

	@ParameterizedTest
	@MethodSource("invalidBoards")
	void testMultipleCases(int row, int column, int number, boolean expected) {
		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 5);

		boolean result = SudokuRules.isValidNumber(board, row, column, number);

		assertEquals(expected, result);
	}
}
