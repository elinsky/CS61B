package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.TileEngine.Tileset;

import java.io.Serializable;

public class Coin implements Serializable {
    private final Board board;
    private Point location;


    public Coin(Board board, Point location) {
        this.board = board;
        this.location = location;
        board.setCell(location, Tileset.COIN);
    }

    public void collect() {
        board.setCell(location, Tileset.AVATAR);
        location = null;
    }

    public Point location() {
        return location;
    }
}
