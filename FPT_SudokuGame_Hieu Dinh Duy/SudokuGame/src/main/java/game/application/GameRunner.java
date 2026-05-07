package game.application;

import java.util.Scanner;

import game.ports.IDisplay;

public class GameRunner {

	private final CommandProcessor processor;
	private final GameService service;
	private final IDisplay display;
	public static final int NUMBER_OF_PRE_FILL_CELLS = 30;

	public GameRunner(CommandProcessor processor, GameService service, IDisplay display) {
		this.processor = processor;
		this.service = service;
		this.display = display;
	}

	public void run(Scanner scanner) {
		service.generateNewGame(NUMBER_OF_PRE_FILL_CELLS);

		while (true) {
			display.display(service.getCurrentBoard());
			System.out.print("\nEnter command (e.g: A3 4, C5 clear, hint, check, quit, generate):");

			String input = scanner.nextLine();
			Command command = processor.parse(input);
			try {
				CommandProcessor.CommandResult result = processor.execute(command, service, service.getCurrentBoard());

				System.out.println(result.message);

				if (result.exit)
					break;

				if ((command.getType() == Command.Type.MOVE || command.getType() == Command.Type.RESOLVE)
						&& service.isSolved(service.getCurrentBoard())) {
					display.display(service.getCurrentBoard());
					System.out.println("\nYou have successfully completed the Sudoku puzzle!");
				}

			} catch (UnsupportedOperationException e) {
				if (command.getType() == Command.Type.MOVE || command.getType() == Command.Type.CLEAR) {
					String position = toPosition(command.getRow(), command.getColumn());

					System.out.printf("Invalid move. Cell %s is pre-filled.%n", position);
				} else {
					System.out.println("Error " + e.getMessage());
				}
			} catch (Exception e) {
				System.out.println("Unexpected error: " + e.getMessage());
			}
		}
	}

	private String toPosition(int row, int column) {
		return String.format("%c%d", (char) ('A' + row), column + 1);
	}

	public GameService getService() {
		return service;
	}
}