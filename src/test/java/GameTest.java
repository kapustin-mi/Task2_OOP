import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.oop.ChineseCheckers.Cell;
import ru.vsu.cs.oop.ChineseCheckers.Game;
import ru.vsu.cs.oop.ChineseCheckers.Player;

import java.awt.*;
import java.util.Arrays;

class GameTest {

    @Test
    void makeMovesWithInvalidValues() {
        Game game = new Game(Arrays.asList(new Player(Color.RED), new Player(Color.BLUE)));

        Assertions.assertFalse(game.makeMove(0, 0, 12, 12));
        Assertions.assertFalse(game.makeMove(-52, 32, 12, 288));
        Assertions.assertFalse(game.makeMove(0, 0, 2, 0));
    }

    @Test
    void makeMovesWithValidValues() {
        Game game = new Game(Arrays.asList(new Player(Color.RED), new Player(Color.BLUE)));

        Assertions.assertTrue(game.makeMove(2, 2, 4, 8));
        game.endMove();

        Assertions.assertTrue(game.makeMove(13, 3, 12, 8));
        Assertions.assertTrue(game.makeMove(3, 3, 5, 8));
        Assertions.assertTrue(game.makeMove(5, 8, 3, 3));
    }

    @Test
    void endMoveNotAllowed() {
        Game game = new Game(Arrays.asList(new Player(Color.RED), new Player(Color.BLUE)));

        Assertions.assertFalse(game.endMove());
    }

    @Test
    void endMoveAllowed() {
        Game game = new Game(Arrays.asList(new Player(Color.RED), new Player(Color.BLUE)));

        game.makeMove(2, 2, 4, 8);
        Assertions.assertTrue(game.endMove());

        game.makeMove(14, 2, 12, 8);
        game.makeMove(12, 8, 14, 2);
        Assertions.assertTrue(game.endMove());
    }

    @Test
    void checkFieldChangeWithInvalidMoves() {
        Game game = new Game(Arrays.asList(new Player(Color.RED), new Player(Color.BLUE)));
        Cell[][] field = game.getField();

        game.makeMove(123, -879, 45, 65);
        Assertions.assertEquals(field, game.getField());

        game.makeMove(0, 0, 3, 3);
        Assertions.assertEquals(field, game.getField());
    }

    @Test
    void checkFieldChangeWithValidMoves() {
        Game game = new Game(Arrays.asList(new Player(Color.RED), new Player(Color.BLUE)));
        Cell[][] field = game.getField();

        game.makeMove(3, 3, 4, 8);
        Assertions.assertNull(field[3][3].getCellColor());
        Assertions.assertEquals(Color.RED, field[4][8].getCellColor());

        game.makeMove(14, 1, 12, 7);
        Assertions.assertEquals(Color.BLUE, field[12][7].getCellColor());
        Assertions.assertNull(field[14][1].getCellColor());
    }

    @Test
    void getMoverWithInvalidMoves() {
        Player firstPlayer = new Player(Color.RED), secondPlayer = new Player(Color.BLUE);
        Game game = new Game(Arrays.asList(firstPlayer, secondPlayer));

        Assertions.assertEquals(firstPlayer, game.getMover());

        game.makeMove(123, -879, 45, 65);
        Assertions.assertEquals(firstPlayer, game.getMover());

        game.makeMove(0, 0, 3, 3);
        Assertions.assertEquals(firstPlayer, game.getMover());
    }

    @Test
    void getMoverWithValidMoves() {
        Player firstPlayer = new Player(Color.RED), secondPlayer = new Player(Color.BLUE);
        Game game = new Game(Arrays.asList(firstPlayer, secondPlayer));

        game.makeMove(3, 3, 4, 8);
        Assertions.assertEquals(secondPlayer, game.getMover());

        game.makeMove(13, 3, 12, 8);
        Assertions.assertEquals(firstPlayer, game.getMover());

        game.makeMove(2, 2, 4, 6);
        game.endMove();
        Assertions.assertEquals(secondPlayer, game.getMover());
    }
}