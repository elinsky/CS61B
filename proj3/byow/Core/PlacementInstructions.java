package byow.Core;

public class PlacementInstructions {
    private final Point location;
    private final Side side_for_door;

    public PlacementInstructions(Point location, Side side_for_door) {
        this.location = location;
        this.side_for_door = side_for_door;
    }

    public Point location() {
        return location;
    }

    public Side side_for_door() {
        return side_for_door;
    }
}
