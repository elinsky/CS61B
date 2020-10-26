package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Point;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;

public class Player extends Sprite implements Serializable {

    public Player(Board board, Point location) {
        super(board, location);
        this.traversableTiles = new ArrayList<>();
        traversableTiles.add(Tileset.FLOOR);
        traversableTiles.add(Tileset.COIN);
        traversableTiles.add(Tileset.BLUE_GHOST);
        this.shape = Tileset.AVATAR;
        board.setCell(location, shape);
    }

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
