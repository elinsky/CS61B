package byow.Core;

public class Door {
    private final Point position;
    private final Side side;

    public Door(Point position, boolean is_open, Side side, Board board) {
        // The door class is dumb.  It doesn't know anything about its neighbors.
        this.position = position;
        this.side = side;
    }

    /**
     * For a given door, this method will return build instructions for a new room or
     * hallway that connects to this door.
     * @return Build Instructions object = location for area + side that the door goes on.
     */
    public RoomBuildPlans get_build_plans_for_neighbor() {
        Point center_of_new_build_site;
        Side side_of_new_door;
        switch (side) {
            // TODO - I think I should be able to refactor the switch statments away by adding polymorphisim.
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
        return new RoomBuildPlans(center_of_new_build_site, side_of_new_door, position);
    }
}