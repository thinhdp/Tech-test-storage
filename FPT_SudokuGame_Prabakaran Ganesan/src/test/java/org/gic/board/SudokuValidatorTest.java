package org.gic.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SudokuValidator.
 *
 * We set up a known board so we can predict exactly which violations
 * should be detected.
 */
class SudokuValidatorTest {

    private SudokuValidator validator;

    // this is the classic sample puzzle from the spec
    private static final int[][] SAMPLE_SOLUTION = {
        {5, 3, 4, 6, 7, 8, 9, 1, 2},
        {6, 7, 2, 1, 9, 5, 3, 4, 8},
        {1, 9, 8, 3, 4, 2, 5, 6, 7},
        {8, 5, 9, 7, 6, 1, 4, 2, 3},
        {4, 2, 6, 8, 5, 3, 7, 9, 1},
        {7, 1, 3, 9, 2, 4, 8, 5, 6},
        {9, 6, 1, 5, 3, 7, 2, 8, 4},
        {2, 8, 7, 4, 1, 9, 6, 3, 5},
        {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    @BeforeEach
    void setUp() {
        validator = new SudokuValidator();
    }

    @Test
    void validCompletedBoardShouldPassValidation() {
        SudokuBoard board = new SudokuBoard(SAMPLE_SOLUTION, SAMPLE_SOLUTION);
        Optional<String> result = validator.validate(board);
        assertTrue(result.isEmpty(), "A correctly completed board should have no violations");
    }

    @Test
    void duplicateInRowShouldBeDetected() {
        // place 3 again in row A (row A already has 3 at column 2)
        int[][] puzzle = copyGrid(SAMPLE_SOLUTION);
        puzzle[0][2] = 0; // clear A3 so it's not prefilled
        SudokuBoard board = new SudokuBoard(SAMPLE_SOLUTION, puzzle);
        board.setCell(0, 2, 3); // A3 = 3, but A2 is also 3

        Optional<String> result = validator.validate(board);
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("Row A"), "Should mention Row A: " + result.get());
    }

    @Test
    void duplicateInColumnShouldBeDetected() {
        // Use a minimal grid: only put 5 in A1 (pre-filled) and C1 (user-entered).
        // No row duplicates, so the column check fires first.
        int[][] solution = new int[9][9];
        int[][] puzzle = new int[9][9];
        puzzle[0][0] = 5; // A1 = 5, pre-filled

        SudokuBoard board = new SudokuBoard(solution, puzzle);
        board.setCell(2, 0, 5); // C1 = 5, same column as A1

        Optional<String> result = validator.validate(board);
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("Column 1"), "Should mention Column 1: " + result.get());
    }

    @Test
    void duplicateInSubgridShouldBeDetected() {
        // Use a minimal grid: only put 8 in A1 (pre-filled) and B3 (user-entered).
        // Both are in the top-left 3×3 subgrid. No row/column duplicates.
        int[][] solution = new int[9][9];
        int[][] puzzle = new int[9][9];
        puzzle[0][0] = 8; // A1 = 8, pre-filled

        SudokuBoard board = new SudokuBoard(solution, puzzle);
        board.setCell(1, 2, 8); // B3 = 8, same top-left 3×3 subgrid as A1

        Optional<String> result = validator.validate(board);
        assertTrue(result.isPresent());
        assertTrue(result.get().contains("3×3 subgrid"), "Should mention subgrid: " + result.get());
    }

    @Test
    void partiallyFilledBoardWithNoViolationsShouldPass() {
        // board where some cells are empty — no duplicates
        int[][] puzzle = new int[9][9]; // all zeros
        puzzle[0][0] = 5;
        puzzle[0][1] = 3;
        SudokuBoard board = new SudokuBoard(SAMPLE_SOLUTION, puzzle);

        Optional<String> result = validator.validate(board);
        assertTrue(result.isEmpty(), "Partial board with no duplicates should pass");
    }

    private int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            copy[r] = java.util.Arrays.copyOf(grid[r], 9);
        }
        return copy;
    }
}

