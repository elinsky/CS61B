package byow.Core;

import java.util.Random;

public abstract class EnclosedSpace {
    private Point initial_door_location; // This is the door created when the room is created. // TODO somewhere I need to set this
    private Side initial_door_side;
    private Point initial_center;
    private Point top_left;
    private Point bottom_right;
    private Board board;

    abstract void grow_to_random_size(Random rand);

    abstract void set_initial_placement(PlacementInstructions initial_placement, Board board);

    // TODO - decide if I should have a 'draw()' method here.
}
