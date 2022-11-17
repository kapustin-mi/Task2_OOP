package ru.vsu.cs.oop.ChineseCheckers;

import java.awt.*;

public class Cell {
    protected Color cellColor;

    protected Cell(Color cellColor) {
        this.cellColor = cellColor;
    }

    protected void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }

    public Color getCellColor() {
        return cellColor;
    }
}
