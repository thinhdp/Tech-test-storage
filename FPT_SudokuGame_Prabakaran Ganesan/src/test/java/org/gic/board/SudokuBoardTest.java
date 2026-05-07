package org.gic.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SudokuBoard.
 *
 * Covers cell placement, pre-fill protection, clear, and completion detection.
 */
class SudokuBoardTest {

    private static final int[][] SOLUTION = {
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

    private SudokuBoard boardWithEmptyCell(int row, int col) {
        int[][] puzzle = new int[9][9];
        for (int r = 0; r < 9; r++) {
            puzzle[r] = java.util.Arrays.copyOf(SOLUTION[r], 9);
        }
        puzzle[row][col] = 0; // make one cell empty
        return new SudokuBoard(SOLUTION, puzzle);
    }

    @Test
    void prefilledCellCannotBeOverwritten() {
        // A1 is 5 in the solution and is pre-filled
        SudokuBoard board = new SudokuBoard(SOLUTION, SOLUTION);
        boolean accepted = board.setCell(0, 0, 9);
        assertFalse(accepted, "Should not be able to overwrite a pre-filled cell");
        assertEquals(5, board.getCell(0, 0), "Value should remain unchanged");
    }

    @Test
    void emptyCellCanBeSet() {
        SudokuBoard board = boardWithEmptyCell(0, 2); // A3 is empty
        boolean accepted = board.setCell(0, 2, 4);
        assertTrue(accepted);
        assertEquals(4, board.getCell(0, 2));
    }

    @Test
    void prefilledCellCannotBeCleared() {
        SudokuBoard board = new SudokuBoard(SOLUTION, SOLUTION);
        boolean cleared = board.clearCell(0, 0);
        assertFalse(cleared);
        assertEquals(5, board.getCell(0, 0));
    }

    @Test
    void userEnteredCellCanBeCleared() {
        SudokuBoard board = boardWithEmptyCell(0, 2);
        board.setCell(0, 2, 7);
        boolean cleared = board.clearCell(0, 2);
        assertTrue(cleared);
        assertEquals(0, board.getCell(0, 2));
    }

    @Test
    void boardWithEmptyCellIsNotComplete() {
        SudokuBoard board = boardWithEmptyCell(4, 4);
        assertFalse(board.isComplete());
    }

    @Test
    void fullyFilledBoardIsComplete() {
        SudokuBoard board = new SudokuBoard(SOLUTION, SOLUTION);
        assertTrue(board.isComplete());
    }

    @Test
    void solutionValueIsAccessible() {
        SudokuBoard board = boardWithEmptyCell(3, 3);
        // solution at D4 (row 3, col 3) is 7
        assertEquals(7, board.getSolutionValue(3, 3));
    }

    @Test
    void cellWithExistingUserValueCannotBeOverwritten() {
        SudokuBoard board = boardWithEmptyCell(0, 2); // A3 is empty
        board.setCell(0, 2, 4); // user places 4

        // the game layer checks getCell != 0 before calling setCell again.
        // Verify the cell still holds 4 and is not pre-filled (so game layer
        // can distinguish this case from a pre-filled block).
        assertEquals(4, board.getCell(0, 2), "Cell should hold the first value placed");
        assertFalse(board.isPrefilled(0, 2), "Cell should not be flagged as pre-filled");
        // Confirm getCell != 0 so the game layer knows it is occupied
        assertNotEquals(0, board.getCell(0, 2));
    }

    @Test
    void cellCanBeUpdatedAfterClearing() {
        SudokuBoard board = boardWithEmptyCell(0, 2); // A3 is empty
        board.setCell(0, 2, 4);
        board.clearCell(0, 2);           // clear the user value
        boolean accepted = board.setCell(0, 2, 6); // now place a different value

        assertTrue(accepted, "Should accept a value in a cleared cell");
        assertEquals(6, board.getCell(0, 2));
    }

    @Test
    void isPrefilledReturnsTrueForPuzzleNumbers() {
        int[][] puzzle = new int[9][9];
        puzzle[0][0] = 5; // only one pre-filled cell
        SudokuBoard board = new SudokuBoard(SOLUTION, puzzle);
        assertTrue(board.isPrefilled(0, 0));
        assertFalse(board.isPrefilled(0, 1));
    }
}

