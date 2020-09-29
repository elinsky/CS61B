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
public class Board {
    private final double MAX_CONGESTION = 1.0;
    private final int SEED = 50;
    private final int height;
    private final int width;
    TETile[][] board;
    private HashSet<Door> used_doors;
    private ArrayList<Door> unused_doors;
    private HashSet<Room> rooms;
    private HashSet<Hallway> hallways;
    private HashSet<EnclosedSpace> spaces;

    public Board(int height, int width) {
        // TODO - make sure the minimum board size is 9x9
        this.height = height;
        this.width = width;
        this.board = initialize_board(height, width);
        this.used_doors = new HashSet<>();
        this.unused_doors = new ArrayList<>();
        this.rooms = new HashSet<>();
        this.hallways = new HashSet<>();
        this.spaces = new HashSet<>();
    }

    /**
     * Determines if the board has any doors left that don't connect to a neighbor.  It also checks to make sure the
     * unused doors have adequate space to build off of.
     *
     * @return boolean.  True = there are doors left that we can build off.  False = No doors left we can build off.
     */
    public boolean unused_doors_left() {
        remove_unplaceable_doors();
        return unused_doors.size() > 0;
    }

    private void remove_unplaceable_doors() {
        unused_doors.removeIf(x -> !usable_placement_instructions(x.get_placement_instructions_for_neighbor()));
    }

    /**
     * This method will return a random door on the board that does not have a neighbor.  It will also only return a
     * door that has enough space nearby for a new room or hallway.
     *
     * @return Door on the map that doesn't have a neighbor.
     */
    public Door get_random_door_to_build_off(Random rand) {
        if (unused_doors.size() == 0) {
            throw new RuntimeException("Ran out of usable doors");
        }
        java.util.Collections.shuffle(unused_doors, rand);
        Door unused_door = unused_doors.remove(0);
        used_doors.add(unused_door);
        if (usable_placement_instructions(unused_door.get_placement_instructions_for_neighbor())) {
            return unused_door;
        } else {
            return get_random_door_to_build_off(rand);
        }
    }

    public boolean usable_placement_instructions(PlacementInstructions placement_instructions) {
        // For now this just checks if all the points are on the board and empty.  Later I might need to also check to
        // see if the door connects to another door.
        Point center = placement_instructions.location();
        for (int x = center.x() - 1; x < center.x() + 2; x++) {
            for (int y = center.y() + 1; y > center.y() - 2; y--) {
                Point cell = new Point(x, y);
                if (!valid_point(cell) || get_tile(cell) != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean valid_point(Point point) {
        return point.x() >= 0 && point.x() < width() && point.y() >= 0 && point.y() < height();
    }

    public boolean valid_points(List<Point> points) {
        for (Point point : points) {
            if (!valid_point(point)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method takes a newly initialized room/hallway, and places it on the board, and adds the first door to the room/hallway.
     *
     * @param enclosedSpace          The room/hallway you want to place on the board.
     * @param placement_instructions The build instructions for the room/hallway.  The location is the center of the room/hallway.  The side
     *                               is the side to put the door on.
     */
    public void add_enclosed_space(EnclosedSpace enclosedSpace, PlacementInstructions placement_instructions) {
        // TODO
        // Note that the initial door can be open or closed.  This method needs to handle that.  Its closed for the initial
        // room.  But afterwards, all other rooms should have the initial door be open (because its connected to another
        // area already.
        // TODO - For now assume that the placement instructions are valid.  Later you can add some validation.
        // TODO - For now, assume that a space hasn't been added already.  Later you can double check this.
        // TODO - this method seems weird.  Can I refactor to get rid of it somehow?
        spaces.add(enclosedSpace);
        draw_space(placement_instructions);
    }

    public boolean are_cells_unoccupied(List<Point> cells) {
        for (Point cell : cells) {
            if (board[cell.x()][cell.y()] != Tileset.NOTHING) {
                return false;
            }
        }
        return true;
    }

    private void draw_space(PlacementInstructions placement) {
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
        set_tile(center, Tileset.FLOOR);
        set_tile(new_door, Tileset.FLOOR);
        set_tile(existing_door_to_open, Tileset.FLOOR);
    }


    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void add_unused_door(Door door) {
        unused_doors.add(door);
    }

    public void add_used_door(Door door) {
        used_doors.add(door);
    }

    public void add_room(Room room) {
        // TODO Do I need this?
        rooms.add(room);
    }

    public TETile[][] get_board() {
        return board;
    }

    /**
     * Returns a random interior cell on the board.  This method doesn't return cells that are too close to the edge of
     * the board.  Too close = within 4 cells from the edge of the board.
     *
     * @return Point on the board.
     */
    public Point random_interior_cell(Random rand) {
        int x_loc = RandomUtils.uniform(rand, 4, width - 4);
        int y_loc = RandomUtils.uniform(rand, 4, height - 4);
        return new Point(x_loc, y_loc);
    }

    /**
     * Given a Point and a TETile, this method sets that point to the desired tile.
     *
     * @param cell Point on the board.
     * @param tile Desired TETile.
     */
    public void set_tile(Point cell, TETile tile) {
        board[cell.x()][cell.y()] = tile;
    }

    public TETile get_tile(Point cell) {
        return board[cell.x()][cell.y()];
    }

    /**
     * This method determines if the board is getting too congested or not.  We don't want to add more enclosed spaces
     * to a congested board.
     *
     * @return Boolean.  True = the board is too congested.  False = the board has enough room to keep adding enclosed
     * spaces.
     */
    public boolean too_congested() {
        return perc_full() > MAX_CONGESTION;
    }

    /**
     * Calculates how full the board is.  NOTHING cells count as unused.  All other cells are counted as used.
     *
     * @return Percentage of the board that is full.
     */
    private double perc_full() {
        int total_cells = width * height;
        int cells_full = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y] != Tileset.NOTHING) {
                    cells_full++;
                }
            }
        }
        return (float) cells_full / (float) total_cells;
    }

    /**
     * This method creates a board and sets all its cells to Tileset.NOTHING.  It is called on board creation.
     *
     * @param height height of the desired board in cells.
     * @param width  width of the desired board in cells.
     * @return 2-Dimensional array of TETiles, each filled with Tileset.NOTHING
     */
    private TETile[][] initialize_board(int height, int width) {
        TETile[][] board = new TETile[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = Tileset.NOTHING;
            }
        }
        return board;
    }

    private void drawHorizontalRow(TETile type, int x_start, int y, int length) {
        for (int x = x_start; x < x_start + length; x++) {
            board[x][y] = type;
        }
    }

    private void draw_rectangle(Point top_left, Point bottom_right, TETile type) {
        for (int x = top_left.x(); x <= bottom_right.x(); x++) {
            for (int y = top_left.y(); y >= bottom_right.y(); y--) {
                board[x][y] = type;
            }
        }
    }
}
