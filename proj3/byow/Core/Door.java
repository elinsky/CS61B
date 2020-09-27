package byow.Core;

import byow.TileEngine.Tileset;

public class Door {
    private Board board;
    private Point position;
    private boolean is_open;


    public void draw() {
        if (is_open) {
            board.set_tile(position, Tileset.FLOOR);
        } else {
            board.set_tile(position, Tileset.WALL);
        }
    }

    /**
     * For a given door that doesn't have a neighbor, this method will return build instructions for a new room or
     * hallway.  This method should fail if there isn't room to create a new room or hallway (e.g. this room is at the
     * edge of the board).  It should also fail somehow if the door already has a room/hallway attached to it.
     * @return Build Instructions object = location for area + side that the door goes on.
     */
    public PlacementInstructions get_placement_instructions_for_neighbor() {
        // TODO
        return null;
    }


    public Point location() {
        return position;
    }

    public boolean is_open() {
        return is_open;
    }

}