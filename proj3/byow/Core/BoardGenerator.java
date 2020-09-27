package byow.Core;

import java.util.Random;


/**
 * This class generates a game map.  You can either generate a map with a random seed, or let the class choose a seed
 * for you.  Maps are defined as a 2-dimensional array of TETiles.
 */
public class BoardGenerator {
    Random rand;
    int SEED = 26;

    public BoardGenerator() {
        this.rand = new Random(SEED);
    }

    public Board generate_random_board() {
        Board board = new Board(50, 50);

        // Bootstrap the initial board generation process
        Room initial_room = new Room();
        PlacementInstructions initial_placement_instructions = new PlacementInstructions(board.random_interior_cell(rand), Board.random_side(rand));
        board.add_enclosed_space(initial_room, initial_placement_instructions);
        initial_room.grow_to_random_size(rand);

        // Let the rest of the board emerge
        while (!board.too_congested() && board.unused_doors_left()) {
            Door door_to_nowhere = board.get_random_unused_door();
            PlacementInstructions placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
            EnclosedSpace new_space = switch (board.choose_random_growth_option(rand)) {
                case NEW_ROOM -> new Room();
                case NEW_HALLWAY -> new Hallway();
            };
            board.add_enclosed_space(new_space, placement_instructions);
            new_space.grow_to_random_size(rand);
        }
        return board;
    }

    public Board generate_random_board2() {
        Board board = new Board(50, 50);

        // Bootstrap the initial board generation process
        Room initial_room = new Room();
        PlacementInstructions initial_placement_instructions = new PlacementInstructions(board.random_interior_cell(rand), Board.random_side(rand));
        board.add_enclosed_space(initial_room, initial_placement_instructions);
        initial_room.grow_to_random_size(rand);

        return board;
    }
}
