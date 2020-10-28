package byow.Core.BoardPiece;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Point;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.HashSet;

/**
 * A player is a character on a game board.  It can be controlled by a human with a keyboard.  The player's goal is to
 * collect all of the coins on the board, while staying away from enemies.  Enemies will kill a player.
 */
public class Player extends Sprite implements Serializable {

    /**
     * Constructs a player on a board at a given location.
     */
    public Player(Board board, Point location) {
        super(board, location);
        this.traversableTiles = new HashSet<>();
        traversableTiles.add(Tileset.FLOOR);
        traversableTiles.add(Tileset.COIN);
        traversableTiles.add(Tileset.BLUE_GHOST);
        this.shape = Tileset.AVATAR;
        board.setCell(location, shape);
    }

    /**
     * This method accepts a keypress as input (W, A, S, D), and then moves the player one tile in that direction.
     */
    public void move(char key) {
        switch (key) {
            case 'W' -> move(Side.TOP);
            case 'A' -> move(Side.LEFT);
            case 'S' -> move(Side.BOTTOM);
            case 'D' -> move(Side.RIGHT);
        }
    }

    @Override
    public void takeTurn() {
        // TODO - figure out what to do with this method
    }
}
