package byow.Core;

import byow.TileEngine.Tileset;

public class Door {
    // The door class is dumb.  It doesn't know anything about its neighbors.
    private Board board;
    private Point position;
    private boolean is_open;
    private Side side;

    public Door(Board board, Point position, boolean is_open, Side side) {
        this.board = board;
        this.position = position;
        this.is_open = is_open;
        this.side = side;
    }

    public void draw() {
        if (is_open) {
            board.set_tile(position, Tileset.FLOOR);
        } else {
            board.set_tile(position, Tileset.WALL);
        }
    }

    public void close_door() {
        board.set_tile(position, Tileset.WALL);
    }

    public void open_door() {
        board.set_tile(position, Tileset.FLOOR);
    }

    /**
     * For a given door, this method will return build instructions for a new room or
     * hallway that connects to this door.
     * @return Build Instructions object = location for area + side that the door goes on.
     */
    public PlacementInstructions get_placement_instructions_for_neighbor() {
        Point center_of_new_build_site;
        Side side_of_new_door;
        switch (side) {
            case TOP -> {
                center_of_new_build_site = new Point(position.x(), position.y() + 2);
                side_of_new_door = Side.BOTTOM;
            }
            case BOTTOM -> {
                center_of_new_build_site = new Point(position.x(), position.y() - 2);
                side_of_new_door = Side.TOP;
            }
            case LEFT -> {
                center_of_new_build_site = new Point(position.x() - 2, position.y());
                side_of_new_door = Side.RIGHT;
            }
            case RIGHT -> {
                center_of_new_build_site = new Point(position.x() + 2, position.y());
                side_of_new_door = Side.LEFT;
            }
            default -> throw new RuntimeException("unrecognized side " + side);
        }
        return new PlacementInstructions(center_of_new_build_site, side_of_new_door, position);
    }

    public Point location() {
        return position;
    }

    public boolean is_open() {
        return is_open;
    }
}