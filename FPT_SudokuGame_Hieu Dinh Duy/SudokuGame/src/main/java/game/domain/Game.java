package game.domain;

public class Game {
	private final Board puzzle;
	private final Board solution;
	
	public Game(Board puzzle, Board solution) {
		this.puzzle = puzzle;
		this.solution = solution;
	}

	public Board getPuzzle() {
		return puzzle;
	}

	public Board getSolution() {
		return solution;
	}
}
