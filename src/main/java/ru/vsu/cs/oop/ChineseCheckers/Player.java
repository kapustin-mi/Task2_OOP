package ru.vsu.cs.oop.ChineseCheckers;

import java.awt.*;

public class Player {
    private final String name;

    protected final Color playerColor;

    protected Corner playerCorner;
    protected Corner oppositeCorner;

    public Player(String name, Color playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }

    public Player(Color playerColor) {
        this.name = null;
        this.playerColor = playerColor;
    }

    protected void setPlayerCorner(Corner corner) {
        playerCorner = corner;
    }

    public String getName() {
        return name;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    protected Corner getOppositeCorner() {
        if (oppositeCorner == null) {
            oppositeCorner = Utils.OPPOSITE_CORNERS.get(playerCorner);
        }

        return oppositeCorner;
    }
}
