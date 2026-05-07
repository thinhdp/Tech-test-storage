package game.implementors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import game.domain.Board;
import game.domain.Cell;
import game.domain.SudokuRules;
import game.ports.IDisplay;

public class ResolverEdgeCaseTest {

	@Test
	void shouldDetectRowConflict() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 4);
		board.set(0, 1, 4);

		String result = resolver.check(board, 0, 0, 4);

		assertTrue(result.contains("Row"));
	}

	@Test
	void shouldDetectColumnConflict() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 6);
		board.set(1, 0, 6);

		String result = resolver.check(board, 0, 0, 6);

		assertTrue(result.contains("Column"));
	}

	@Test
	void shouldDetectBoxConflict() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = new Board(new Cell[9][9]);
		board.set(0, 0, 2);
		board.set(1, 1, 2);

		String result = resolver.check(board, 0, 0, 2);

		assertTrue(result.contains("subgrid"));
	}

	@Test
	void shouldReturnNoViolationForValidInput() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = new Board(new Cell[9][9]);

		String result = resolver.check(board, 0, 0, 5);

		assertTrue(result.contains("No rule violations"));
	}

	@Test
	void shouldDetectMultipleDuplicatesInRow() {
		Board board = new Board(new Cell[9][9]);

		board.set(0, 0, 5);
		board.set(0, 1, 5);
		board.set(0, 2, 5);

		int count = SudokuRules.countOccurrencesInRowOrColumn(board, 0, 5, SudokuRules.CellField.ROW);

		assertTrue(count > 2);
	}
}