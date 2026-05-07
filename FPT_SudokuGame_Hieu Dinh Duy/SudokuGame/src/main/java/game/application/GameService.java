package game.application;

import game.domain.Board;
import game.domain.Game;
import game.domain.SudokuRules;
import game.ports.IGenerator;
import game.ports.IResolver;

public class GameService {

	private final IGenerator generator;
	private final IResolver resolver;
	private Game currentGame;

	public GameService(IGenerator generator, IResolver resolver) {
		this.generator = generator;
		this.resolver = resolver;
	}

	public Board getSolution() {
		requireGameStarted();
		return currentGame.getSolution();
	}

	public Board getCurrentBoard() {
		requireGameStarted();
		return currentGame.getPuzzle();
	}

	public Board generateNewGame(int numberOfPreFillCells) {
		currentGame = generator.generate(numberOfPreFillCells);
		return currentGame.getPuzzle();
	}

	public void play(Board board, int row, int column, int number) {
		if (!SudokuRules.isValidNumberInput(number)) {
			throw new IllegalArgumentException("Number must be between 1 and 9");
		}
		board.set(row, column, number);
	}

	public void clear(Board board, int row, int column) {
		board.set(row, column, 0);
	}

	public boolean solve(Board board) {
		return resolver.resolve(board);
	}

	public String hint(Board board) {
		requireGameStarted();
		return resolver.hint(board, currentGame.getSolution());
	}

	public String check(Board board) {
		for (int row = 0; row < SudokuRules.GRID_SIZE; row++) {
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++) {
				String message = resolver.check(board, row, column, board.get(row, column));
				if (!message.contains("No rule violations")) {
					return message;
				}
			}
		}
		return "No rule violations detected...";
	}

	public boolean isSolved(Board board) {
		for (int row = 0; row < SudokuRules.GRID_SIZE; row++) {
			for (int column = 0; column < SudokuRules.GRID_SIZE; column++) {
				if (board.isEmpty(row, column))
					return false;
			}
		}
		// Delegate to check() - it validates ALL cells (fixed and non-fixed) without mutating the board
		return check(board).contains("No rule violations");
	}

	private void requireGameStarted() {
		if (currentGame == null) {
			throw new IllegalStateException("No game in progress. Call generateNewGame() first.");
		}
	}
}