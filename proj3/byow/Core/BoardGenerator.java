package byow.Core;

import java.util.Random;

import static byow.Core.SideUtilities.random_side;


/**
 * This class generates a game map.  You can either generate a map with a random seed, or let the class choose a seed
 * for you.  Maps are defined as a 2-dimensional array of TETiles.
 */
public class BoardGenerator {
    Random rand;
    int SEED = 31;
    private final double PROB_ROOM = 1.0;
    private final double PROB_HALLWAY = 0.0; // TODO move this back to 0.50 after you implement hallways.

    public BoardGenerator() {
        this.rand = new Random(SEED);
    }

    public Board generate_random_board() {
        Board board = new Board(50, 50);

        // Bootstrap the initial board generation process
        // Create first room
        Point new_build_site = board.random_interior_cell(rand);
        Side door_side = Side.TOP;
        Point second_door_site = new Point(new_build_site.x(), new_build_site.y() + 2);
        PlacementInstructions initial_placement_instructions = new PlacementInstructions(new_build_site, door_side, second_door_site);
        Room initial_room = new Room(board, initial_placement_instructions, rand);
        initial_room.grow_to_random_size(rand);

        // Create second room
        Door initial_door = initial_room.get_doors().remove(0);
        PlacementInstructions second_room_plan = initial_door.get_placement_instructions_for_neighbor();
        Room second_room = new Room(board, second_room_plan, rand);
        second_room.grow_to_random_size(rand);

        board.unused_doors_left();


        // Let the rest of the board emerge
        while (!board.too_congested() && board.unused_doors_left()) {
            Door door_to_nowhere = board.get_random_door_to_build_off(rand);
            PlacementInstructions placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
            EnclosedSpace new_space = switch (choose_random_growth_option(rand)) {
                case NEW_ROOM -> new Room(board, placement_instructions, rand);
                case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
            };
            new_space.grow_to_random_size(rand);
        }
        return board;
    }

    public Board generate_random_board2() {
        Board board = new Board(50, 50);

        // Bootstrap the initial board generation process
        // Create first room
        Point new_build_site = board.random_interior_cell(rand);
        Side door_side = Side.TOP;
        Point second_door_site = new Point(new_build_site.x(), new_build_site.y() + 2);
        PlacementInstructions initial_placement_instructions = new PlacementInstructions(new_build_site, door_side, second_door_site);
        Room initial_room = new Room(board, initial_placement_instructions, rand);
        initial_room.grow_to_random_size(rand);

        // Create second room
        Door initial_door = initial_room.get_doors().remove(0);
        PlacementInstructions second_room_plan = initial_door.get_placement_instructions_for_neighbor();
        Room second_room = new Room(board, second_room_plan, rand);
        second_room.grow_to_random_size(rand);

        return board;
    }

    public Board gen3(Board board) {
        Door door_to_nowhere = board.get_random_door_to_build_off(rand);
        PlacementInstructions placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        EnclosedSpace new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);


        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);

        door_to_nowhere = board.get_random_door_to_build_off(rand);
        placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
        new_space = switch (choose_random_growth_option(rand)) {
            case NEW_ROOM -> new Room(board, placement_instructions, rand);
            case NEW_HALLWAY -> new Hallway(board, placement_instructions, rand);
        };
        new_space.grow_to_random_size(rand);
        return board;
    }

    /**
     * This method has the board choose a random growth option for you.  Options include: NEW_ROOM and NEW_HALLWAY.
     * @return Option
     */
    public Option choose_random_growth_option(Random rand) {
        // TODO consider moving this to a static utility class
        double[] probabilities = {PROB_ROOM, PROB_HALLWAY};
        return switch (RandomUtils.discrete(rand, probabilities)) {
            case 0 -> Option.NEW_ROOM;
            case 1 -> Option.NEW_HALLWAY;
            default -> throw new IllegalStateException("Unexpected value: " + RandomUtils.discrete(rand, probabilities));
        };
    }


}
