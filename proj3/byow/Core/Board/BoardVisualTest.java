package byow.Core.Board;

import byow.TileEngine.TERenderer;

import java.util.Random;

/**
 * This class generates a world, and displays it, allowing you to visually test the map.
 */
public class BoardVisualTest {

    public static void main(String[] args) {
        int WIDTH = 80;
        int HEIGHT = 50;
        Random rand = new Random(21);
        BoardGenerator boardGenerator = new BoardGenerator(WIDTH, HEIGHT, rand);
        Board board = boardGenerator.getBoard();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(board.getBoard(), "");
    }
}
