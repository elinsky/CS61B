package byow.Core.Board;

import byow.Core.*;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

/**
 * To start a game, you need a board.  Given a random number a board generator will generate a random board for you.
 * This board will have a random number of rooms that are randomly sized.  Each room is connected to at least one other
 * room via hallways.
 */
public class BoardGenerator {
    private final Board board;
    private final ArrayList<Door> unusedDoors;
    Random rand;

    /**
     * Creates a randomly generated board.
     * @param width width of the desired board.
     * @param height height of the desired board.
     * @param rand random integer used to seed a Random object.
     */
    public BoardGenerator(int width, int height, Random rand) {
        this.rand = rand;
        this.board = new Board(height, width, Tileset.NOTHING);
        this.unusedDoors = new ArrayList<>();

        // Bootstrap the initial world generation process
        // Create first room
        int randX = RandomUtils.uniform(rand, 4, width - 4);
        int randY = RandomUtils.uniform(rand, 4, height - 4);
        Point randStart = new Point(randX, randY);
        Point secondDoorSite = new Point(randStart.x(), randStart.y() + 2);
        RoomBuildPlans initialPlacementInstructions = new RoomBuildPlans(randStart, Side.TOP, secondDoorSite);
        Room initialRoom = new Room(initialPlacementInstructions, rand, board);
        unusedDoors.addAll(initialRoom.getDoors());

        // Create second room
        Door initialDoor = initialRoom.getDoors().remove(0);
        RoomBuildPlans secondRoomPlan = initialDoor.getBuildPlansForNeighbor();
        Room secondRoom = new Room(secondRoomPlan, rand, this.board);
        unusedDoors.addAll(secondRoom.getDoors());

        // Let the rest of the board emerge
        while (unusedDoors.size() > 0) {
            Door doorToNowhere = this.getRandomDoorToBuildOff(rand);
            RoomBuildPlans adjoiningRoomPlans = doorToNowhere.getBuildPlansForNeighbor();
            Room adjoiningRoom = new Room(adjoiningRoomPlans, rand, this.board);
            unusedDoors.addAll(adjoiningRoom.getDoors());
            unusedDoors.removeIf(x -> !usablePlacementInstructions(x.getBuildPlansForNeighbor()));
        }
    }

    /**
     * Returns the board object.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * This method will return a random door on the board that does not have a neighbor.  It will also only return a
     * door that has enough space nearby for a new room or hallway.
     *
     * @return Door on the map that doesn't have a neighbor.
     */
    private Door getRandomDoorToBuildOff(Random rand) {
        if (unusedDoors.size() == 0) {
            throw new RuntimeException("Ran out of usable doors");
        }
        java.util.Collections.shuffle(unusedDoors, rand);
        Door unusedDoor = unusedDoors.remove(0);
        if (usablePlacementInstructions(unusedDoor.getBuildPlansForNeighbor())) {
            return unusedDoor;
        } else {
            return getRandomDoorToBuildOff(rand);
        }
    }

    private boolean usablePlacementInstructions(RoomBuildPlans plans) {
        // This just checks to see of the board has space to build the new room.  If there isn't space, it likely means
        // that another room is in the way, or that the room wouldn't fit on the board.
        Point center = plans.location();
        for (int x = center.x() - 1; x < center.x() + 2; x++) {
            for (int y = center.y() + 1; y > center.y() - 2; y--) {
                Point cell = new Point(x, y);
                if (!board.isValidPoint(cell) || board.getCell(cell) != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }
}