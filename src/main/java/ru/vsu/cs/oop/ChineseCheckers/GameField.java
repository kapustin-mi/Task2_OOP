package ru.vsu.cs.oop.ChineseCheckers;

import java.awt.*;
import java.util.List;

class GameField {
    public static final int FIELD_HEIGHT = 17;
    public static final int FIELD_WIDTH = 25;
    public static final int CORNER_HEIGHT = 4;

    private final Cell[][] field;

    public GameField(List<Player> players) {
        field = new Cell[FIELD_HEIGHT][FIELD_WIDTH];

        initField(players);
    }

    private void initField(List<Player> players) {
        initCentreCells();

        switch (players.size()) {
            case 2 -> initFieldFor2Players(players);
            case 3 -> initFieldFor3Players(players);
            case 4 -> initFieldFor4Players(players);
            case 6 -> initFieldFor6Players(players);
        }
    }

    private void initCentreCells() {
        int startRowLength = 5, rowLength = 5, startRow = 4, endRow = 12, startCol = 8, indent, curLength;

        for (int r = startRow; r <= endRow; r++) {
            indent = rowLength - startRowLength;
            curLength = 0;

            for (int c = startCol - indent; curLength < rowLength; c += 2) {
                field[r][c] = new Cell(null);
                curLength++;
            }

            rowLength = r < (endRow + startRow) / 2 ? rowLength + 1 : rowLength - 1;
        }
    }

    private void initFieldFor6Players(List<Player> players) {
        initPlayerCorner(players.get(0), Corner.TOP);
        initPlayerCorner(players.get(1), Corner.UPPER_RIGHT);
        initPlayerCorner(players.get(2), Corner.LOWER_RIGHT);
        initPlayerCorner(players.get(3), Corner.BOTTOM);
        initPlayerCorner(players.get(4), Corner.LOWER_LEFT);
        initPlayerCorner(players.get(5), Corner.UPPER_LEFT);
    }

    private void initFieldFor4Players(List<Player> players) {
        initPlayerCorner(players.get(0), Corner.TOP);
        initPlayerCorner(players.get(1), Corner.LOWER_RIGHT);
        initPlayerCorner(players.get(2), Corner.BOTTOM);
        initPlayerCorner(players.get(3), Corner.UPPER_LEFT);

        initCorner(Corner.UPPER_RIGHT, null);
        initCorner(Corner.LOWER_LEFT, null);
    }

    private void initFieldFor3Players(List<Player> players) {
        initPlayerCorner(players.get(0), Corner.UPPER_RIGHT);
        initPlayerCorner(players.get(1), Corner.BOTTOM);
        initPlayerCorner(players.get(2), Corner.UPPER_LEFT);

        initCorner(Corner.TOP, null);
        initCorner(Corner.LOWER_LEFT, null);
        initCorner(Corner.LOWER_RIGHT, null);
    }

    private void initFieldFor2Players(List<Player> players) {
        initPlayerCorner(players.get(0), Corner.TOP);
        initPlayerCorner(players.get(1), Corner.BOTTOM);

        initCorner(Corner.LOWER_LEFT, null);
        initCorner(Corner.LOWER_RIGHT, null);
        initCorner(Corner.UPPER_LEFT, null);
        initCorner(Corner.UPPER_RIGHT, null);
    }

    private void initPlayerCorner(Player player, Corner corner) {
        player.setPlayerCorner(corner);
        initCorner(corner, player.playerColor);
    }

    private void initCorner(Corner corner, Color color) {
        Point coordinates;
        switch (corner) {
            case TOP, LOWER_LEFT, LOWER_RIGHT -> {
                coordinates = Utils.CORNER_VERTEX_INDICES.get(corner);
                initUpwardCorner(coordinates.y, coordinates.x, color);
            }

            case BOTTOM, UPPER_LEFT, UPPER_RIGHT -> {
                coordinates = Utils.CORNER_VERTEX_INDICES.get(corner);
                initDownwardCorner(coordinates.y, coordinates.x, color);
            }
        }
    }

    private void initUpwardCorner(int topRow, int topCol, Color color) {
        int cornerLength = 4;

        for (int r = topRow, rowLength = 1; r < topRow + cornerLength; r++, rowLength++) {
            fillAngleLine(r, topCol, rowLength, color);
        }
    }

    private void initDownwardCorner(int bottomRow, int bottomCol, Color color) {
        int cornerLength = 4;

        for (int r = bottomRow, rowLength = 1; r > bottomRow - cornerLength; r--, rowLength++) {
            fillAngleLine(r, bottomCol, rowLength, color);
        }
    }

    private void fillAngleLine(int row, int column, int rowLength, Color color) {
        int indent = rowLength - 1, curLength = 0;

        for (int c = column - indent; curLength < rowLength; c += 2) {
            field[row][c] = new Cell(color);
            curLength++;
        }
    }

    public boolean isCornerFilled(Corner corner, Color color) {
        Point coordinates;
        switch (corner) {
            case TOP, LOWER_LEFT, LOWER_RIGHT -> {
                coordinates = Utils.CORNER_VERTEX_INDICES.get(corner);
                return isUpwardCornerFilled(coordinates.y, coordinates.x, color);
            }

            case BOTTOM, UPPER_LEFT, UPPER_RIGHT -> {
                coordinates = Utils.CORNER_VERTEX_INDICES.get(corner);
                return isDownwardCornerFilled(coordinates.y, coordinates.x, color);
            }
        }

        return false;
    }

    private boolean isUpwardCornerFilled(int row, int col, Color color) {
        int curLength = 0;

        for (int r = row, rowLength = 1; r < row + CORNER_HEIGHT; r++, rowLength++) {
            for (int c = col; curLength < rowLength; c += 2) {
                if (field[r][c].getCellColor() != color) {
                    return false;
                }

                curLength++;
            }
        }

        return true;
    }

    private boolean isDownwardCornerFilled(int row, int col, Color color) {
        int curLength = 0;

        for (int r = row, rowLength = 1; r > r - CORNER_HEIGHT; r--) {
            for (int c = col; curLength < rowLength; c += 2) {
                if (field[r][c].getCellColor() != color) {
                    return false;
                }

                curLength++;
            }
        }

        return true;
    }

    public void setCellColor(int row, int col, Color color) {
        field[row][col].setCellColor(color);
    }

    public Color getCellColor(int row, int col) {
        if (field[row][col] == null) {
            throw new NullPointerException();
        }

        return field[row][col].getCellColor();
    }
}
