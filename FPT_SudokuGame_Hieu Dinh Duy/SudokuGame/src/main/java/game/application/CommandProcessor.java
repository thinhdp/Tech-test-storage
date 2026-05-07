package game.application;

import game.domain.Board;
import game.domain.SudokuRules;

public class CommandProcessor {

	public static class CommandResult {
		public final String message;
		public final boolean exit;
		public final Board newBoard;

		public CommandResult(String message, boolean exit, Board newBoard) {
			this.message = message;
			this.exit = exit;
			this.newBoard = newBoard;
		}
	}

	public Command parse(String input) {
		if (!SudokuRules.isValidUserInput(input)) {
			return new Command(Command.Type.INVALID, -1, -1, null);
		}
		input = input.trim();
		if ("quit".equalsIgnoreCase(input))
			return new Command(Command.Type.QUIT, -1, -1, null);
		if ("hint".equalsIgnoreCase(input))
			return new Command(Command.Type.HINT, -1, -1, null);
		if ("resolve".equalsIgnoreCase(input))
			return new Command(Command.Type.RESOLVE, -1, -1, null);
		if ("generate".equalsIgnoreCase(input))
			return new Command(Command.Type.GENERATE, -1, -1, null);
		if ("check".equalsIgnoreCase(input))
			return new Command(Command.Type.CHECK, -1, -1, null);

		String[] parts = input.split(" ");
		if (parts.length == 2) {
			String position = parts[0].toUpperCase();
			int row = position.charAt(0) - 'A';
			int column = position.charAt(1) - '1';

			if ("clear".equalsIgnoreCase(parts[1])) {
				return new Command(Command.Type.CLEAR, row, column, null);
			}

			try {
				int number = Integer.parseInt(parts[1]);
				return new Command(Command.Type.MOVE, row, column, number);
			} catch (NumberFormatException e) {
				return new Command(Command.Type.INVALID, -1, -1, null);
			}
		}

		return new Command(Command.Type.INVALID, -1, -1, null);
	}

	public CommandResult execute(Command cmd, GameService service, Board board) {
		switch (cmd.getType()) {
		case QUIT:
			return new CommandResult("Exiting...", true, board);
		case HINT:
			return new CommandResult(service.hint(board), false, board);
		case RESOLVE:
			service.solve(board);
			return new CommandResult("\nSolved!", false, board);
		case GENERATE:
			return new CommandResult("New Board Generated", false, service.generateNewGame(GameRunner.NUMBER_OF_PRE_FILL_CELLS));
		case CHECK:
			return new CommandResult(service.check(board), false, board);
		case CLEAR:
			service.clear(board, cmd.getRow(), cmd.getColumn());
			return new CommandResult("Cell cleared", false, board);
		case MOVE:
			if(SudokuRules.isValidNumberInput(cmd.getValue())) {
				service.play(board, cmd.getRow(), cmd.getColumn(), cmd.getValue());
				return new CommandResult("Move accepted", false, board);
			} else {
				return new CommandResult("Invalid option, Enter command (e.g: A3 4, C5 clear, hint, check, quit, generate):", false, board);
			}
		default:
			return new CommandResult("Invalid option, Enter command (e.g: A3 4, C5 clear, hint, check, quit, generate):", false, board);
		}
	}
}