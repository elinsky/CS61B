package byow.Core.Board;

import byow.Core.Point;

/**
 * Given a closed door, RoomBuildPlans contain all the information that a BoardGenerator needs to build an adjoining
 * room.
 */
public class RoomBuildPlans {
    private final Point newDoorLocation;
    private final Side sideForDoor;
    private final Point existingDoorLocation;

    /**
     * Constructs plans for a new 3x3 room.
     * @param center the center point of the room.
     * @param sideForDoor the side of the room to place the initial door.
     * @param existingDoorLocation The location of the door to the adjoining room (the room we are building off of).
     */
    public RoomBuildPlans(Point center, Side sideForDoor, Point existingDoorLocation) {
        this.newDoorLocation = center;
        this.sideForDoor = sideForDoor;
        this.existingDoorLocation = existingDoorLocation;
    }

    /**
     * Gets the point where we want to build the first door.
     */
    public Point location() {
        return newDoorLocation;
    }

    /**
     * Gets the side of the room that we want to place the first door on.
     */
    public Side sideForDoor() {
        return sideForDoor;
    }

    /**
     * Gets the location of the door of the adjoining room.  This is the door of an existing room that we would like to
     * build off of.
     */
    public Point existingDoorLocation() {
        return existingDoorLocation;
    }
}
