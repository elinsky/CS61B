package byow.Core;

import java.util.Random;

public class Hallway extends EnclosedSpace {
    private Orientation orientation;

    Hallway(Board board, PlacementInstructions initial_placement_instructions, Random rand) {
        super(board, initial_placement_instructions, rand);
    }

    @Override
    public void grow_to_random_size(Random rand) {
        // TODO
        // We need to decide the orientation here.
    }
}
