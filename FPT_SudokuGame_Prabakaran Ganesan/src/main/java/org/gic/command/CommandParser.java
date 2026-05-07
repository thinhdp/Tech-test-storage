package org.gic.command;

import java.util.Optional;

/**
 * Parses raw text input from the player into a Command.
 *
 * Valid input formats:
 *   - "A3 4"       place number 4 at row A, column 3
 *   - "C5 clear"   clear the cell at row C, column 5
 *   - "hint"       request a hint
 *   - "check"      validate the current board
 *   - "quit"       exit the game
 *
 * Returns Optional.empty() if the input doesn't match any known format.
 */
public class CommandParser {

    public Optional<Command> parse(String input) {
        if (input == null) {
            return Optional.empty();
        }

        String trimmed = input.trim();

        if (trimmed.equalsIgnoreCase("hint")) {
            return Optional.of(Command.hint());
        }
        if (trimmed.equalsIgnoreCase("check")) {
            return Optional.of(Command.check());
        }
        if (trimmed.equalsIgnoreCase("quit")) {
            return Optional.of(Command.quit());
        }

        // expecting something like "A3 4" or "C5 clear"
        String[] parts = trimmed.split("\\s+");
        if (parts.length < 2) {
            return Optional.empty();
        }

        String cellRef = parts[0].toUpperCase();
        if (cellRef.length() != 2) {
            return Optional.empty();
        }

        char rowChar = cellRef.charAt(0);
        char colChar = cellRef.charAt(1);

        if (rowChar < 'A' || rowChar > 'I') {
            return Optional.empty();
        }
        if (colChar < '1' || colChar > '9') {
            return Optional.empty();
        }

        int row = rowChar - 'A';
        int col = colChar - '1';

        String action = parts[1];

        if (action.equalsIgnoreCase("clear")) {
            return Optional.of(Command.clear(row, col));
        }

        try {
            int value = Integer.parseInt(action);
            if (value < 1 || value > 9) {
                return Optional.empty();
            }
            return Optional.of(Command.place(row, col, value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}

