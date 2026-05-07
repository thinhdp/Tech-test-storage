package org.gic.game;

import org.gic.board.SudokuBoard;
import org.gic.board.SudokuGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end test for SudokuGame.
 *
 * Simulates a real game session by:
 *   1. Generating a puzzle with a fixed seed (so the board is deterministic)
 *   2. Building all the place commands needed to fill every empty cell correctly
 *   3. Piping those commands through the game as if a player typed them
 *   4. Asserting the success message is printed when the board is complete
 */
class SudokuGameIntegrationTest {

    private static final long SEED = 42L;

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    void redirectOutput() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void completingAllCellsCorrectlyShowsSuccessMessage() {
        // --- arrange ---
        // Generate the same board the game will use (same seed, same generator instance)
        SudokuGenerator generator = new SudokuGenerator(SEED);
        SudokuBoard boardForInspection = generator.generate();

        // Build one "RowCol value" command for every empty cell using the solution
        StringBuilder commands = new StringBuilder();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!boardForInspection.isPrefilled(r, c)) {
                    char row = (char) ('A' + r);
                    int col = c + 1;
                    int value = boardForInspection.getSolutionValue(r, c);
                    commands.append(row).append(col).append(" ").append(value).append("\n");
                }
            }
        }
        // after the success message the game asks whether to play again — answer quit
        commands.append("quit\n");

        // The game creates its own generator internally via the seed constructor.
        // We pass a fresh generator with the same seed so it produces the identical board.
        SudokuGenerator sameGenerator = new SudokuGenerator(SEED);
        Scanner scanner = new Scanner(commands.toString());
        SudokuGame game = new SudokuGame(scanner, sameGenerator);

        // --- act ---
        game.start();

        // --- assert ---
        String output = capturedOutput.toString();
        assertTrue(
            output.contains("You have successfully completed the Sudoku puzzle!"),
            "Expected success message in output.\n\nActual output:\n" + output
        );
    }

    @Test
    void prefilledCellRejectionMessageAppearsInOutput() {
        // Generate board and find the first pre-filled cell
        SudokuGenerator generator = new SudokuGenerator(SEED);
        SudokuBoard boardForInspection = generator.generate();

        int prefilledRow = -1, prefilledCol = -1;
        outer:
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (boardForInspection.isPrefilled(r, c)) {
                    prefilledRow = r;
                    prefilledCol = c;
                    break outer;
                }
            }
        }

        // Try to place a wrong value in a pre-filled cell, then quit
        char row = (char) ('A' + prefilledRow);
        int col = prefilledCol + 1;
        String input = row + "" + col + " 9\nquit\n";

        SudokuGenerator sameGenerator = new SudokuGenerator(SEED);
        Scanner scanner = new Scanner(input);
        SudokuGame game = new SudokuGame(scanner, sameGenerator);

        game.start();

        String output = capturedOutput.toString();
        assertTrue(
            output.contains("is pre-filled"),
            "Expected pre-filled rejection message.\n\nActual output:\n" + output
        );
    }
}

