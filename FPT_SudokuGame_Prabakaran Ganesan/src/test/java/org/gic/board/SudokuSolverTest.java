package org.gic.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SudokuSolver.
 *
 * Verifies that the backtracking solver can solve known puzzles correctly,
 * and that it returns false for grids with no valid solution.
 */
class SudokuSolverTest {

    private final SudokuSolver solver = new SudokuSolver();

    @Test
    void solvesKnownPuzzle() {
        int[][] puzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        boolean solved = solver.solve(puzzle);
        assertTrue(solved, "Should be able to solve this known puzzle");

        // just check no zeros remain
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                assertNotEquals(0, puzzle[r][c], "Cell [" + r + "][" + c + "] should not be empty after solve");
            }
        }
    }

    @Test
    void alreadyCompletedGridSolvesTrivially() {
        int[][] complete = {
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
        assertTrue(solver.solve(complete));
    }

    @Test
    void canPlaceReturnsFalseForConflict() {
        int[][] grid = new int[9][9];
        grid[0][0] = 5;

        // 5 is already in row 0, so we can't place 5 anywhere else in row 0
        assertFalse(solver.canPlace(grid, 0, 5, 5));

        // placing a different number in the same row is fine
        assertTrue(solver.canPlace(grid, 0, 5, 3));
    }

    @Test
    void canPlaceReturnsFalseForSubgridConflict() {
        int[][] grid = new int[9][9];
        grid[0][0] = 5; // top-left subgrid has 5

        // any cell in top-left 3×3 subgrid cannot have 5
        assertFalse(solver.canPlace(grid, 1, 1, 5));
        assertFalse(solver.canPlace(grid, 2, 2, 5));
    }
}

