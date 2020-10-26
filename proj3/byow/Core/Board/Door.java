package byow.Core.Board;

import byow.Core.Point;

/**
 * A door is a temporary object used to help generate random boards.  A door exists on a side of a room, and can be
 * open or closed.  A closed door appears visually as a wall.  An open door appears like a floor with walls on each
 * side.  When a board is being generated, the BoardGenerator will select random doors that are closed, and attempt
 * to build an adjoining room on the other side of that door.
 */
public class Door {
    private final Point position;
    private final Side side;

    /**
     * @param position location of the door on the board
     * @param isOpen boolean indicating if the door is open (in use) or closed (not used)
     * @param side indicates the side of the room that the door is on (e.g. top, bottom, left, right)
     * @param board the Board that the door is placed on
     */
    public Door(Point position, boolean isOpen, Side side, Board board) {
        // The door class is dumb.  It doesn't know anything about its neighbors.
        this.position = position;
        this.side = side;
    }

    /**
     * For a given door, this method will return build instructions for a new room or
     * hallway that connects to this door.
     * @return Build Instructions object = location for area + side that the door goes on.
     */
    public RoomBuildPlans getBuildPlansForNeighbor() {
        Point centerOfNewBuildSite;
        Side sideOfNewDoor;
        switch (side) {
            case TOP -> {
                centerOfNewBuildSite = new Point(position.x(), position.y() + 2);
                sideOfNewDoor = Side.BOTTOM;
            }
            case BOTTOM -> {
                centerOfNewBuildSite = new Point(position.x(), position.y() - 2);
                sideOfNewDoor = Side.TOP;
            }
            case LEFT -> {
                centerOfNewBuildSite = new Point(position.x() - 2, position.y());
                sideOfNewDoor = Side.RIGHT;
            }
            case RIGHT -> {
                centerOfNewBuildSite = new Point(position.x() + 2, position.y());
                sideOfNewDoor = Side.LEFT;
            }
            default -> throw new RuntimeException("unrecognized side " + side);
        }
        return new RoomBuildPlans(centerOfNewBuildSite, sideOfNewDoor, position);
    }
}