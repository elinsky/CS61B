package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Point;
import byow.Core.RandomUtils;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public abstract class Sprite {
    private Point location;
    private final Board board;
    private final TETile shape;

    // A sprite is a character on the board.
    public Sprite(Board board, TETile shape, Point location) {
        this.board = board;
        this.shape = shape;
        this.location = location;
        board.set_cell(location, shape);
    }

    abstract public void take_turn();

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
        if (ObjectUtils.valid_location(move, board)) {
            board.set_cell(location, Tileset.FLOOR);
            location = move;
            board.set_cell(location, shape);
        }
    }
}
