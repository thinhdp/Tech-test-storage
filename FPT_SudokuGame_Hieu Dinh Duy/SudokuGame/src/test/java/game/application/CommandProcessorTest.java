package game.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import game.TestUtility;
import game.domain.Board;
import game.domain.Game;
import game.ports.IGenerator;
import game.ports.IResolver;

public class CommandProcessorTest {
	CommandProcessor processor = new CommandProcessor();

	

	@Test
	void shouldParseMoveCommand() {
		Command cmd = processor.parse("A1 5");

		assertEquals(Command.Type.MOVE, cmd.getType());
		assertEquals(0, cmd.getRow());
		assertEquals(0, cmd.getColumn());
		assertEquals(5, cmd.getValue());
	}

	@Test
	void shouldExecuteMove() {
		CommandProcessor processor = new CommandProcessor();

		IGenerator generator = mock(IGenerator.class);
		IResolver resolver = mock(IResolver.class);

		GameService service = new GameService(generator, resolver);

		Board board = TestUtility.emptyBoard();

		Command cmd = new Command(Command.Type.MOVE, 0, 0, 5);

		processor.execute(cmd, service, board);

		assertEquals(5, board.get(0, 0));
	}

	@Test
	void shouldExecuteGenerate() {

		IGenerator generator = mock(IGenerator.class);
		IResolver resolver = mock(IResolver.class);

		Board newBoard = TestUtility.emptyBoard();
		
		Game game = new Game(newBoard, newBoard);
		
		when(generator.generate(TestUtility.NUMBER_OF_PRE_FILL_CELLS)).thenReturn(game);

		GameService service = new GameService(generator, resolver);

		Command cmd = new Command(Command.Type.GENERATE, -1, -1, null);

		CommandProcessor.CommandResult result = processor.execute(cmd, service, TestUtility.emptyBoard());

		assertEquals(newBoard, result.newBoard);
	}

	@Test
	void shouldParseClearCommand() {
		Command cmd = processor.parse("B2 clear");

		assertEquals(Command.Type.CLEAR, cmd.getType());
	}
	
	@Test
	void shouldParseHintCommand() {
		Command cmd = processor.parse("hint");

		assertEquals(Command.Type.HINT, cmd.getType());
	}
	
	@Test
	void shouldParseCheckCommand() {
		Command cmd = processor.parse("check");

		assertEquals(Command.Type.CHECK, cmd.getType());
	}

	@Test
	void shouldParseQuitCommand() {
		Command cmd = processor.parse("quit");

		assertEquals(Command.Type.QUIT, cmd.getType());
	}

	@Test
	void shouldReturnInvalidForBadInput() {
		Command cmd = processor.parse("XYZ");

		assertEquals(Command.Type.INVALID, cmd.getType());
	}
}