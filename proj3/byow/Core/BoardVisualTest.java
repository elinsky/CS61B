package byow.Core;

import byow.TileEngine.TERenderer;

/**
 * This class generates a world, and displays it, allowing you to visually test the map.
 */
public class BoardVisualTest {

    public static void main(String[] args) {
        BoardGenerator board_generator = new BoardGenerator();
        TERenderer ter = new TERenderer();

        Board board = board_generator.generate_random_board();
        ter.initialize(board.width(), board.height());
        ter.renderFrame(board.get_board());

//        board = board_generator.gen3(board);
//        ter.renderFrame(board.get_board());



    }
}
