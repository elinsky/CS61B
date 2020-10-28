package byow.Core.BoardPiece;

import byow.Core.Board.Board;
import byow.Core.Point;
import byow.TileEngine.Tileset;

import java.io.Serializable;

/**
 * A coin is a object on the game board that characters like a Player can interact with.  A coin does not move, but it
 * can be collected by someone who walks into it.
 */
public class Coin implements Serializable {
    private final Board board;
    private Point location;


    /**
     * Creates a coin on a board at a given location.
     */
    public Coin(Board board, Point location) {
        this.board = board;
        this.location = location;
        board.setCell(location, Tileset.COIN);
    }

    /**
     * Collecting a coin removes it from the game.
     */
    public void collect() {
        board.setCell(location, Tileset.AVATAR);
        location = null;
    }

    /**
     * Returns the location of the coin.
     * @return
     */
    public Point location() {
        return location;
    }
}
