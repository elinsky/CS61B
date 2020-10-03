package byow.Core;

/**
 * This class generates a world, and displays it, allowing you to visually test the map.
 */
public class BoardVisualTest {

    public static void main(String[] args) {
        BoardGenerator boardGenerator = new BoardGenerator(50, 50, 22);
        Board board = boardGenerator.get_board();
        board.render_frame();
    }
}
