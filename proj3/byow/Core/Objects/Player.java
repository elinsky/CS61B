package byow.Core.Objects;

import byow.Core.Board.Board;
import byow.Core.Board.Side;
import byow.Core.KeyListener;
import byow.Core.Objects.Sprite;
import byow.Core.Point;
import byow.TileEngine.TETile;

public class Player extends Sprite {
    public Player(Board board, TETile shape, Point location) {
        super(board, shape, location);
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
}
