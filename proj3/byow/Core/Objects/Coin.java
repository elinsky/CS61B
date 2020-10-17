package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Coin {
    private Board board;
    private TETile shape;
    private Point location;
    private int value = 1;


    public Coin(Board board, Point location) {
        this.board = board;
        this.shape = Tileset.COIN;
        this.location = location;
        board.set_cell(location, shape);
    }

    public int collect() {
        int award = value;
        value = 0;
        board.set_cell(location, Tileset.FLOOR);
        return award;
    }
}
