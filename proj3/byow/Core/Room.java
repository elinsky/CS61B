package byow.Core;

import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Room extends EnclosedSpace {
    private double PROB_GROW = 0.90;

    Room(Board board, PlacementInstructions initial_placement_instructions, Random rand) {
        super(board, initial_placement_instructions, rand);
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
            Side grow_side = SideUtilities.random_side_except(rand, initial_door_side);
            if (room_to_grow(grow_side)) {
                expand_wall(grow_side);
            }
        }
        set_rest_of_doors();
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
        if (initial_door_side != Side.LEFT) {
            ArrayList<Point> left_wall_points = vertical_points_between(top_left, new Point(top_left.x(), bottom_right.y()));
            java.util.Collections.shuffle(left_wall_points, rand);
            Point left_wall_point = left_wall_points.remove(0);
            Door left_door = new Door(board, left_wall_point, false, Side.LEFT);
            doors.add(left_door);
            board.add_unused_door(left_door);
        }

        if (initial_door_side != Side.RIGHT) {
            ArrayList<Point> right_wall_points = vertical_points_between(new Point(bottom_right.x(),top_left.y()), bottom_right);
            java.util.Collections.shuffle(right_wall_points, rand);
            Point right_wall_point = right_wall_points.remove(0);
            Door right_door = new Door(board, right_wall_point, false, Side.RIGHT);
            doors.add(right_door);
            board.add_unused_door(right_door);
        }

        if (initial_door_side != Side.TOP) {
            ArrayList<Point> top_wall_points = horizontal_points_between(top_left, new Point(bottom_right.x(), top_left.y()));
            java.util.Collections.shuffle(top_wall_points, rand);
            Point top_wall_point = top_wall_points.remove(0);
            Door top_door = new Door(board, top_wall_point, false, Side.TOP);
            doors.add(top_door);
            board.add_unused_door(top_door);
        }

        if (initial_door_side != Side.BOTTOM) {
            ArrayList<Point> bottom_wall_points = horizontal_points_between(new Point(top_left.x(), bottom_right.y()), bottom_right);
            java.util.Collections.shuffle(bottom_wall_points, rand);
            Point bottom_wall_point = bottom_wall_points.remove(0);
            Door bottom_door = new Door(board, bottom_wall_point, false, Side.BOTTOM);
            doors.add(bottom_door);
            board.add_unused_door(bottom_door);
        }
    }


    // Exclusive of the end points
    private ArrayList<Point> vertical_points_between(Point a, Point b) {
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

}
