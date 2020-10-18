package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.Engine;
import byow.Core.KeyListener;
import byow.Core.Objects.Sprite;
import byow.Core.Point;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;

public class Player extends Sprite {

    public Player(Board board, Engine engine, Point location) {
        super(board, engine, location);
        this.traversable_tiles = new ArrayList<>();
        traversable_tiles.add(Tileset.FLOOR);
        traversable_tiles.add(Tileset.COIN);
        traversable_tiles.add(Tileset.BLUE_GHOST);
        traversable_tiles.add(Tileset.ORANGE_GHOST);
        traversable_tiles.add(Tileset.PINK_GHOST);
        this.shape = Tileset.AVATAR;
        board.set_cell(location, shape);
    }

    @Override
    public void take_turn() {
        String key = KeyListener.get_keypress(1);
        switch (key) {
            case "w" -> move(Side.TOP);
            case "a" -> move(Side.LEFT);
            case "s" -> move(Side.BOTTOM);
            case "d" -> move(Side.RIGHT);
        }
    }

    @Override
    public void die() {
        // TODO
    }
}
