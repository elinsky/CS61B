package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.Core.RandomUtils;
import byow.TileEngine.TETile;

import java.util.ArrayList;
import java.util.Random;

public class ObjectUtils {

    public static Point randomCell(Board board, Random rand, ArrayList<TETile> validTileTypes) {
        int x = RandomUtils.uniform(rand, 0, board.getWidth());
        int y = RandomUtils.uniform(rand, 0, board.getHeight());
        Point loc = new Point(x, y);
        while (!isTileTraversable(loc, board, validTileTypes)) {
            x = RandomUtils.uniform(rand, 0, board.getWidth());
            y = RandomUtils.uniform(rand, 0, board.getHeight());
            loc = new Point(x, y);
        }
        return loc;
    }

    public static boolean isTileTraversable(Point desiredDestination, Board board, ArrayList<TETile> traversableTiles) {
        TETile destinationTile = board.getCell(desiredDestination);
        for (TETile traversableTile : traversableTiles) {
            if (destinationTile.equals(traversableTile)) {
                return true;
            }
        }
        return false;
    }

}
