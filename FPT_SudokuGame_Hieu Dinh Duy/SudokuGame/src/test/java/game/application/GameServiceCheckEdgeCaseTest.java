package game.application;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import game.TestUtility;
import game.domain.Board;
import game.ports.IGenerator;
import game.ports.IResolver;

public class GameServiceCheckEdgeCaseTest {

	@Test
	void shouldDetectRowViolation() {
		IResolver resolver = mock(IResolver.class);
		IGenerator generator = mock(IGenerator.class);

		Board board = TestUtility.emptyBoard();
		board.set(0, 0, 5);
		board.set(0, 1, 5);

		when(resolver.check(any(), anyInt(), anyInt(), anyInt())).thenReturn("Number 5 already exists in Row A.");

		GameService service = new GameService(generator, resolver);

		String result = service.check(board);

		assertTrue(result.contains("Row"));
	}

	@Test
	void shouldDetectColumnViolation() {
		IResolver resolver = mock(IResolver.class);
		IGenerator generator = mock(IGenerator.class);

		Board board = TestUtility.emptyBoard();
		board.set(0, 0, 3);
		board.set(1, 0, 3);

		when(resolver.check(any(), anyInt(), anyInt(), anyInt())).thenReturn("Number 3 already exists in Column 1.");

		GameService service = new GameService(generator, resolver);

		String result = service.check(board);

		assertTrue(result.contains("Column"));
	}

	@Test
	void shouldDetectBoxViolation() {
		IResolver resolver = mock(IResolver.class);
		IGenerator generator = mock(IGenerator.class);

		Board board = TestUtility.emptyBoard();
		board.set(0, 0, 8);
		board.set(1, 1, 8);

		when(resolver.check(any(), anyInt(), anyInt(), anyInt()))
				.thenReturn("Number 8 already exists in the same 3x3 subgrid.");

		GameService service = new GameService(generator, resolver);

		String result = service.check(board);

		assertTrue(result.contains("subgrid"));
	}

	@Test
	void shouldReturnValidWhenNoViolation() {
		IResolver resolver = mock(IResolver.class);
		IGenerator generator = mock(IGenerator.class);

		when(resolver.check(any(), anyInt(), anyInt(), anyInt())).thenReturn("No rule violations detected...");

		GameService service = new GameService(generator, resolver);

		String result = service.check(TestUtility.emptyBoard());

		assertTrue(result.contains("No rule violations"));
	}
}