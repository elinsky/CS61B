package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.List;

/**
 * This Board class contains general purpose code for creating and editing a board.  It doesn't contain any special
 * purpose code.  This class hides the underlying board data structure from users of the class.  I do worry a bit that
 * this class is too shallow.  Maybe it is only shallow because my boards are currently small, and don't require
 * sophisticated data structures or algorithms.
 */
public class Board {
    private final int dumb_height;
    private final int dumb_width;
    private final TETile[][] dumbboard;
    private final TERenderer ter;



    public Board(int height, int width, TETile default_tile) {
        this.dumb_height = height;
        this.dumb_width = width;
        this.dumbboard = new TETile[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                dumbboard[x][y] = default_tile;
            }
        }
        ter = new TERenderer();
        ter.initialize(height, width);
        ter.renderFrame(dumbboard);
    }

    public void render_frame() {
        ter.renderFrame(dumbboard);
    }

    public TETile get_cell(Point point) {
        return dumbboard[point.x()][point.y()];
    }

    public void set_cell(Point point, TETile tile) {
        dumbboard[point.x()][point.y()] = tile;
    }

    public int get_width() {
        return dumb_width;
    }

    public int get_height() {
        return dumb_height;
    }

    public boolean is_valid_point(Point point) {
        return point.x() >= 0 && point.x() < dumb_width && point.y() >= 0 && point.y() < dumb_height;
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
