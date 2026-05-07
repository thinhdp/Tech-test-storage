package game.ports;

import game.domain.Game;

public interface IGenerator {
	Game generate(int numberOfPreFillCells);
}