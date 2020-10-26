package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Point;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Sprite implements Serializable {
    private Point location;
    private final Board board;
    protected TETile shape;
    protected ArrayList<TETile> traversableTiles;

    // A sprite is a character on the board.
    public Sprite(Board board, Point location) {
        this.board = board;
        this.location = location;
    }

    abstract public void takeTurn();

    public ArrayList<TETile> getTraversableTiles() {
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
        if (ObjectUtils.isTileTraversable(move, board, traversableTiles)) {
            board.setCell(location, Tileset.FLOOR);
            location = move;
            board.setCell(location, shape);
        }
    }

    public Point location() {
        return location;
    }
}
