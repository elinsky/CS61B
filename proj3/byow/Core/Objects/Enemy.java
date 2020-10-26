package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Board.SideUtilities;
import byow.Core.Point;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Sprite implements Serializable {
    private final Random rand; // TODO - delete after adding in AI

    public Enemy(Board board, Point location) {
        super(board, location);
        this.rand = new Random(21);
        this.traversableTiles = new ArrayList<>();
        traversableTiles.add(Tileset.FLOOR);
        traversableTiles.add(Tileset.AVATAR);
        this.shape = Tileset.BLUE_GHOST;
        board.setCell(location, shape);
    }

    @Override
    public void takeTurn() {
        Side direction = SideUtilities.randomSideExcept(rand, null);
        // TODO - I don't want enemies to be able to move to the same cell as a coin
        move(direction);
    }
}
