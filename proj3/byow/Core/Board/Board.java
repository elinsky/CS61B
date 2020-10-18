package byow.Core.Board;

import byow.Core.Point;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * This Board class contains general purpose code for creating and editing a board.  It doesn't contain any special
 * purpose code.  This class hides the underlying board data structure from users of the class.  I do worry a bit that
 * this class is too shallow.  Maybe it is only shallow because my boards are currently small, and don't require
 * sophisticated data structures or algorithms.
 */
public class Board implements Serializable {
    private final int height;
    private final int width;
    private final TETile[][] board;
//    private final TERenderer ter;



    public Board(int height, int width, TETile default_tile) {
        this.height = height;
        this.width = width;
        this.board = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = default_tile;
            }
        }
//        ter = new TERenderer();
//        ter.initialize(height, width);
//        ter.renderFrame(board);
    }

//    public void render_frame() {
//        ter.renderFrame(board);
//    }

    public TETile[][] get_board() {
        return board;
    }

    public TETile get_cell(Point point) {
        return board[point.x()][point.y()];
    }

    public void set_cell(Point point, TETile tile) {
        board[point.x()][point.y()] = tile;
    }

    public int get_width() {
        return width;
    }

    public int get_height() {
        return height;
    }

    public boolean is_valid_point(Point point) {
        return point.x() >= 0 && point.x() < width && point.y() >= 0 && point.y() < height;
    }

    public boolean are_valid_points(List<Point> points) {
        for (Point point : points) {
            if (!is_valid_point(point)) {
                return false;
            }
        }
        return true;
    }

}
