package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Coin {
    Board board;
    TETile shape;
    Point location;

    public Coin(Board board, Point location) {
        this.board = board;
        this.shape = Tileset.COIN;
        this.location = location;
        board.set_cell(location, shape);
    }
}
