package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Point;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;

public abstract class Sprite {
    private Point location;
    private final Board board;
    protected TETile shape;
    protected ArrayList<TETile> traversable_tiles;

    // A sprite is a character on the board.
    public Sprite(Board board, Point location) {
        this.board = board;
        this.location = location;
    }

    abstract public void take_turn();

    public ArrayList<TETile> getTraversable_tiles() {
        return traversable_tiles;
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
        if (ObjectUtils.is_tile_traversable(move, board, traversable_tiles)) {
            board.set_cell(location, Tileset.FLOOR);
            location = move;
            board.set_cell(location, shape);
        }
    }

    public Point location() {
        return location;
    }
}
