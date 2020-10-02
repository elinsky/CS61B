package byow.Core;

import java.util.ArrayList;
import java.util.Random;

public class EnclosedSpace {
    protected Point initial_door_location; // This is the door created when the room is created.
    protected Side initial_door_side;
    protected Point initial_center;
    protected Point top_left;
    protected Point bottom_right;
    protected World world;
    protected ArrayList<Door> doors;
    protected Random rand;
    protected Board board;

    EnclosedSpace(World world, PlacementInstructions initial_placement_instructions, Random rand, Board board) {
        this.rand = rand;
        this.world = world;
        this.board = board;
        initial_door_side = initial_placement_instructions.side_for_door();
        initial_center = initial_placement_instructions.location();
        top_left = new Point(initial_center.x() - 1, initial_center.y() + 1);
        bottom_right = new Point(initial_center.x() + 1, initial_center.y() - 1);
        switch (initial_door_side) {
            case TOP -> initial_door_location = new Point(initial_center.x(), initial_center.y() + 1);
            case BOTTOM -> initial_door_location = new Point(initial_center.x(), initial_center.y() - 1);
            case LEFT -> initial_door_location = new Point(initial_center.x() - 1, initial_center.y());
            case RIGHT -> initial_door_location = new Point(initial_center.x() + 1, initial_center.y());
        }
        world.add_enclosed_space(this, initial_placement_instructions);
        this.doors = new ArrayList<>();
        Door first_door = new Door(world, initial_door_location, true, initial_door_side, board);
        doors.add(first_door);
        world.add_used_door(first_door);
    }

    public ArrayList<Door> get_doors() {
        return doors;
    }

    private void build_door(World world, Point position, boolean is_open) {
        // TODO
    }

    void grow_to_random_size(Random rand) {

    }

    // TODO - decide if I should have a 'draw()' method here.
}
