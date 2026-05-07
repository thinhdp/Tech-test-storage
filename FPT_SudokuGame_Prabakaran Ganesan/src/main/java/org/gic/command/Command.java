package org.gic.command;

/**
 * Represents a parsed player command.
 *
 * Examples of what users type:
 *   "A3 4"      → PLACE row=0, col=2, value=4
 *   "C5 clear"  → CLEAR row=2, col=4
 *   "hint"      → HINT
 *   "check"     → CHECK
 *   "quit"      → QUIT
 */
public class Command {

    public enum Type {
        PLACE, CLEAR, HINT, CHECK, QUIT
    }

    private final Type type;
    private final int row;   // 0-based (0 = row A, 8 = row I)
    private final int col;   // 0-based (0 = column 1, 8 = column 9)
    private final int value; // only meaningful for PLACE

    private Command(Type type, int row, int col, int value) {
        this.type = type;
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public static Command place(int row, int col, int value) {
        return new Command(Type.PLACE, row, col, value);
    }

    public static Command clear(int row, int col) {
        return new Command(Type.CLEAR, row, col, 0);
    }

    public static Command hint() {
        return new Command(Type.HINT, -1, -1, 0);
    }

    public static Command check() {
        return new Command(Type.CHECK, -1, -1, 0);
    }

    public static Command quit() {
        return new Command(Type.QUIT, -1, -1, 0);
    }

    public Type getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }
}

