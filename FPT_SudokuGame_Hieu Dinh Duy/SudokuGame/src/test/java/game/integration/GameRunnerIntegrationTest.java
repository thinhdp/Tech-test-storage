package game.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import game.TestUtility;
import game.application.CommandProcessor;
import game.application.GameRunner;
import game.application.GameService;
import game.domain.Board;
import game.domain.Cell;
import game.domain.Game;
import game.implementors.ImplementBasicGenerator;
import game.implementors.ImplementBruteForceResolver;
import game.implementors.ImplementConsoleDisplay;
import game.implementors.ImplementRandom;
import game.ports.IDisplay;
import game.ports.IGenerator;
import game.ports.IResolver;

public class GameRunnerIntegrationTest {

	@Test
	void shouldPlayMoveThenQuit() {
		IDisplay display = mock(IDisplay.class);
		IGenerator generator = mock(IGenerator.class);
		IResolver resolver = mock(IResolver.class);

		Board board = TestUtility.emptyBoard();
		Game game = new Game(board, board);

		when(generator.generate(anyInt())).thenReturn(game);

		GameService service = new GameService(generator, resolver);
		CommandProcessor processor = new CommandProcessor();
		GameRunner runner = new GameRunner(processor, service, display);

		String input = "A1 5\nquit\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		runner.run(scanner);

		verify(display, atLeastOnce()).display(any(Board.class));
	}

	@Test
	void shouldRejectMoveOnFixedCell() {
		IDisplay display = mock(IDisplay.class);
		IGenerator generator = mock(IGenerator.class);
		IResolver resolver = mock(IResolver.class);

		Board board = TestUtility.emptyBoard();
		Game game = new Game(board, board);

		when(generator.generate(anyInt())).thenReturn(game);

		GameService service = new GameService(generator, resolver);
		GameRunner runner = new GameRunner(new CommandProcessor(), service, display);

		String input = "A1 3\nquit\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		runner.run(scanner);

		verify(display, atLeastOnce()).display(any());
	}

	@Test
	void shouldHandleInvalidCommand() {
		IDisplay display = mock(IDisplay.class);
		IGenerator generator = mock(IGenerator.class);
		IResolver resolver = mock(IResolver.class);

		Board board = TestUtility.emptyBoard();

		Game game = new Game(board, board);

		when(generator.generate(anyInt())).thenReturn(game);

		GameRunner runner = new GameRunner(new CommandProcessor(), new GameService(generator, resolver), display);

		String input = "INVALID\nquit\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		runner.run(scanner);

		verify(display, atLeastOnce()).display(any());
	}

	@Test
	void shouldHandleWhenPlayOnFixedCell() {
		IDisplay display = mock(IDisplay.class);
		IGenerator generator = mock(IGenerator.class);
		IResolver resolver = mock(IResolver.class);

		Cell[][] grid = new Cell[9][9];
		grid[0][0] = new Cell(0, 0, true, 5);
		Board board = new Board(grid);
		Game game = new Game(board, board);

		when(generator.generate(anyInt())).thenReturn(game);

		GameRunner runner = new GameRunner(new CommandProcessor(), new GameService(generator, resolver), display);

		String input = "A1 5\nquit\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		runner.run(scanner);

		assertEquals("\nEnter command (e.g: A3 4, C5 clear, hint, check, quit, generate):Invalid move. Cell A1 is pre-filled."
					+System.lineSeparator()
					+"\nEnter command (e.g: A3 4, C5 clear, hint, check, quit, generate):Exiting..."
					+System.lineSeparator()
					, out.toString());
	}

	@Test
	void shouldCompleteGameSuccessfully() {
		GameRunner runner = new GameRunner(new CommandProcessor(),
				new GameService(new ImplementBasicGenerator(new ImplementRandom()),
						new ImplementBruteForceResolver(new ImplementConsoleDisplay())),
				new ImplementConsoleDisplay());

		// Simulate solving the puzzle
		runner.getService().generateNewGame(TestUtility.NUMBER_OF_PRE_FILL_CELLS);
		Board solution = runner.getService().getSolution();
		Board puzzle = runner.getService().getCurrentBoard();

		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				if (puzzle.isEmpty(row, column) && !puzzle.isFixed(row, column)) {
					puzzle.set(row, column, solution.get(row, column));
				}
			}
		}

		assertTrue(runner.getService().isSolved(puzzle));
	}

}