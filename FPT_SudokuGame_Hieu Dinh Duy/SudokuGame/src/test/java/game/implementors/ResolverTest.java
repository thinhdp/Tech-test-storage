package game.implementors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import game.TestUtility;
import game.domain.*;
import game.ports.IDisplay;

public class ResolverTest {

	@Test
	void shouldReturnHint() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = TestUtility.emptyBoard();

		String hint = resolver.hint(board, board);

		assertTrue(hint.contains("Hint") || hint.contains("complete"));
	}

	@Test
	void shouldDetectRowConflict() {
		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = TestUtility.emptyBoard();

		board.set(0, 0, 5);
		board.set(0, 1, 5);

		String result = resolver.check(board, 0, 0, 5);

		assertTrue(result.contains("Row"));
	}
}