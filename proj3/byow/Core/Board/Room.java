package byow.Core.Board;

import byow.Core.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A room is a rectangular area on a Board consisting of floor tiles surrounded by wall tiles.  This object is transient,
 * it only exists to facilitate the creation of a new board.  Rooms also have at least one door, although they may have
 * multiple doors.  A side of a room can only have one door on it.
 */
public class Room {
    protected Point initialDoorLocation; // This is the door created when the room is created.
    protected Side initialDoorSide;
    protected Point initialCenter;
    protected Point topLeft;
    protected Point bottomRight;
    protected ArrayList<Door> doors;
    protected Random rand;
    protected Board board;

    /**
     * Creates a new room.
     * @param plans location and door information for the new room.
     * @param rand Random object to be used in determining the size of the room.
     * @param board Board to place the room on.
     */
    public Room(RoomBuildPlans plans, Random rand, Board board) {
        this.rand = rand;
        this.board = board;
        initialDoorSide = plans.sideForDoor();
        initialCenter = plans.location();
        topLeft = new Point(initialCenter.x() - 1, initialCenter.y() + 1);
        bottomRight = new Point(initialCenter.x() + 1, initialCenter.y() - 1);
        switch (initialDoorSide) {
            case TOP -> initialDoorLocation = new Point(initialCenter.x(), initialCenter.y() + 1);
            case BOTTOM -> initialDoorLocation = new Point(initialCenter.x(), initialCenter.y() - 1);
            case LEFT -> initialDoorLocation = new Point(initialCenter.x() - 1, initialCenter.y());
            case RIGHT -> initialDoorLocation = new Point(initialCenter.x() + 1, initialCenter.y());
        }
        drawRoom(plans);
        this.doors = new ArrayList<>();
        Door firstDoor = new Door(initialDoorLocation, true, initialDoorSide, board);
        doors.add(firstDoor);
        growToRandomSize(rand);
    }

