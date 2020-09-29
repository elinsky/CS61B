package byow.Core;

import java.util.Random;

public class SideUtilities {

    public static Side random_side_except(Random rand, Side exclude) {
        Side choice =  switch (RandomUtils.uniform(rand, 4)) {
            case 0 -> Side.TOP;
            case 1 -> Side.BOTTOM;
            case 2 -> Side.LEFT;
            case 3 -> Side.RIGHT;
            default -> Side.TOP;
        };
        if (choice != exclude) {
            return choice;
        } else {
            return random_side_except(rand, exclude);
        }
    }

    /**
     * Utility method to return a random side.
     * @return Randomly selected Side
     */
    public static Side random_side(Random rand) {
        return random_side_except(rand, null);
    }
}
