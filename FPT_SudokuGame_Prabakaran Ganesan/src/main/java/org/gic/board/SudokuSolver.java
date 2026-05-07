package org.gic.board;

/**
 * Solves a Sudoku grid using recursive backtracking.
 *
 * Works on a raw int[][] grid (0 = empty cell).
 * Modifies the grid in place — caller should pass a copy if the original
 * needs to be preserved.
 */
public class SudokuSolver {

    /**
     * Attempts to solve the grid. Returns true if a solution was found.
     * The grid is modified in place with the solution values.
     */
    public boolean solve(int[][] grid) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (canPlace(grid, r, c, num)) {
                            grid[r][c] = num;

                            if (solve(grid)) {
                                return true;
                            }

                            // this number didn't lead to a solution, undo and try next
                            grid[r][c] = 0;
                        }
                    }
                    // no number worked in this cell — dead end, backtrack
                    return false;
                }
            }
        }
        // no empty cells left — solved!
        return true;
    }

    /**
     * Checks if placing 'num' at (row, col) would violate any Sudoku rule.
     */
    public boolean canPlace(int[][] grid, int row, int col, int num) {
        // check row and column together
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }

        // check the 3x3 subgrid
        int boxStartRow = (row / 3) * 3;
        int boxStartCol = (col / 3) * 3;
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartCol; c < boxStartCol + 3; c++) {
                if (grid[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}

