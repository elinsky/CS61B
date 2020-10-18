package byow.Core.Board;

import byow.Core.Point;

public class RoomBuildPlans {
    private final Point new_door_location;
    private final Side side_for_door;
    private final Point existing_door_location;

    public RoomBuildPlans(Point center, Side side_for_door, Point existing_door_location) {
        this.new_door_location = center;
        this.side_for_door = side_for_door;
        this.existing_door_location = existing_door_location;
    }

    public Point location() {
        return new_door_location;
    }

    public Side side_for_door() {
        return side_for_door;
    }

    public Point existing_door_location() {
        return existing_door_location;
    }
}