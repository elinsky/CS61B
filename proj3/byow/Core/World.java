package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * A board is a size.
 */
public class World {
    private final int SEED = 50;
    private final Board board;
    private final ArrayList<Door> unused_doors;
    int seed;

    public World(int height, int width, int seed) {
        this.seed = seed;
        // TODO - make sure the minimum board size is 9x9
        this.board = new Board(height, width, Tileset.NOTHING);
        this.unused_doors = new ArrayList<>();
        HashSet<EnclosedSpace> rooms = new HashSet<>();
        Random rand = new Random(seed);

        // Bootstrap the initial world generation process
        // Create first room
        int rand_x = RandomUtils.uniform(rand, 4, width - 4);
        int rand_y = RandomUtils.uniform(rand, 4, height - 4);
        Point rand_start = new Point(rand_x, rand_y);
        Point second_door_site = new Point(rand_start.x(), rand_start.y() + 2);
        PlacementInstructions initial_placement_instructions = new PlacementInstructions(rand_start, Side.TOP, second_door_site);
        EnclosedSpace initial_room = new EnclosedSpace(this, initial_placement_instructions, rand, board);
        initial_room.grow_to_random_size(rand);

        // Create second room
        Door initial_door = initial_room.get_doors().remove(0);
        PlacementInstructions second_room_plan = initial_door.get_placement_instructions_for_neighbor();
        EnclosedSpace second_room = new EnclosedSpace(this, second_room_plan, rand, this.board);
        second_room.grow_to_random_size(rand);

        // Let the rest of the board emerge
        while (this.unused_doors_left()) {
            Door door_to_nowhere = this.get_random_door_to_build_off(rand);
            PlacementInstructions placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
            EnclosedSpace new_space = new EnclosedSpace(this, placement_instructions, rand, this.board);
            new_space.grow_to_random_size(rand);
        }
    }

    // TODO - probably delete this later?  Not really sure here.
    public void render() {
        board.render_frame();
    }

    public void add_door(Door door) {
        unused_doors.add(door);
    }

    public boolean are_cells_unoccupied(List<Point> cells) {
        for (Point cell : cells) {
            if (board.get_cell(cell) != Tileset.NOTHING) {
                return false;
            }
        }
        return true;
    }

    public void draw_space(PlacementInstructions placement) {
        // TODO consider moving this method to the EnclosedSpace class
        Point center = placement.location();
        Point existing_door_to_open = placement.existing_door_location();
        Point top_left = new Point(center.x() - 1, center.y() + 1);
        Point bottom_right = new Point(center.x() + 1, center.y() - 1);
        Point new_door = switch (placement.side_for_door()) {
            case TOP -> new Point(center.x(), center.y() + 1);
            case BOTTOM -> new Point(center.x(), center.y() - 1);
            case LEFT -> new Point(center.x() - 1, center.y());
            case RIGHT -> new Point(center.x() + 1, center.y());
        };

        draw_rectangle(top_left, bottom_right, Tileset.WALL);
        board.set_cell(center, Tileset.FLOOR);
        board.set_cell(new_door, Tileset.FLOOR);
        board.set_cell(existing_door_to_open, Tileset.FLOOR);
    }

    /**
     * Determines if the board has any doors left that don't connect to a neighbor.  It also checks to make sure the
     * unused doors have adequate space to build off of.
     *
     * @return boolean.  True = there are doors left that we can build off.  False = No doors left we can build off.
     */
     private boolean unused_doors_left() {
         // First remove doors that aren't usable
         unused_doors.removeIf(x -> !usable_placement_instructions(x.get_placement_instructions_for_neighbor()));
         return unused_doors.size() > 0;
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
        if (usable_placement_instructions(unused_door.get_placement_instructions_for_neighbor())) {
            return unused_door;
        } else {
            return get_random_door_to_build_off(rand);
        }
    }

    private boolean usable_placement_instructions(PlacementInstructions placement_instructions) {
        // For now this just checks if all the points are on the board and empty.  Later I might need to also check to
        // see if the door connects to another door.
        Point center = placement_instructions.location();
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

    private void drawHorizontalRow(TETile type, int x_start, int y, int length) {
        for (int x = x_start; x < x_start + length; x++) {
            board.set_cell(new Point(x, y), type);
        }
    }

    private void draw_rectangle(Point top_left, Point bottom_right, TETile type) {
        for (int x = top_left.x(); x <= bottom_right.x(); x++) {
            for (int y = top_left.y(); y >= bottom_right.y(); y--) {
                board.set_cell(new Point(x, y), type);
            }
        }
    }
}
