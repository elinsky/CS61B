package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Coin {
    private final Board board;
    private Point location;


    public Coin(Board board, Point location) {
        this.board = board;
        this.location = location;
        board.set_cell(location, Tileset.COIN);
    }

    public void collect() {
        board.set_cell(location, Tileset.AVATAR);
        location = null;
    }

    public Point location() {
        return location;
    }
}
