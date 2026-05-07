package game;

import java.util.Scanner;

import game.application.CommandProcessor;
import game.application.GameRunner;
import game.application.GameService;
import game.implementors.ImplementBasicGenerator;
import game.implementors.ImplementBruteForceResolver;
import game.implementors.ImplementConsoleDisplay;
import game.implementors.ImplementRandom;
import game.ports.IDisplay;
import game.ports.IGenerator;
import game.ports.IRandom;
import game.ports.IResolver;

public class MainProgram {

	public static void main(String[] args) {

		IRandom random = new ImplementRandom();
		IGenerator generator = new ImplementBasicGenerator(random);

		IDisplay display = new ImplementConsoleDisplay();
		IResolver resolver = new ImplementBruteForceResolver(display);

		CommandProcessor processor = new CommandProcessor();
		GameService service = new GameService(generator, resolver);
		GameRunner runner = new GameRunner(processor, service, display);

		System.out.println("Welcome to Sudoku!");
		System.out.println("Here is your puzzle:\n");

		try (Scanner scanner = new Scanner(System.in)) {
			runner.run(scanner);
		}
	}
}