package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Board.SideUtilities;
import byow.Core.Objects.Sprite;
import byow.Core.Point;
import byow.TileEngine.TETile;

import java.util.Random;

public class Enemy extends Sprite {
    private Random rand; // TODO - delete after adding in AI

    public Enemy(Board board, TETile shape, Point location) {
        super(board, shape, location);
        this.rand = new Random(21);
    }

    @Override
    public void take_turn() {
        // TODO - later make the enemies smart.  They should use a search algorithm to find the player.
        Side direction = SideUtilities.random_side_except(rand, null);
        move(direction);
    }
}
