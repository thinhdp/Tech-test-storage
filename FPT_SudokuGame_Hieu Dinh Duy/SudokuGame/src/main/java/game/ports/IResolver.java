package game.ports;

import game.domain.Board;

public interface IResolver {
	boolean resolve(Board board);

	String hint(Board board, Board solution);

	String check(Board board, int row, int column, int number);
}