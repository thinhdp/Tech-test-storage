package org.gic.board;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SudokuGenerator.
 *
 * We use a fixed seed so the tests are deterministic and reproducible.
 */
class SudokuGeneratorTest {

    @Test
    void generatedPuzzleHasExactly30PrefilledCells() {
        SudokuGenerator generator = new SudokuGenerator(42L);
        SudokuBoard board = generator.generate();

        int prefilledCount = 0;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.isPrefilled(r, c)) {
                    prefilledCount++;
                }
            }
        }
        assertEquals(30, prefilledCount, "Puzzle should have exactly 30 pre-filled cells");
    }

    @Test
    void generatedPuzzleSolutionIsValid() {
        SudokuGenerator generator = new SudokuGenerator(99L);
        SudokuBoard board = generator.generate();

        // fill all cells with the solution values and validate
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (!board.isPrefilled(r, c)) {
                    board.setCell(r, c, board.getSolutionValue(r, c));
                }
            }
        }

        SudokuValidator validator = new SudokuValidator();
        Optional<String> violation = validator.validate(board);
        assertTrue(violation.isEmpty(), "Solution should be valid: " + violation.orElse(""));
    }

    @Test
    void prefilledCellsMatchTheSolution() {
        SudokuGenerator generator = new SudokuGenerator(7L);
        SudokuBoard board = generator.generate();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.isPrefilled(r, c)) {
                    assertEquals(
                        board.getSolutionValue(r, c),
                        board.getCell(r, c),
                        "Pre-filled cell [" + r + "][" + c + "] should match the solution"
                    );
                }
            }
        }
    }
}

