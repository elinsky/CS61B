package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.Core.RandomUtils;
import byow.TileEngine.TETile;

import java.util.ArrayList;
import java.util.Random;

public class ObjectUtils {

    public static Point random_cell(Board board, Random rand, ArrayList<TETile> valid_tile_types) {
        int x = RandomUtils.uniform(rand, 0, board.get_width());
        int y = RandomUtils.uniform(rand, 0, board.get_height());
        Point loc = new Point(x, y);
        while (!is_tile_traversable(loc, board, valid_tile_types)) {
            x = RandomUtils.uniform(rand, 0, board.get_width());
            y = RandomUtils.uniform(rand, 0, board.get_height());
            loc = new Point(x, y);
        }
        return loc;


    }

    public static boolean is_tile_traversable(Point desired_destination, Board board, ArrayList<TETile> traversable_tiles) {
        TETile destination_tile = board.get_cell(desired_destination);
        for (TETile traversable_tile : traversable_tiles) {
            if (destination_tile.equals(traversable_tile)) {
                return true;
            }
        }
        return false;
    }

}
