package game.application;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessorParameterizedTest {

	CommandProcessor processor = new CommandProcessor();

	@ParameterizedTest
	@CsvSource({ "quit,QUIT", "hint,HINT", "resolve,RESOLVE", "generate,GENERATE", "check,CHECK" })
	void shouldParseSimpleCommands(String input, Command.Type expectedType) {
		Command cmd = processor.parse(input);

		assertEquals(expectedType, cmd.getType());
	}

	@ParameterizedTest
	@CsvSource({ "A1 5,0,0,5", "B2 9,1,1,9", "C3 1,2,2,1" })
	void shouldParseMoveCommands(String input, int row, int column, int value) {
		Command cmd = processor.parse(input);

		assertEquals(Command.Type.MOVE, cmd.getType());
		assertEquals(row, cmd.getRow());
		assertEquals(column, cmd.getColumn());
		assertEquals(value, cmd.getValue());
	}

	@ParameterizedTest
	@CsvSource({ "A1 clear,0,0", "B2 clear,1,1" })
	void shouldParseClearCommands(String input, int row, int column) {
		Command cmd = processor.parse(input);

		assertEquals(Command.Type.CLEAR, cmd.getType());
		assertEquals(row, cmd.getRow());
		assertEquals(column, cmd.getColumn());
	}

	@ParameterizedTest
	@CsvSource({ "invalid", "Z9 5", "A1 10" })
	void shouldParseInvalidCommands(String input) {
		Command cmd = processor.parse(input);

		assertEquals(Command.Type.INVALID, cmd.getType());
	}
}
