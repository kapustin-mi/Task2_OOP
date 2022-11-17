package ru.vsu.cs.oop.ChineseCheckers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;
    private final List<Player> winners;
    private final List<Integer> countOfPlayerMoves;

    private final GameField field;
    private final Cell[][] convertedField;

    private Player mover;

    private int moverIndex;

    private boolean wasJump;

    public Game(List<Player> players) {
        checkNumberOfPlayers(players.size());

        moverIndex = 0;
        mover = players.get(moverIndex);

        field = new GameField(players);
        convertedField = Utils.copyGameField(field);

        this.players = players;
        countOfPlayerMoves = initCount();

        winners = new ArrayList<>();

        wasJump = false;
    }

    private void checkNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers < 2 || numberOfPlayers == 5 || numberOfPlayers > 6) {
            throw new IllegalArgumentException("Error. The number of players can be 2, 3, 4 and 6.");
        }
    }

    private List<Integer> initCount() {
        List<Integer> moves = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            moves.add(0);
        }

        return moves;
    }

    public boolean makeMove(int startRowIndex, int startColIndex, int endRowIndex, int endColIndex) {
        if (isValidPoint(startRowIndex, startColIndex) && isValidPoint(endRowIndex, endColIndex)) {
            Point startMovePoint = Utils.convertToGamePoint(startRowIndex, startColIndex);

            if (field.getCellColor(startMovePoint.y, startMovePoint.x) == mover.getPlayerColor()) {
                Point endMovePoint = Utils.convertToGamePoint(endRowIndex, endColIndex);

                if (isNullPointNearby(startMovePoint, endMovePoint)) {
                    swapCells(startMovePoint, endMovePoint, startRowIndex, startColIndex, endRowIndex, endColIndex);
                    wasJump = true;
                    endMove();

                    return true;
                }

                if (isNullPointAfterJump(startMovePoint, endMovePoint)) {
                    swapCells(startMovePoint, endMovePoint, startRowIndex, startColIndex, endRowIndex, endColIndex);

                    return true;
                }
            }
        }

        return false;
    }

    private void swapCells(Point start, Point end, int startR, int startC, int endR, int endC) {
        field.setCellColor(end.y, end.x, mover.getPlayerColor());
        field.setCellColor(start.y, start.x, null);

        convertedField[endR][endC].setCellColor(mover.getPlayerColor());
        convertedField[startR][startC].setCellColor(null);
    }

    private boolean isNullPointNearby(Point startPoint, Point endPoint) {
        return !wasJump && field.getCellColor(endPoint.y, endPoint.x) == null &&
                Math.abs(startPoint.x - endPoint.x) <= 1 && Math.abs(startPoint.y - endPoint.y) <= 1;
    }

    private boolean isNullPointAfterJump(Point startPoint, Point endPoint) {
        if (field.getCellColor(endPoint.y, endPoint.x) == null) {
            Point pointBetween = new Point(startPoint.x, startPoint.y);

            int difference = Math.abs(startPoint.x - endPoint.x);
            switch (difference) {
                case 2:
                    if (Math.abs(startPoint.y - endPoint.y) == 2) {
                        pointBetween.y = (startPoint.y + endPoint.y) / 2;
                    }
                case 4:
                    pointBetween.x = (startPoint.x + endPoint.x) / 2;

                    try {
                        if (field.getCellColor(pointBetween.y, pointBetween.x) != null) {
                            wasJump = true;
                            return true;
                        }
                    } catch (NullPointerException e) {
                        return false;
                    }

                default:
                    return false;
            }
        }

        return false;
    }

    public boolean endMove() {
        if (wasJump) {
            countOfPlayerMoves.set(moverIndex, countOfPlayerMoves.get(moverIndex) + 1);

            if (moverIndex == players.size() - 1) {
                moverIndex = 0;
            } else {
                moverIndex++;
            }

            mover = players.get(moverIndex);
            wasJump = false;

            return true;
        }

        return false;
    }

    private boolean isValidPoint(int r, int c) {
        return r >= 0 && r < convertedField.length && c >= 0 && c < convertedField[r].length;
    }

    public boolean isGameEnd() {
        for (Player player : players) {
            if (field.isCornerFilled(player.getOppositeCorner(), player.playerColor)) {
                winners.add(player);
            }
        }

        return isSameNumberOfMoves() && winners.size() != 0;
    }

    private boolean isSameNumberOfMoves() {
        int count = countOfPlayerMoves.get(0);
        for (int i = 1; i < countOfPlayerMoves.size(); i++) {
            if (countOfPlayerMoves.get(i) != count) {
                return false;
            }
        }

        return true;
    }

    public Cell[][] getField() {
        return convertedField;
    }

    public Player getMover() {
        return mover;
    }

    public List<Player> getWinners() {
        return winners;
    }
}
