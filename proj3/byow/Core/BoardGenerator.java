package byow.Core;

import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

/**
 * A board is a size.
 */
public class BoardGenerator {
    private final Board board;
    private final ArrayList<Door> unused_doors;
    int seed;

    public BoardGenerator(int width, int height, int seed) {
        this.seed = seed;
        // TODO - make sure the minimum board size is 9x9
        this.board = new Board(height, width, Tileset.NOTHING);
        this.unused_doors = new ArrayList<>();
        Random rand = new Random(seed);

        // Bootstrap the initial world generation process
        // Create first room
        int rand_x = RandomUtils.uniform(rand, 4, width - 4);
        int rand_y = RandomUtils.uniform(rand, 4, height - 4);
        Point rand_start = new Point(rand_x, rand_y);
        Point second_door_site = new Point(rand_start.x(), rand_start.y() + 2);
        RoomBuildPlans initial_placement_instructions = new RoomBuildPlans(rand_start, Side.TOP, second_door_site);
        Room initial_room = new Room(initial_placement_instructions, rand, board);
        unused_doors.addAll(initial_room.get_doors());

        // Create second room
        Door initial_door = initial_room.get_doors().remove(0);
        RoomBuildPlans second_room_plan = initial_door.get_build_plans_for_neighbor();
        Room second_room = new Room(second_room_plan, rand, this.board);
        unused_doors.addAll(second_room.get_doors());

        // Let the rest of the board emerge
        while (unused_doors.size() > 0) {
            Door door_to_nowhere = this.get_random_door_to_build_off(rand);
            RoomBuildPlans adjoining_room_plans = door_to_nowhere.get_build_plans_for_neighbor();
            Room adjoining_room = new Room(adjoining_room_plans, rand, this.board);
            unused_doors.addAll(adjoining_room.get_doors());
            unused_doors.removeIf(x -> !usable_placement_instructions(x.get_build_plans_for_neighbor()));
        }
    }

    public Board get_board() {
        return board;
    }

    /**
     * This method will return a random door on the board that does not have a neighbor.  It will also only return a
     * door that has enough space nearby for a new room or hallway.
     *
     * @return Door on the map that doesn't have a neighbor.
     */
    private Door get_random_door_to_build_off(Random rand) {
        if (unused_doors.size() == 0) {
            throw new RuntimeException("Ran out of usable doors");
        }
        java.util.Collections.shuffle(unused_doors, rand);
        Door unused_door = unused_doors.remove(0);
        if (usable_placement_instructions(unused_door.get_build_plans_for_neighbor())) {
            return unused_door;
        } else {
            return get_random_door_to_build_off(rand);
        }
    }

    private boolean usable_placement_instructions(RoomBuildPlans plans) {
        // For now this just checks if all the points are on the board and empty.  Later I might need to also check to
        // see if the door connects to another door.
        Point center = plans.location();
        for (int x = center.x() - 1; x < center.x() + 2; x++) {
            for (int y = center.y() + 1; y > center.y() - 2; y--) {
                Point cell = new Point(x, y);
                if (!board.is_valid_point(cell) || board.get_cell(cell) != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }
}