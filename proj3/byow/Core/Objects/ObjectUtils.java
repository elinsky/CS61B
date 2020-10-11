package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.Core.RandomUtils;
import byow.TileEngine.Tileset;

import java.util.Random;

public class ObjectUtils {

    public static Point random_walkable_cell(Board board, Random rand) {
        int x = RandomUtils.uniform(rand, 0, board.get_width());
        int y = RandomUtils.uniform(rand, 0, board.get_height());
        Point loc = new Point(x, y);
        while (!valid_location(loc, board)) {
            x = RandomUtils.uniform(rand, 0, board.get_width());
            y = RandomUtils.uniform(rand, 0, board.get_height());
            loc = new Point(x, y);
        }
        return loc;


    }

    // Sprites are only allowed to be on Floor cells
    public static boolean valid_location(Point location, Board board) {
        return board.get_cell(location) == Tileset.FLOOR;
    }

}
