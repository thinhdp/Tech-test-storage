package org.gic.game;

import org.gic.board.SudokuBoard;
import org.gic.board.SudokuGenerator;
import org.gic.board.SudokuValidator;
import org.gic.command.Command;
import org.gic.command.CommandParser;
import org.gic.display.GridPrinter;

import java.util.Optional;
import java.util.Scanner;

/**
 * Orchestrates one full game of Sudoku.
 *
 * Generates the puzzle, shows the board, reads commands in a loop,
 * handles each command, and ends when the player wins or quits.
 */
public class SudokuGame {

    private final SudokuGenerator generator;
    private final SudokuValidator validator;
    private final CommandParser commandParser;
    private final GridPrinter gridPrinter;
    private final Scanner scanner;

    public SudokuGame(Scanner scanner) {
        this(scanner, new SudokuGenerator());
    }

    // package-visible so tests can inject a seeded generator for deterministic behaviour
    SudokuGame(Scanner scanner, SudokuGenerator generator) {
        this.generator = generator;
        this.validator = new SudokuValidator();
        this.commandParser = new CommandParser();
        this.gridPrinter = new GridPrinter();
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("Welcome to Sudoku!");

        boolean playAgain = true;
        while (playAgain) {
            playAgain = playOneRound();
        }

        System.out.println("Thanks for playing. Goodbye!");
    }

    /**
     * Plays one full round. Returns true if the player wants another game.
     */
    private boolean playOneRound() {
        SudokuBoard board = generator.generate();

        System.out.println();
        System.out.println("Here is your puzzle:");
        gridPrinter.print(board);

        while (true) {
            System.out.println();
            System.out.print("Enter command (e.g., A3 4, C5 clear, hint, check, quit): ");
            String input = scanner.nextLine();

            Optional<Command> parsed = commandParser.parse(input);
            if (parsed.isEmpty()) {
                System.out.println("Unknown command. Try something like: A3 4, C5 clear, hint, check, quit");
                continue;
            }

            Command cmd = parsed.get();

            switch (cmd.getType()) {
                case PLACE -> handlePlace(board, cmd);
                case CLEAR -> handleClear(board, cmd);
                case HINT  -> handleHint(board);
                case CHECK -> handleCheck(board);
                case QUIT  -> {
                    System.out.println("Quitting. See you next time!");
                    return false;
                }
            }

            if (board.isComplete()) {
                Optional<String> violation = validator.validate(board);
                if (violation.isEmpty()) {
                    System.out.println();
                    System.out.println("Current grid:");
                    gridPrinter.print(board);
                    System.out.println();
                    System.out.println("You have successfully completed the Sudoku puzzle!");
                    System.out.print("Press Enter to play again, or type 'quit' to exit: ");
                    String response = scanner.nextLine();
                    return !response.trim().equalsIgnoreCase("quit");
                }
            }
        }
    }

    private void handlePlace(SudokuBoard board, Command cmd) {
        int row = cmd.getRow();
        int col = cmd.getCol();
        int value = cmd.getValue();

        String cellLabel = cellLabel(row, col);

        if (board.isPrefilled(row, col)) {
            System.out.println("Invalid move. " + cellLabel + " is pre-filled.");
        } else if (board.getCell(row, col) != 0) {
            System.out.println("Invalid move. " + cellLabel + " already has a value. Use '" + cellLabel + " clear' to remove it first.");
        } else {
            board.setCell(row, col, value);
            System.out.println("Move accepted.");
        }

        System.out.println();
        System.out.println("Current grid:");
        gridPrinter.print(board);
    }

    private void handleClear(SudokuBoard board, Command cmd) {
        int row = cmd.getRow();
        int col = cmd.getCol();

        String cellLabel = cellLabel(row, col);

        if (board.isPrefilled(row, col)) {
            System.out.println("Cannot clear " + cellLabel + ". It is pre-filled.");
        } else {
            board.clearCell(row, col);
            System.out.println("Cell " + cellLabel + " cleared.");
        }

        System.out.println();
        System.out.println("Current grid:");
        gridPrinter.print(board);
    }

    private void handleHint(SudokuBoard board) {
        // find any empty cell and reveal its correct value from the solution
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getCell(r, c) == 0) {
                    int correctValue = board.getSolutionValue(r, c);
                    board.setCell(r, c, correctValue);

                    char rowLabel = (char) ('A' + r);
                    int colLabel = c + 1;
                    System.out.println("Hint: Cell " + rowLabel + colLabel + " = " + correctValue);

                    System.out.println();
                    System.out.println("Current grid:");
                    gridPrinter.print(board);
                    return;
                }
            }
        }
        System.out.println("No empty cells left to hint!");
    }

    private void handleCheck(SudokuBoard board) {
        Optional<String> violation = validator.validate(board);
        if (violation.isPresent()) {
            System.out.println(violation.get());
        } else {
            System.out.println("No rule violations detected.");
        }
    }

    private String cellLabel(int row, int col) {
        return "" + (char) ('A' + row) + (col + 1);
    }
}

