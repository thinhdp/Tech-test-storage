package org.gic;

import org.gic.game.SudokuGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SudokuGame game = new SudokuGame(scanner);
        game.start();
    }
}