    /**
     * Returns a list of doors that are attached to this room.
     */
    public List<Door> getDoors() {
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
    private void growToRandomSize(Random rand) {
        double PROB_GROW = 0.90;
        while (RandomUtils.uniform(rand) < PROB_GROW) {
            Side growSide = SideUtilities.randomSideExcept(rand, initialDoorSide);
            if (hasSpaceToGrow(growSide)) {
                expandWall(growSide);
            }
        }
        setRestOfDoors();
    }

    private void drawRoom(RoomBuildPlans plans) {
        Point center = plans.location();
        Point existingDoorToOpen = plans.existingDoorLocation();
        Point topLeft = new Point(center.x() - 1, center.y() + 1);
        Point bottomRight = new Point(center.x() + 1, center.y() - 1);
        Point newDoor = switch (plans.sideForDoor()) {
            case TOP -> new Point(center.x(), center.y() + 1);
            case BOTTOM -> new Point(center.x(), center.y() - 1);
            case LEFT -> new Point(center.x() - 1, center.y());
            case RIGHT -> new Point(center.x() + 1, center.y());
        };

        drawRectangle(topLeft, bottomRight, Tileset.WALL);
        board.setCell(center, Tileset.FLOOR);
        board.setCell(newDoor, Tileset.FLOOR);
        board.setCell(existingDoorToOpen, Tileset.FLOOR);
    }

    private void drawRectangle(Point topLeft, Point bottomRight, TETile type) {
        for (int x = topLeft.x(); x <= bottomRight.x(); x++) {
            for (int y = topLeft.y(); y >= bottomRight.y(); y--) {
                board.setCell(new Point(x, y), type);
            }
        }
    }

    /**
     * Given a side, determines if there is empty space available to push the wall out, making the room bigger.
     * @param growSide
     * @return
     */
    private boolean hasSpaceToGrow(Side growSide) {
        ArrayList<Point> newWall = newWallPoints(growSide);
        return board.areValidPoints(newWall) && areCellsUnoccupied(newWall);
    }

    /**
     * Given a side, this method pushes the wall for that side out, making the room bigger.
     */
    private void expandWall(Side growSide) {
        ArrayList<Point> newWall = newWallPoints(growSide);
        for (Point wall : newWall) {
            board.setCell(wall, Tileset.WALL);
        }
        ArrayList<Point> newFloor = newFloorPoints(growSide);
        for (Point floor : newFloor) {
            board.setCell(floor, Tileset.FLOOR);
        }
        updateRoomDimensions(growSide);
    }

    /**
     * Helper method for 'expandWall'.  After a wall has been pused outward, making the room bigger, this method ensures
     * that we update the instance variables 'topLeft' and 'bottomRight' as necessary.
     * @param expansionSide
     */
    private void updateRoomDimensions(Side expansionSide) {
        switch (expansionSide) {
            case TOP -> topLeft = new Point(topLeft.x(), topLeft.y() + 1);
            case BOTTOM -> bottomRight = new Point(bottomRight.x(), bottomRight.y() - 1);
            case LEFT -> topLeft = new Point(topLeft.x() - 1, topLeft.y());
            case RIGHT -> bottomRight = new Point(bottomRight.x() + 1, bottomRight.y());
        }
    }

    /**
     * Helper method for 'expandWall'.  Given a side of a room that we want to push out by one cell, this method
     * determines which cells need to be converted to new floor.  It then returns those cells as a list of points.
     */
    private ArrayList<Point> newFloorPoints(Side expansionSide) {
        ArrayList<Point> newFloor = new ArrayList<>();
        switch (expansionSide) {
            case TOP -> {
                for (int x = topLeft.x() + 1; x < bottomRight.x(); x++) {
                    newFloor.add(new Point(x, topLeft.y()));
                }
            }
            case BOTTOM -> {
                for (int x = topLeft.x() + 1; x < bottomRight.x(); x++) {
                    newFloor.add(new Point(x, bottomRight.y()));
                }
            }
            case LEFT -> {
                for (int y = topLeft.y() - 1; y > bottomRight.y(); y--) {
                    newFloor.add(new Point(topLeft.x(), y));
                }
            }
            case RIGHT -> {
                for (int y = topLeft.y() - 1; y > bottomRight.y(); y--) {
                    newFloor.add(new Point(bottomRight.x(), y));
                }
            }
        }
        return newFloor;
    }

    /**
     * Helper method for 'hasSpaceToGrow' and 'expandWall'.  Given we want to expand a wall in a given direction, this
     * method determines the points that we need to convert into a wall.
     */
    private ArrayList<Point> newWallPoints(Side expansionSide) {
        ArrayList<Point> newWall = new ArrayList<>();
        switch (expansionSide) {
            case TOP -> {
                for (int x = topLeft.x(); x <= bottomRight.x(); x++) {
                    newWall.add(new Point(x, topLeft.y() + 1));
                }
            }
            case BOTTOM -> {
                for (int x = topLeft.x(); x <= bottomRight.x(); x++) {
                    newWall.add(new Point(x, bottomRight.y() - 1));
                }
            }
            case LEFT -> {
                for (int y = topLeft.y(); y >= bottomRight.y(); y--) {
                    newWall.add(new Point(topLeft.x() - 1, y));
                }
            }
            case RIGHT -> {
                for (int y = topLeft.y(); y >= bottomRight.y(); y--) {
                    newWall.add(new Point(bottomRight.x() + 1, y));
                }
            }
        }
        return newWall;
    }

    /**
     * This is a helper method for 'growToRandomSize'.  When a room is initially created, it is 3x3 and has a single
     * door.  After we've expanded the room to its final dimensions, we need to add doors to the other three sides.
     */
    private void setRestOfDoors() {
        if (initialDoorSide != Side.LEFT) {
            List<Point> leftWallPoints = verticalPointsBetween(topLeft, new Point(topLeft.x(), bottomRight.y()));
            java.util.Collections.shuffle(leftWallPoints, rand);
            Point leftWallPoint = leftWallPoints.remove(0);
            Door leftDoor = new Door(leftWallPoint, false, Side.LEFT, board);
            doors.add(leftDoor);
        }

        if (initialDoorSide != Side.RIGHT) {
            List<Point> rightWallPoints = verticalPointsBetween(new Point(bottomRight.x(), topLeft.y()), bottomRight);
            java.util.Collections.shuffle(rightWallPoints, rand);
            Point rightWallPoint = rightWallPoints.remove(0);
            Door rightDoor = new Door(rightWallPoint, false, Side.RIGHT, board);
            doors.add(rightDoor);
        }

        if (initialDoorSide != Side.TOP) {
            List<Point> topWallPoints = horizontalPointsBetween(topLeft, new Point(bottomRight.x(), topLeft.y()));
            java.util.Collections.shuffle(topWallPoints, rand);
            Point topWallPoint = topWallPoints.remove(0);
            Door topDoor = new Door(topWallPoint, false, Side.TOP, board);
            doors.add(topDoor);
        }

        if (initialDoorSide != Side.BOTTOM) {
            List<Point> bottomWallPoints = horizontalPointsBetween(new Point(topLeft.x(), bottomRight.y()), bottomRight);
            java.util.Collections.shuffle(bottomWallPoints, rand);
            Point bottomWallPoint = bottomWallPoints.remove(0);
            Door bottomDoor = new Door(bottomWallPoint, false, Side.BOTTOM, board);
            doors.add(bottomDoor);
        }
    }

    /**
     * Given two points, this method returns all the points between those two points, inclusive of the endpoints.  Note
     * that Point 'a' and Point 'b' need to share the same 'x' value.
     */
    private List<Point> verticalPointsBetween(Point a, Point b) {
        ArrayList<Point> points = new ArrayList<>();
        Point topPoint;
        Point bottomPoint;
        if (a.y() >= b.y()) {
            topPoint = a;
            bottomPoint = b;
        } else {
            topPoint = b;
            bottomPoint = a;
        }
        for (int y = topPoint.y() - 1; y > bottomPoint.y(); y--) {
            points.add(new Point(a.x(), y));
        }
        return points;
    }

    /**
     * Given two points, this method returns all the points between those two points, inclusive of the endpoints.  Note
     * that Point 'a' and Point 'b' need to share the same 'y' value.
     */
    private List<Point> horizontalPointsBetween(Point a, Point b) {
        // TODO - there is probably a way to clean up these two 'points between' methods.
        ArrayList<Point> points = new ArrayList<>();
        Point leftPoint;
        Point rightPoint;
        if (a.x() <= b.x()) {
            leftPoint = a;
            rightPoint = b;
        } else {
            leftPoint = b;
            rightPoint = a;
        }
        for (int x = leftPoint.x() + 1; x < rightPoint.x(); x++) {
            points.add(new Point(x, a.y()));
        }
        return points;
    }

    /**
     * Helper method for 'hasSpaceToGrow'.  Given a list of points, this method determines if they are populated with
     * 'Nothing' tiles or not.  If a single tile in the list is not a 'nothing' tile, this method returns false.
     */
    private boolean areCellsUnoccupied(List<Point> cells) {
        for (Point cell : cells) {
            if (board.getCell(cell) != Tileset.NOTHING) {
                return false;
            }
        }
        return true;
    }
}
