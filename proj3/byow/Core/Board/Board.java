package byow.Core.Board;

import byow.Core.Point;
import byow.TileEngine.TETile;

import java.io.Serializable;
import java.util.List;

/**
 * A board is a general purpose 2 dimensional grid of tiles.  You can populate the grid with TETiles, which can be
 * anything you want: floors, walls, players, enemies, coins, etc.
 */
public class Board implements Serializable {
    private final int height;
    private final int width;
    private final TETile[][] board;

    /**
     * Creates a new board that is populated with the default tile.
     * @param height height of the desired board in tiles.
     * @param width width of the desired board in tiles.
     * @param defaultTile tile to populate the whole board with.
     */
    public Board(int height, int width, TETile defaultTile) {
        this.height = height;
        this.width = width;
        this.board = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = defaultTile;
            }
        }
    }

    /**
     * returns the board as a 2 dimensional array of TETiles
     */
    public TETile[][] getBoard() {
        return board;
    }

    /**
     * Given a valid point on the board, returns the cell at that point in TETile form.
     */
    public TETile getCell(Point point) {
        return board[point.x()][point.y()];
    }

    /**
     * Given a valid point on the board, and a tile, sets the corresponding cell on the board to the given tile.
     */
    public void setCell(Point point, TETile tile) {
        board[point.x()][point.y()] = tile;
    }

    /**
     * Returns the width of the board in tiles.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the board in tiles.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Given a point, checks to see if that point is actually on the board.
     */
    public boolean isValidPoint(Point point) {
        return point.x() >= 0 && point.x() < width && point.y() >= 0 && point.y() < height;
    }

    /**
     * Given a list of points, checks to see if all points are actually on the board.  If a single point is not, this
     * method will return false.
     */
    public boolean areValidPoints(List<Point> points) {
        for (Point point : points) {
            if (!isValidPoint(point)) {
                return false;
            }
        }
        return true;
    }
}