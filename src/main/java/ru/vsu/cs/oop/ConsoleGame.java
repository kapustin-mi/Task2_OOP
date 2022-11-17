package ru.vsu.cs.oop;

import ru.vsu.cs.oop.ChineseCheckers.Cell;
import ru.vsu.cs.oop.ChineseCheckers.Game;
import ru.vsu.cs.oop.ChineseCheckers.Player;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ConsoleGame {
    private static final Color[] PLAYERS_COLOR = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.MAGENTA,
            Color.CYAN};

    private static final HashMap<Color, String> CONSOLE_COLORS;

    static {
        CONSOLE_COLORS = new HashMap<>();

        CONSOLE_COLORS.put(Color.RED, "\033[0;31m");
        CONSOLE_COLORS.put(Color.GREEN, "\033[0;32m");
        CONSOLE_COLORS.put(Color.YELLOW, "\033[0;33m");
        CONSOLE_COLORS.put(Color.BLUE, "\033[0;34m");
        CONSOLE_COLORS.put(Color.MAGENTA, "\033[0;35m");
        CONSOLE_COLORS.put(Color.CYAN, "\033[0;36m");
    }

    private static final String DEFAULT_COLOR = "\033[0m";

    private static final int[] SPACES = {12, 11, 10, 9, 0, 1, 2, 3, 4, 3, 2, 1, 0, 9, 10, 11, 12};

    public static void main(String[] args) {
        int numberOfPlayers = readNumberOfPlayers();
        List<Player> players = createPlayers(numberOfPlayers);

        Game game = new Game(players);

        Cell[][] field = game.getField();
        printField(game, field);

        while (!game.isGameEnd()) {
            if (makeMove(game)) {
                printField(game, field);
            } else {
                System.out.println("Repeat move.");
            }
        }

        printWinner(game);
    }

    /* для обращения к ячейке на поле нужно указать индекс горизонтали и индекс вертикали */
    private static boolean makeMove(Game game) {
        String move = readMove();
        if (move.equals("end")) {
            game.endMove();
            return true;
        }

        try {
            String[] indices = move.split(" ");
            int[] intIndices = new int[4];
            for (int i = 0; i < intIndices.length; i++) {
                intIndices[i] = Integer.parseInt(indices[i]);
            }

            return game.makeMove(intIndices[0], intIndices[1], intIndices[2], intIndices[3]);
        } catch (ClassCastException e) {
            return false;
        }
    }

    private static List<Player> createPlayers(int numberOfPlayers) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(PLAYERS_COLOR[i]));
        }

        return players;
    }

    private static String readMove() {
        Scanner scn = new Scanner(System.in);
        return scn.nextLine();
    }

    private static int readNumberOfPlayers() {
        Scanner scn = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        int players = 0;

        if (scn.hasNextInt()) {
            players = scn.nextInt();
        }

        if (players < 7 && players > 1 && players != 5) {
            return players;
        } else {
            System.out.println("Please, try again...");
            return readNumberOfPlayers();
        }
    }

    private static void printField(Game game, Cell[][] field) {
        Color cellColor;

        System.out.println("Mover : " + CONSOLE_COLORS.get(game.getMover().getPlayerColor()) + "0" + DEFAULT_COLOR);
        for (int r = 0; r < field.length; r++) {
            for (int i = 0; i < SPACES[r]; i++) {
                System.out.print(" ");
            }

            for (Cell cell : field[r]) {
                cellColor = cell.getCellColor();
                if (cellColor == null) {
                    System.out.print("0 ");
                } else {
                    System.out.print(CONSOLE_COLORS.get(cellColor) + "0 " + DEFAULT_COLOR);
                }
            }

            System.out.println();
        }
    }

    private static void printWinner(Game game) {
        System.out.println("Won : " + CONSOLE_COLORS.get(game.getWinner().getPlayerColor()) + "0.");
    }
}
