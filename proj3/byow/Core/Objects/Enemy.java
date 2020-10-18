package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Board.SideUtilities;
import byow.Core.Engine;
import byow.Core.Objects.Sprite;
import byow.Core.Point;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Sprite {
    private Random rand; // TODO - delete after adding in AI

    public Enemy(Board board, Engine engine, Point location) {
        super(board, engine, location);
        this.rand = new Random(21);
        this.traversable_tiles = new ArrayList<>();
        traversable_tiles.add(Tileset.FLOOR);
        traversable_tiles.add(Tileset.AVATAR);
        this.shape = Tileset.BLUE_GHOST;
        board.set_cell(location, shape);
    }

    @Override
    public void take_turn() {
        // TODO - later make the enemies smart.  They should use a search algorithm to find the player.
        Side direction = SideUtilities.random_side_except(rand, null);
        // TODO - I don't want enemies to be able to move to the same cell as a coin
        move(direction);
    }

    @Override
    public void die() {
        // TODO
    }
}
