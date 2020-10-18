package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Objects.Sprite;
import byow.Core.Point;
import byow.TileEngine.Tileset;

import java.util.ArrayList;

public class Player extends Sprite {

    public Player(Board board, Point location) {
        super(board, location);
        this.traversable_tiles = new ArrayList<>();
        traversable_tiles.add(Tileset.FLOOR);
        traversable_tiles.add(Tileset.COIN);
        traversable_tiles.add(Tileset.BLUE_GHOST);
        this.shape = Tileset.AVATAR;
        board.set_cell(location, shape);
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
    public void take_turn() {
        // TODO - figure out what to do with this method
//        String key = KeyListener.get_keypress(1);
//        switch (key) {
//            case "w" -> move(Side.TOP);
//            case "a" -> move(Side.LEFT);
//            case "s" -> move(Side.BOTTOM);
//            case "d" -> move(Side.RIGHT);
//        }
    }
}
