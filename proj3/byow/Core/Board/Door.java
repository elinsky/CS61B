package byow.Core.Board;

import byow.Core.Point;

public class Door {
    private final Point position;
    private final Side side;

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