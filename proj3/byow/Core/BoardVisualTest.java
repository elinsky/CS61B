package byow.Core;

import byow.TileEngine.TERenderer;

/**
 * This class generates a world, and displays it, allowing you to visually test the map.
 */
public class BoardVisualTest {

    public static void main(String[] args) {
        BoardGenerator board_generator = new BoardGenerator();
        Board board = board_generator.generate_random_board2();
        TERenderer ter = new TERenderer();
        ter.initialize(board.width(), board.height());
        ter.renderFrame(board.get_board());
    }
}
