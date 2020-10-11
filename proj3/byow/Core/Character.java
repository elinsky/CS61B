package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Character {
    private Point location;
    private Board board;
    private final TETile shape;


    public Character(Board board, TETile shape) {
        this.board = board;
        this.shape = shape;
        this.location = random_avatar_location(board, 24);
        board.set_cell(location, shape);
    }

    // Avatars are only allowed to be on Floor cells
    public static boolean valid_location(Point location, Board board) {
        return board.get_cell(location) == Tileset.FLOOR;
    }

    // Avatars can only move to other floor cells.
    public void move(Side side) {
        Point move;
        switch (side) {
            case TOP -> move = new Point(location.x(), location.y() + 1);
            case BOTTOM -> move = new Point(location.x(), location.y() - 1);
            case LEFT -> move = new Point(location.x() - 1, location.y());
            case RIGHT -> move = new Point(location.x() + 1, location.y());
            default -> move = location;
        }
        if (valid_location(move, board)) {
            board.set_cell(location, Tileset.FLOOR);
            location = move;
            board.set_cell(location, shape);
        }
    }

    private static Point random_avatar_location(Board board, int seed) {
        Random rand = new Random(seed);
        int x = RandomUtils.uniform(rand, 0, board.get_width());
        int y = RandomUtils.uniform(rand, 0, board.get_height());
        Point loc = new Point(x, y);
        while (!Character.valid_location(loc, board)) {
            x = RandomUtils.uniform(rand, 0, board.get_width());
            y = RandomUtils.uniform(rand, 0, board.get_height());
            loc = new Point(x, y);
        }
        return loc;
    }

}
