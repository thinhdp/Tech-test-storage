package game.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CellTest {

	@Test
	void shouldCreateNewCellWithValue() {
		Cell cell = new Cell(0, 0, false, 1);

		Cell newCell = cell.withValue(5);

		assertEquals(1, cell.getValue());
		assertEquals(5, newCell.getValue());
	}

	@Test
	void shouldThrowWhenFixedCellUpdated() {
		Cell cell = new Cell(0, 0, true, 1);

		assertThrows(UnsupportedOperationException.class, () -> cell.withValue(5));
	}
}
