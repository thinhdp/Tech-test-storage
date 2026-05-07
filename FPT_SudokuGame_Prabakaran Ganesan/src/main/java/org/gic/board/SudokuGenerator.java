package org.gic.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generates a Sudoku puzzle.
 *
 * Strategy:
 * 1. Start with an empty grid and fill it using the solver with randomized
 *    number order — this gives us a complete, valid solution.
 * 2. Remove 51 numbers at random to leave 30 pre-filled cells.
 *
 * The resulting SudokuBoard stores both the puzzle state and the full solution,
 * so the game can show hints without needing to re-solve on the fly.
 */
public class SudokuGenerator {

    // 81 total cells - 30 pre-filled = 51 cells to remove
    private static final int CELLS_TO_REMOVE = 51;

    private final Random random;

    public SudokuGenerator() {
        this.random = new Random();
    }

    // constructor for tests so we can control randomness with a seed
    public SudokuGenerator(long seed) {
        this.random = new Random(seed);
    }

    public SudokuBoard generate() {
        int[][] solution = new int[9][9];
        fillCompleteGrid(solution);

        int[][] puzzle = copyGrid(solution);
        removeCells(puzzle, CELLS_TO_REMOVE);

        return new SudokuBoard(solution, puzzle);
    }

    /**
     * Fills the grid using backtracking with a shuffled number list at each cell.
     * The shuffle makes sure every call produces a different puzzle.
     */
    private boolean fillCompleteGrid(int[][] grid) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid[r][c] == 0) {
                    List<Integer> candidates = shuffledNumbers();
                    for (int num : candidates) {
                        if (canPlace(grid, r, c, num)) {
                            grid[r][c] = num;

                            if (fillCompleteGrid(grid)) {
                                return true;
                            }

                            grid[r][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Randomly removes 'count' cells from the grid.
     */
    private void removeCells(int[][] grid, int count) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions, random);

        for (int i = 0; i < count; i++) {
            int pos = positions.get(i);
            grid[pos / 9][pos % 9] = 0;
        }
    }

    private boolean canPlace(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (grid[r][c] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> shuffledNumbers() {
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(nums, random);
        return nums;
    }

    private int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            copy[r] = Arrays.copyOf(grid[r], 9);
        }
        return copy;
    }
}

