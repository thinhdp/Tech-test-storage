package game.implementors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import game.TestUtility;
import game.domain.Board;
import game.domain.Cell;
import game.ports.IDisplay;

public class ImplementBruteForceResolverTest {

	@Test
	void shouldSolveSimpleBoard() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = TestUtility.emptyBoard();

		boolean result = resolver.resolve(board);

		assertTrue(result);
	}

	@Test
	void shouldReturnHint() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = TestUtility.emptyBoard();

		String hint = resolver.hint(board, board);

		assertNotNull(hint);
	}

	@Test
	void shouldReturnNoHint() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = TestUtility.emptyBoard();

		resolver.resolve(board);
		
		String hint = resolver.hint(board, board);

		assertEquals("Board already complete!", hint);
	}
	
	@Test
	void shouldCheckValidBoard() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = new Board(new Cell[9][9]);

		String result = resolver.check(board, 0, 0, 1);

		assertTrue(result.contains("No rule violations"));
	}
}