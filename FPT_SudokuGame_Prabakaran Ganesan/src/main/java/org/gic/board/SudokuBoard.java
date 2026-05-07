package org.gic.board;

import java.util.Arrays;

/**
 * Represents the Sudoku board state.
 *
 * Holds the current cell values, which cells were pre-filled by the puzzle,
 * and the complete solution (used for hints and win detection).
 *
 * Rows are 0–8 (displayed as A–I), columns are 0–8 (displayed as 1–9).
 */
public class SudokuBoard {

    private final int[][] cells;
    private final boolean[][] prefilled;
    private final int[][] solution;

    public SudokuBoard(int[][] solution, int[][] puzzle) {
        this.solution = solution;
        this.cells = new int[9][9];
        this.prefilled = new boolean[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells[r][c] = puzzle[r][c];
                prefilled[r][c] = puzzle[r][c] != 0;
            }
        }
    }

    public int getCell(int row, int col) {
        return cells[row][col];
    }

    public boolean isPrefilled(int row, int col) {
        return prefilled[row][col];
    }

    /**
     * Places a number in a cell. Returns false if the cell is pre-filled.
     */
    public boolean setCell(int row, int col, int value) {
        if (prefilled[row][col]) {
            return false;
        }
        cells[row][col] = value;
        return true;
    }

    /**
     * Clears a user-entered cell. Returns false if the cell is pre-filled.
     */
    public boolean clearCell(int row, int col) {
        if (prefilled[row][col]) {
            return false;
        }
        cells[row][col] = 0;
        return true;
    }

    public int getSolutionValue(int row, int col) {
        return solution[row][col];
    }

    /**
     * Returns true when every cell is filled in (doesn't check correctness here).
     */
    public boolean isComplete() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (cells[r][c] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a snapshot of the current grid for validation purposes.
     */
    public int[][] getCells() {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            copy[r] = Arrays.copyOf(cells[r], 9);
        }
        return copy;
    }
}

