package ru.vsu.cs.oop.ChineseCheckers;

import java.awt.*;
import java.util.HashMap;

class Utils {
    public static final HashMap<Corner, Point> CORNER_VERTEX_INDICES;
    static {
        CORNER_VERTEX_INDICES = new HashMap<>();

        CORNER_VERTEX_INDICES.put(Corner.TOP, new Point(12, 0));
        CORNER_VERTEX_INDICES.put(Corner.LOWER_LEFT, new Point(3, 9));
        CORNER_VERTEX_INDICES.put(Corner.LOWER_RIGHT, new Point(21, 9));
        CORNER_VERTEX_INDICES.put(Corner.BOTTOM, new Point(12, 16));
        CORNER_VERTEX_INDICES.put(Corner.UPPER_LEFT, new Point(3, 7));
        CORNER_VERTEX_INDICES.put(Corner.UPPER_RIGHT, new Point(21, 7));
    }

    public static final HashMap<Corner, Corner> OPPOSITE_CORNERS;
    static  {
        OPPOSITE_CORNERS = new HashMap<>();

        OPPOSITE_CORNERS.put(Corner.TOP, Corner.BOTTOM);
        OPPOSITE_CORNERS.put(Corner.UPPER_RIGHT, Corner.LOWER_LEFT);
        OPPOSITE_CORNERS.put(Corner.LOWER_RIGHT, Corner.UPPER_LEFT);
        OPPOSITE_CORNERS.put(Corner.BOTTOM, Corner.TOP);
        OPPOSITE_CORNERS.put(Corner.LOWER_LEFT, Corner.UPPER_RIGHT);
        OPPOSITE_CORNERS.put(Corner.UPPER_LEFT, Corner.LOWER_RIGHT);
    }

    private static final int[] SPACES = {12, 11, 10, 9, 0, 1, 2, 3, 4, 3, 2, 1, 0, 9, 10, 11, 12};
    private static final int[] ROW_LENGTH = {1, 2, 3, 4, 13, 12, 11, 10, 9, 10, 11, 12, 13, 4, 3, 2, 1};

    public static Point convertToGamePoint(int rowIndex, int colIndex) {
        int gameColIndex = SPACES[rowIndex];
        for (int i = 0; i < colIndex; i++) {
            gameColIndex += 2;
        }

        return new Point(gameColIndex, rowIndex);
    }

    public static Cell[][] copyGameFieldToArr(GameField field) {
        Cell[][] matrix = createMatrix();

        Point curPoint;
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                curPoint = convertToGamePoint(r, c);
                matrix[r][c] = new Cell(field.getCellColor(curPoint.y, curPoint.x));
            }
        }

        return matrix;
    }

    private static Cell[][] createMatrix() {
        Cell[][] matrix = new Cell[GameField.FIELD_HEIGHT][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new Cell[ROW_LENGTH[i]];
        }

        return matrix;
    }
}
