package byow.Core.Board;

import byow.Core.Point;

public class RoomBuildPlans {
    private final Point newDoorLocation;
    private final Side sideForDoor;
    private final Point existingDoorLocation;

    public RoomBuildPlans(Point center, Side sideForDoor, Point existingDoorLocation) {
        this.newDoorLocation = center;
        this.sideForDoor = sideForDoor;
        this.existingDoorLocation = existingDoorLocation;
    }

    public Point location() {
        return newDoorLocation;
    }

    public Side sideForDoor() {
        return sideForDoor;
    }

    public Point existingDoorLocation() {
        return existingDoorLocation;
    }
}
