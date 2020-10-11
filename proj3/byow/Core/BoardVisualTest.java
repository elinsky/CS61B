package byow.Core;

import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * This class generates a world, and displays it, allowing you to visually test the map.
 */
public class BoardVisualTest {

    public static void main(String[] args) {
        BoardGenerator boardGenerator = new BoardGenerator(50, 50, 23);
        Board board = boardGenerator.get_board();
    }
}
