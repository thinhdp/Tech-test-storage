package game.domain;

import java.util.regex.Pattern;

public class SudokuRules {

	public static final int GRID_SIZE = 9;
	public static final int BOX_SIZE = 3;
	private static final String USER_INPUT_REGEX = "(?i)([A-I][1-9]\\s([1-9]|CLEAR)|QUIT|CHECK|HINT|RESOLVE|GENERATE)";
	private static final Pattern pattern = Pattern.compile(USER_INPUT_REGEX);
	
	public enum CellField {
		ROW, COLUMN, BOX
	}
	
	private SudokuRules() {}
	
	public static boolean isValidUserInput(String userInput) {
		if (userInput == null || userInput.isEmpty())
			return false;
		return pattern.matcher(userInput.trim()).matches();
	}

	public static int countOccurrencesInRowOrColumn(Board board, int rowOrColumn, int number,
			CellField cellField) {
		int count = 0;
		for (int index = 0; index < GRID_SIZE; index++) {
			if (CellField.ROW.equals(cellField)) {
				if (board.get(rowOrColumn, index) == number)
					count++;
			} else if (CellField.COLUMN.equals(cellField)) {
				if (board.get(index, rowOrColumn) == number)
					count++;
			}
		}
		return count;
	}

	// The number must be between 1-9
	public static boolean isValidNumberInput(int input) {
		return input >= 1 && input <= 9;
	}

	public static int countOccurrencesInBox(Board board, int row, int column, int number) {
		int count = 0;
		int startRowPosition = (row / BOX_SIZE) * BOX_SIZE;
		int startColumnPosition = (column / BOX_SIZE) * BOX_SIZE;

		for (int currentRow = startRowPosition; currentRow < startRowPosition + BOX_SIZE; currentRow++) {
			for (int currentColumn = startColumnPosition; currentColumn <  startColumnPosition + BOX_SIZE; currentColumn++) {
				if (board.get(currentRow, currentColumn) == number)
					count++;
			}
		}
		return count;
	}

	public static boolean isValidNumber(Board board, int row, int column, int number) {
		return isValidNumberInput(number)
				&& countOccurrencesInRowOrColumn(board, column, number, CellField.COLUMN) == 0
				&& countOccurrencesInRowOrColumn(board, row, number, CellField.ROW) == 0
				&& countOccurrencesInBox(board, row, column, number) == 0;
	}
}