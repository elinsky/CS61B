package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    protected Point initial_door_location; // This is the door created when the room is created.
    protected Side initial_door_side;
    protected Point initial_center;
    protected Point top_left;
    protected Point bottom_right;
    protected ArrayList<Door> doors;
    protected Random rand;
    protected Board board;

    Room(RoomBuildPlans plans, Random rand, Board board) {
        this.rand = rand;
        this.board = board;
        initial_door_side = plans.side_for_door();
        initial_center = plans.location();
        top_left = new Point(initial_center.x() - 1, initial_center.y() + 1);
        bottom_right = new Point(initial_center.x() + 1, initial_center.y() - 1);
        switch (initial_door_side) {
            // TODO - I think I should be able to refactor the switch statments away by adding polymorphisim.
            case TOP -> initial_door_location = new Point(initial_center.x(), initial_center.y() + 1);
            case BOTTOM -> initial_door_location = new Point(initial_center.x(), initial_center.y() - 1);
            case LEFT -> initial_door_location = new Point(initial_center.x() - 1, initial_center.y());
            case RIGHT -> initial_door_location = new Point(initial_center.x() + 1, initial_center.y());
        }
        draw_room(plans);
        this.doors = new ArrayList<>();
        Door first_door = new Door(initial_door_location, true, initial_door_side, board);
        doors.add(first_door);
        grow_to_random_size(rand);
    }

    public ArrayList<Door> get_doors() {
        return doors;
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
    private void grow_to_random_size(Random rand) {
        double PROB_GROW = 0.90;
        while (RandomUtils.uniform(rand) < PROB_GROW) {
            Side grow_side = SideUtilities.random_side_except(rand, initial_door_side);
            if (space_to_grow(grow_side)) {
                expand_wall(grow_side);
            }
        }
        set_rest_of_doors();
    }

    private void draw_room(RoomBuildPlans plans) {
        Point center = plans.location();
        Point existing_door_to_open = plans.existing_door_location();
        Point top_left = new Point(center.x() - 1, center.y() + 1);
        Point bottom_right = new Point(center.x() + 1, center.y() - 1);
        Point new_door = switch (plans.side_for_door()) {
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

    private void draw_rectangle(Point top_left, Point bottom_right, TETile type) {
        for (int x = top_left.x(); x <= bottom_right.x(); x++) {
            for (int y = top_left.y(); y >= bottom_right.y(); y--) {
                board.set_cell(new Point(x, y), type);
            }
        }
    }

    private boolean space_to_grow(Side grow_side) {
        ArrayList<Point> new_wall = new_wall_points(grow_side);
        return board.are_valid_points(new_wall) && are_cells_unoccupied(new_wall);
    }

    private void expand_wall(Side grow_side) {
        ArrayList<Point> new_wall = new_wall_points(grow_side);
        for (Point wall : new_wall) {
            board.set_cell(wall, Tileset.WALL);
        }
        ArrayList<Point> new_floor = new_floor_points(grow_side);
        for (Point floor : new_floor) {
            board.set_cell(floor, Tileset.FLOOR);
        }
        update_room_dimensions(grow_side);
    }

    private void update_room_dimensions(Side expansion_side) {
        // TODO - I think I should be able to refactor the switch statments away by adding polymorphisim.
        switch (expansion_side) {
            case TOP -> top_left = new Point(top_left.x(), top_left.y() + 1);
            case BOTTOM -> bottom_right = new Point(bottom_right.x(), bottom_right.y() - 1);
            case LEFT -> top_left = new Point(top_left.x() - 1, top_left.y());
            case RIGHT -> bottom_right = new Point(bottom_right.x() + 1, bottom_right.y());
        }
    }

    private ArrayList<Point> new_floor_points(Side expansion_side) {
        // TODO - I think I should be able to refactor the switch statments away by adding polymorphisim.
        ArrayList<Point> new_floor = new ArrayList<>();
        switch (expansion_side) {
            case TOP -> {
                for (int x = top_left.x() + 1; x < bottom_right.x(); x++) {
                    new_floor.add(new Point(x, top_left.y()));
                }
            }
            case BOTTOM -> {
                for (int x = top_left.x() + 1; x < bottom_right.x(); x++) {
                    new_floor.add(new Point(x, bottom_right.y()));
                }
            }
            case LEFT -> {
                for (int y = top_left.y() - 1; y > bottom_right.y(); y--) {
                    new_floor.add(new Point(top_left.x(), y));
                }
            }
            case RIGHT -> {
                for (int y = top_left.y() - 1; y > bottom_right.y(); y--) {
                    new_floor.add(new Point(bottom_right.x(), y));
                }
            }
        }
        return new_floor;
    }

    private ArrayList<Point> new_wall_points(Side expansion_side) {
        // TODO - I think I should be able to refactor the switch statments away by adding polymorphisim.
        ArrayList<Point> new_wall = new ArrayList<>();
        switch (expansion_side) {
            case TOP -> {
                for (int x = top_left.x(); x <= bottom_right.x(); x++) {
                    new_wall.add(new Point(x, top_left.y() + 1));
                }
            }
            case BOTTOM -> {
                for (int x = top_left.x(); x <= bottom_right.x(); x++) {
                    new_wall.add(new Point(x, bottom_right.y() - 1));
                }
            }
            case LEFT -> {
                for (int y = top_left.y(); y >= bottom_right.y(); y--) {
                    new_wall.add(new Point(top_left.x() - 1, y));
                }
            }
            case RIGHT -> {
                for (int y = top_left.y(); y >= bottom_right.y(); y--) {
                    new_wall.add(new Point(bottom_right.x() + 1, y));
                }
            }
        }
        return new_wall;
    }

    private void set_rest_of_doors() {
        // TODO - I think I should be able to refactor the switch statments away by adding polymorphisim.
        if (initial_door_side != Side.LEFT) {
            ArrayList<Point> left_wall_points = vertical_points_between(top_left, new Point(top_left.x(), bottom_right.y()));
            java.util.Collections.shuffle(left_wall_points, rand);
            Point left_wall_point = left_wall_points.remove(0);
            Door left_door = new Door(left_wall_point, false, Side.LEFT, board);
            doors.add(left_door);
        }

        if (initial_door_side != Side.RIGHT) {
            ArrayList<Point> right_wall_points = vertical_points_between(new Point(bottom_right.x(),top_left.y()), bottom_right);
            java.util.Collections.shuffle(right_wall_points, rand);
            Point right_wall_point = right_wall_points.remove(0);
            Door right_door = new Door(right_wall_point, false, Side.RIGHT, board);
            doors.add(right_door);
        }

        if (initial_door_side != Side.TOP) {
            ArrayList<Point> top_wall_points = horizontal_points_between(top_left, new Point(bottom_right.x(), top_left.y()));
            java.util.Collections.shuffle(top_wall_points, rand);
            Point top_wall_point = top_wall_points.remove(0);
            Door top_door = new Door(top_wall_point, false, Side.TOP, board);
            doors.add(top_door);
        }

        if (initial_door_side != Side.BOTTOM) {
            ArrayList<Point> bottom_wall_points = horizontal_points_between(new Point(top_left.x(), bottom_right.y()), bottom_right);
            java.util.Collections.shuffle(bottom_wall_points, rand);
            Point bottom_wall_point = bottom_wall_points.remove(0);
            Door bottom_door = new Door(bottom_wall_point, false, Side.BOTTOM, board);
            doors.add(bottom_door);
        }
    }


    // Exclusive of the end points
    private ArrayList<Point> vertical_points_between(Point a, Point b) {
        // TODO - there is probably a way to clean up these two 'points between' methods.
        ArrayList<Point> points = new ArrayList<>();
        Point top_point;
        Point bottom_point;
        if (a.y() >= b.y()) {
            top_point = a;
            bottom_point = b;
        } else {
            top_point = b;
            bottom_point = a;
        }
        for (int y = top_point.y() - 1; y > bottom_point.y(); y--) {
            points.add(new Point(a.x(), y));
        }
        return points;
    }

    // Exclusive of the end points
    private ArrayList<Point> horizontal_points_between(Point a, Point b) {
        // TODO - there is probably a way to clean up these two 'points between' methods.
        ArrayList<Point> points = new ArrayList<>();
        Point left_point;
        Point right_point;
        if (a.x() <= b.x()) {
            left_point = a;
            right_point = b;
        } else {
            left_point = b;
            right_point = a;
        }
        for (int x = left_point.x() + 1; x < right_point.x(); x++) {
            points.add(new Point(x, a.y()));
        }
        return points;
    }

    private boolean are_cells_unoccupied(List<Point> cells) {
        for (Point cell : cells) {
            if (board.get_cell(cell) != Tileset.NOTHING) {
                return false;
            }
        }
        return true;
    }
}
