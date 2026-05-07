package game.implementors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import game.TestUtility;
import game.domain.Board;
import game.ports.IRandom;

public class GeneratorTest {

	@Test
	void shouldGenerateBoardWithPrefillCells() {
		IRandom random = new ImplementRandom();
		ImplementBasicGenerator generator = new ImplementBasicGenerator(random);

		Board board = generator.generate(TestUtility.NUMBER_OF_PRE_FILL_CELLS).getPuzzle();

		int notEmpty = 0;

		for (int row = 0; row < 9; row++)
			for (int column = 0; column < 9; column++)
				if (board.get(row, column) != 0)
					notEmpty++;

		assertEquals(TestUtility.NUMBER_OF_PRE_FILL_CELLS, notEmpty);
	}
}