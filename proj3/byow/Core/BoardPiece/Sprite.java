package byow.Core.BoardPiece;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Point;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Set;

/**
 * A sprite is a character on a game board.  Sprites can move around.  There are some tiles that sprites are allowed to
 * more around on (e.g. floor) and other types of tiles for which they cannot move on (e.g. wall).  Sprites wait their
 * turn in order to move.  And sprites decided themselves how they want to move.
 */
public abstract class Sprite implements Serializable {
    private Point location;
    private final Board board;
    protected TETile shape;
    protected Set<TETile> traversableTiles;

    public Sprite(Board board, Point location) {
        this.board = board;
        this.location = location;
    }

    abstract public void takeTurn();

    public Set<TETile> getTraversableTiles() {
        return traversableTiles;
    }

    // Sprites can only move to other floor cells.
    protected void move(Side side) {
        Point move;
        switch (side) {
            case TOP -> move = new Point(location.x(), location.y() + 1);
            case BOTTOM -> move = new Point(location.x(), location.y() - 1);
            case LEFT -> move = new Point(location.x() - 1, location.y());
            case RIGHT -> move = new Point(location.x() + 1, location.y());
            default -> move = location;
        }
        if (BoardPieceUtils.isTileTraversable(move, board, traversableTiles)) {
            board.setCell(location, Tileset.FLOOR);
            location = move;
            board.setCell(location, shape);
        }
    }

    public Point location() {
        return location;
    }
}
