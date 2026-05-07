package org.gic.board;

import java.util.Optional;

/**
 * Validates the current state of the Sudoku board.
 *
 * Checks three Sudoku rules:
 *   1. No duplicate numbers in any row
 *   2. No duplicate numbers in any column
 *   3. No duplicate numbers in any 3×3 subgrid
 *
 * Returns the first violation found, or empty if everything looks fine.
 * Skips empty cells (value = 0) since they aren't considered violations yet.
 */
public class SudokuValidator {

    public Optional<String> validate(SudokuBoard board) {
        int[][] cells = board.getCells();

        for (int r = 0; r < 9; r++) {
            Optional<String> rowError = checkRow(cells, r);
            if (rowError.isPresent()) {
                return rowError;
            }
        }

        for (int c = 0; c < 9; c++) {
            Optional<String> colError = checkColumn(cells, c);
            if (colError.isPresent()) {
                return colError;
            }
        }

        for (int boxRow = 0; boxRow < 9; boxRow += 3) {
            for (int boxCol = 0; boxCol < 9; boxCol += 3) {
                Optional<String> subgridError = checkSubgrid(cells, boxRow, boxCol);
                if (subgridError.isPresent()) {
                    return subgridError;
                }
            }
        }

        return Optional.empty();
    }

    private Optional<String> checkRow(int[][] cells, int row) {
        boolean[] seen = new boolean[10]; // index 1–9

        for (int c = 0; c < 9; c++) {
            int val = cells[row][c];
            if (val == 0) continue;

            if (seen[val]) {
                char rowLabel = (char) ('A' + row);
                return Optional.of("Number " + val + " already exists in Row " + rowLabel + ".");
            }
            seen[val] = true;
        }
        return Optional.empty();
    }

    private Optional<String> checkColumn(int[][] cells, int col) {
        boolean[] seen = new boolean[10];

        for (int r = 0; r < 9; r++) {
            int val = cells[r][col];
            if (val == 0) continue;

            if (seen[val]) {
                return Optional.of("Number " + val + " already exists in Column " + (col + 1) + ".");
            }
            seen[val] = true;
        }
        return Optional.empty();
    }

    private Optional<String> checkSubgrid(int[][] cells, int startRow, int startCol) {
        boolean[] seen = new boolean[10];

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                int val = cells[r][c];
                if (val == 0) continue;

                if (seen[val]) {
                    return Optional.of("Number " + val + " already exists in the same 3×3 subgrid.");
                }
                seen[val] = true;
            }
        }
        return Optional.empty();
    }
}

