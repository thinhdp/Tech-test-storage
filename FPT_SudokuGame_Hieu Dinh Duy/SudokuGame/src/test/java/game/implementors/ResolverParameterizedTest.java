package game.implementors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import game.TestUtility;
import game.domain.Board;
import game.ports.IDisplay;

public class ResolverParameterizedTest {

	@ParameterizedTest
	@CsvSource({ "0,0,0,1,Row", "0,0,1,0,Column", "0,0,1,1,subgrid" })
	void shouldDetectConflicts(int r1, int c1, int r2, int c2, String expected) {

		IDisplay display = mock(IDisplay.class);
		ImplementBruteForceResolver resolver = new ImplementBruteForceResolver(display);

		Board board = TestUtility.emptyBoard();

		board.set(r1, c1, 5);
		board.set(r2, c2, 5);

		String result = resolver.check(board, r1, c1, 5);

		assertTrue(result.contains(expected));
	}
}