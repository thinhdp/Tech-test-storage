package org.gic.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CommandParser.
 *
 * Covers all valid command formats plus various invalid inputs
 * to make sure the parser handles bad input gracefully.
 */
class CommandParserTest {

    private CommandParser parser;

    @BeforeEach
    void setUp() {
        parser = new CommandParser();
    }

    @Test
    void parsePlaceCommand() {
        Optional<Command> result = parser.parse("A3 4");
        assertTrue(result.isPresent());
        assertEquals(Command.Type.PLACE, result.get().getType());
        assertEquals(0, result.get().getRow());   // A = row 0
        assertEquals(2, result.get().getCol());   // column 3 = index 2
        assertEquals(4, result.get().getValue());
    }

    @Test
    void parsePlaceCommandLowercase() {
        Optional<Command> result = parser.parse("b5 9");
        assertTrue(result.isPresent());
        assertEquals(Command.Type.PLACE, result.get().getType());
        assertEquals(1, result.get().getRow());   // B = row 1
        assertEquals(4, result.get().getCol());   // column 5 = index 4
        assertEquals(9, result.get().getValue());
    }

    @Test
    void parseClearCommand() {
        Optional<Command> result = parser.parse("C5 clear");
        assertTrue(result.isPresent());
        assertEquals(Command.Type.CLEAR, result.get().getType());
        assertEquals(2, result.get().getRow());
        assertEquals(4, result.get().getCol());
    }

    @Test
    void parseHintCommand() {
        Optional<Command> result = parser.parse("hint");
        assertTrue(result.isPresent());
        assertEquals(Command.Type.HINT, result.get().getType());
    }

    @Test
    void parseCheckCommand() {
        Optional<Command> result = parser.parse("check");
        assertTrue(result.isPresent());
        assertEquals(Command.Type.CHECK, result.get().getType());
    }

    @Test
    void parseQuitCommand() {
        Optional<Command> result = parser.parse("quit");
        assertTrue(result.isPresent());
        assertEquals(Command.Type.QUIT, result.get().getType());
    }

    @Test
    void commandsAreCaseInsensitive() {
        assertTrue(parser.parse("HINT").isPresent());
        assertTrue(parser.parse("CHECK").isPresent());
        assertTrue(parser.parse("QUIT").isPresent());
        assertTrue(parser.parse("I9 CLEAR").isPresent());
    }

    @Test
    void emptyInputReturnsEmpty() {
        assertTrue(parser.parse("").isEmpty());
        assertTrue(parser.parse("   ").isEmpty());
    }

    @Test
    void nullInputReturnsEmpty() {
        assertTrue(parser.parse(null).isEmpty());
    }

    @Test
    void invalidRowLetterReturnsEmpty() {
        assertTrue(parser.parse("Z3 4").isEmpty()); // Z is not a valid row
    }

    @Test
    void valueOutOfRangeReturnsEmpty() {
        assertTrue(parser.parse("A3 0").isEmpty());
        assertTrue(parser.parse("A3 10").isEmpty());
    }

    @Test
    void columnOutOfRangeReturnsEmpty() {
        assertTrue(parser.parse("A0 5").isEmpty());
    }

    @Test
    void nonNumericValueReturnsEmpty() {
        assertTrue(parser.parse("A3 abc").isEmpty());
    }

    @Test
    void lastRowAndColumnAreParsedCorrectly() {
        Optional<Command> result = parser.parse("I9 7");
        assertTrue(result.isPresent());
        assertEquals(8, result.get().getRow()); // I = row 8
        assertEquals(8, result.get().getCol()); // column 9 = index 8
    }
}

