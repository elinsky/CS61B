package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * A board is a size.
 */
public class Board {
    private final double MAX_CONGESTION = 0.25;
    private final double PROB_ROOM = 0.5;
    private final double PROB_HALLWAY = 0.5;
    private final int SEED = 50;
    private final int height;
    private final int width;
    TETile[][] board;
    private HashSet<Door> doors;
    private HashSet<Room> rooms;
    private HashSet<Hallway> hallways;
    private HashSet<EnclosedSpace> spaces;



    public Board(int height, int width) {
        // TODO - make sure the minimum board size is 9x9
        this.height = height;
        this.width = width;
        this.board = initialize_board(height, width);
        this.doors = new HashSet<>();
        this.rooms = new HashSet<>();
        this.hallways = new HashSet<>();
        this.spaces = new HashSet<>();
    }

    /**
     * Determines if the board has any doors left that don't connect to a neighbor.  It also checks to make sure the
     * unused doors have adequate space to build off of.
     * @return boolean.  True = there are doors left that we can build off.  False = No doors left we can build off.
     */
    public boolean unused_doors_left() {
        // TODO
        return false;
    }

    /**
     * This method will return a random door on the board that does not have a neighbor.  It will also only return a
     * door that has enough space nearby for a new room or hallway.
     * @return Door on the map that doesn't have a neighbor.
     */
    public Door get_random_unused_door() {
        // TODO
        return null;
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

    /**
     * Utility method to return a random side.
     * @return Randomly selected Side
     */
    public static Side random_side(Random rand) {
        // TODO consider moving this to a static utility class
        return switch (RandomUtils.uniform(rand, 4)) {
            case 0 -> Side.TOP;
            case 1 -> Side.BOTTOM;
            case 2 -> Side.LEFT;
            case 3 -> Side.RIGHT;
            default -> Side.TOP;
        };
    }

    public static Side random_side_except(Random rand, Side exclude) {
        // TODO consider moving this to a static utility class
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

    public boolean valid_point(Point point) {
        return point.x() >= 0 && point.x() < width() && point.y() >= 0 && point.y() < height();
    }

    public boolean valid_points(List<Point> points) {
        for (Point point : points) {
            if (point.x() < 0 || point.x() >= width() || point.y() < 0 || point.y() >= height()) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method takes a newly initialized room/hallway, and places it on the board, and adds the first door to the room/hallway.
     * @param enclosedSpace The room/hallway you want to place on the board.
     * @param placement_instructions The build instructions for the room/hallway.  The location is the center of the room/hallway.  The side
     *                           is the side to put the door on.
     */
    public void add_enclosed_space(EnclosedSpace enclosedSpace, PlacementInstructions placement_instructions) {
        // TODO
        // Note that the initial door can be open or closed.  This method needs to handle that.  Its closed for the initial
        // room.  But afterwards, all other rooms should have the initial door be open (because its connected to another
        // area already.
        // TODO - For now assume that the placement instructions are valid.  Later you can add some validation.
        // TODO - For now, assume that a space hasn't been added already.  Later you can double check this.
        spaces.add(enclosedSpace);
        draw_space(placement_instructions);
        enclosedSpace.set_initial_placement(placement_instructions, this);
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
        Point center = placement.location();
        Point top_left = new Point(center.x() - 1, center.y() + 1);
        Point bottom_right = new Point(center.x() + 1, center.y() - 1);
        Point door = switch (placement.side_for_door()) {
            case TOP -> new Point(center.x(), center.y() + 1);
            case BOTTOM -> new Point(center.x(), center.y() - 1);
            case LEFT -> new Point(center.x() - 1, center.y());
            case RIGHT -> new Point(center.x() + 1, center.y());
        };

        draw_rectangle(top_left, bottom_right, Tileset.WALL);
        set_tile(center, Tileset.FLOOR);
        set_tile(door, Tileset.FLOOR);
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

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void add_door(Door door) {
        // TODO Do I need this?
        doors.add(door);
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
     * @return Point on the board.
     */
    public Point random_interior_cell(Random rand) {
        int x_loc = RandomUtils.uniform(rand, 4, width - 4);
        int y_loc = RandomUtils.uniform(rand, 4, height - 4);
        return new Point(x_loc, y_loc);
    }

    public void set_tile(Point cell, TETile tile) {
        board[cell.x()][cell.y()] = tile;
    }

    /**
     * This method determines if the board is getting too congested or not.  We don't want to add more rooms or
     * hallways to a congested board.
     * @return Boolean.  True = the board is too congested.  False = the board has enough room to keep adding hallways
     * and rooms.
     */
    public boolean too_congested() {
        return perc_full() > MAX_CONGESTION;
    }

    private double perc_full() {
        int total_cells = width * height;
        int cells_full = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; x < height; y++) {
                if (board[x][y] != Tileset.NOTHING) {
                    cells_full++;
                }
            }
        }
        return (float) cells_full / (float) total_cells;
    }

    private TETile[][] initialize_board(int height, int width) {
        TETile[][] board = new TETile[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = Tileset.NOTHING;
            }
        }
        return board;
    }
}
