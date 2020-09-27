package byow.Core;

import byow.TileEngine.Tileset;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Room extends EnclosedSpace {
    private double PROB_GROW = 0.93;
    private Point initial_door_location;
    private Side initial_door_side;
    private Point initial_center;
    private Point top_left;
    private Point bottom_right;
    private Board board;

    public Room() {

    }

    /**
     * To call this method, a room needs to be (1) initial size, which is 3x3, (2) placed on a board, and (3) has to
     * have its first door created.
     * When called on a newly created room, with , this method will grow the room to a random size.  The wall with the
     * exiting door will stay fixed, but the other three walls may be pushed out a random length.  If a wall of a room
     * is pushed up against another wall, it is unable to be pushed out further.
     * After the room has reached its final size, doors will be placed on the 3 sides without doors.  These doors will
     * be closed by default.
     */
    @Override
    public void grow_to_random_size(Random rand) {
        while (RandomUtils.uniform(rand) < PROB_GROW) {
            Side grow_side = Board.random_side_except(rand, initial_door_side);
            if (room_to_grow(grow_side)) {
                expand_wall(grow_side);
            }
        }
        set_rest_of_doors();
    }

    @Override
    public void set_initial_placement(PlacementInstructions initial_placement, Board board) {
        // TODO move into the abstract class
        initial_door_side = initial_placement.side_for_door();
        initial_center = initial_placement.location();
        top_left = new Point(initial_center.x() - 1, initial_center.y() + 1);
        bottom_right = new Point(initial_center.x() + 1, initial_center.y() - 1);
        switch (initial_door_side) {
            case TOP -> initial_door_location = new Point(initial_center.x(), initial_center.y() + 1);
            case BOTTOM -> initial_door_location = new Point(initial_center.x(), initial_center.y() - 1);
            case LEFT -> initial_door_location = new Point(initial_center.x() - 1, initial_center.y());
            case RIGHT -> initial_door_location = new Point(initial_center.x() + 1, initial_center.y());
        }
        this.board = board;
    }

    private boolean room_to_grow(Side grow_side) {
        ArrayList<Point> new_wall = new_wall_points(grow_side);
        return board.valid_points(new_wall) && board.are_cells_unoccupied(new_wall);
    }

    private void expand_wall(Side grow_side) {
        ArrayList<Point> new_wall = new_wall_points(grow_side);
        for (Point wall : new_wall) {
            board.set_tile(wall, Tileset.WALL);
        }
        ArrayList<Point> new_floor = new_floor_points(grow_side);
        for (Point floor : new_floor) {
            board.set_tile(floor, Tileset.FLOOR);
        }
        update_room_dimensions(grow_side);
    }

    private void update_room_dimensions(Side expansion_side) {
        switch (expansion_side) {
            case TOP -> top_left = new Point(top_left.x(), top_left.y() + 1);
            case BOTTOM -> bottom_right = new Point(bottom_right.x(), bottom_right.y() - 1);
            case LEFT -> top_left = new Point(top_left.x() - 1, top_left.y());
            case RIGHT -> bottom_right = new Point(bottom_right.x() + 1, bottom_right.y());
        }
    }

    private ArrayList<Point> new_floor_points(Side expansion_side) {
        ArrayList<Point> new_floor = new ArrayList<>();
        switch (expansion_side) {
            case TOP -> {
                for (int x = top_left.x() + 1; x < bottom_right.x(); x++) {
                    new_floor.add(new Point(x, top_left.y()));
                };
            }
            case BOTTOM -> {
                for (int x = top_left.x() + 1; x < bottom_right.x(); x++) {
                    new_floor.add(new Point(x, bottom_right.y()));
                };
            }
            case LEFT -> {
                for (int y = top_left.y() - 1; y > bottom_right.y(); y--) {
                    new_floor.add(new Point(top_left.x(), y));
                };
            }
            case RIGHT -> {
                for (int y = top_left.y() - 1; y > bottom_right.y(); y--) {
                    new_floor.add(new Point(bottom_right.x(), y));
                };
            }
        }
        return new_floor;
    }

    private ArrayList<Point> new_wall_points(Side expansion_side) {
        ArrayList<Point> new_wall = new ArrayList<>();
        switch (expansion_side) {
            case TOP -> {
                for (int x = top_left.x(); x <= bottom_right.x(); x++) {
                    new_wall.add(new Point(x, top_left.y() + 1));
                };
            }
            case BOTTOM -> {
                for (int x = top_left.x(); x <= bottom_right.x(); x++) {
                    new_wall.add(new Point(x, bottom_right.y() - 1));
                };
            }
            case LEFT -> {
                for (int y = top_left.y(); y >= bottom_right.y(); y--) {
                    new_wall.add(new Point(top_left.x() - 1, y));
                };
            }
            case RIGHT -> {
                for (int y = top_left.y(); y >= bottom_right.y(); y--) {
                    new_wall.add(new Point(bottom_right.x() + 1, y));
                };
            }
        }
        return new_wall;
    }

    private void set_rest_of_doors() {
        // TODO
    }

}
