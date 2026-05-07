package game.implementors;

import game.ports.IRandom;

import java.util.Random;

public class ImplementRandom implements IRandom {
	
	@Override
	public Random random() {
		return new Random();
	}
}