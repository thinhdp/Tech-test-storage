package org.gic.display;

import org.gic.board.SudokuBoard;

/**
 * Prints the Sudoku grid to stdout in the format shown in the spec:
 *
 *     1 2 3 4 5 6 7 8 9
 *   A 5 3 _ _ 7 _ _ _ _
 *   B 6 _ _ 1 9 5 _ _ _
 *   ...
 */
public class GridPrinter {

    public void print(SudokuBoard board) {
        System.out.println("    1 2 3 4 5 6 7 8 9");

        for (int r = 0; r < 9; r++) {
            char rowLabel = (char) ('A' + r);
            System.out.print("  " + rowLabel + " ");

            for (int c = 0; c < 9; c++) {
                int val = board.getCell(r, c);
                System.out.print(val == 0 ? "_" : val);

                if (c < 8) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}

