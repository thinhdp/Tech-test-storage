package game.ports;

import game.domain.Board;

public interface IDisplay {
	void display(Board board);

	default void display(Board board, int step) {
		display(board);
	}
}