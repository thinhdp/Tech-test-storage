package game.application;

public class Command {
	public enum Type {
		MOVE, CLEAR, QUIT, HINT, RESOLVE, GENERATE, CHECK, INVALID
	}

	private final Type type;
	private final int row;
	private final int column;
	private final Integer value;

	public Type getType() {
		return type;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Integer getValue() {
		return value;
	}

	public Command(Type type, int row, int column, Integer value) {
		this.type = type;
		this.row = row;
		this.column = column;
		this.value = value;
	}
}
