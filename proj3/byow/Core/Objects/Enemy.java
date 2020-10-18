package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Board.SideUtilities;
import byow.Core.Objects.Sprite;
import byow.Core.Point;
import byow.TileEngine.Tileset;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Sprite implements Serializable {
    private final Random rand; // TODO - delete after adding in AI

    public Enemy(Board board, Point location) {
        super(board, location);
        this.rand = new Random(21);
        this.traversable_tiles = new ArrayList<>();
        traversable_tiles.add(Tileset.FLOOR);
        traversable_tiles.add(Tileset.AVATAR);
        this.shape = Tileset.BLUE_GHOST;
        board.set_cell(location, shape);
    }

    @Override
    public void take_turn() {
        Side direction = SideUtilities.random_side_except(rand, null);
        // TODO - I don't want enemies to be able to move to the same cell as a coin
        move(direction);
    }

//    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
//        aOutputStream.writeObject(rand);
//        aOutputStream.writeObject(traversable_tiles);
//        aOutputStream.writeObject(shape);
//        aOutputStream.writeObject(board);
//
//        // TODO
//    }
}