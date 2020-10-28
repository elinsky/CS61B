package byow.Core.BoardPiece;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Board.SideUtilities;
import byow.Core.Point;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

/**
 * An enemy is a character on a game board.  Like a player, it will take its turn and move around the board.  When a
 * player runs into an enemy, the player dies.  Enemies cannot die.
 */
public class Enemy extends Sprite implements Serializable {
    private final Random rand; // TODO - delete after adding in AI

    /**
     * Constructs an enemy on a given board at a given location.
     * @param board
     * @param location
     */
    public Enemy(Board board, Point location) {
        super(board, location);
        this.rand = new Random(21);
        this.traversableTiles = new HashSet<>();
        traversableTiles.add(Tileset.FLOOR);
        traversableTiles.add(Tileset.AVATAR);
        this.shape = Tileset.BLUE_GHOST;
        board.setCell(location, shape);
    }

    /**
     * This method has the enemy take their turn.  They will choose a random direction to move in, and then attempt to
     * move in that direction.
     */
    @Override
    public void takeTurn() {
        Side direction = SideUtilities.randomSideExcept(rand, null);
        // TODO - I don't want enemies to be able to move to the same cell as a coin
        move(direction);
    }
}
