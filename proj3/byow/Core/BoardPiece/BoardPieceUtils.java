package byow.Core.BoardPiece;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.Core.RandomUtils;
import byow.TileEngine.TETile;

import java.util.Random;
import java.util.Set;

public class BoardPieceUtils {

    /**
     * Given a board, a Random object, and a Set of valid tile types, this method will choose a random point on
     * the board.
     */
    public static Point randomCell(Board board, Random rand, Set<TETile> validTileTypes) {
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

    /**
     * Given a point, a board, and a Set of traversable tiles, this method will determine if that tile is in fact
     * traversable.
     */
    public static boolean isTileTraversable(Point desiredDestination, Board board, Set<TETile> traversableTiles) {
        TETile destinationTile = board.getCell(desiredDestination);
        for (TETile traversableTile : traversableTiles) {
            if (destinationTile.equals(traversableTile)) {
                return true;
            }
        }
        return false;
    }

}
