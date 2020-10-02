package byow.Core;

import byow.TileEngine.Tileset;

import java.util.Random;


/**
 * This class generates a game map.  You can either generate a map with a random seed, or let the class choose a seed
 * for you.  Maps are defined as a 2-dimensional array of TETiles.
 */
public class WorldGenerator {
    Random rand;
    int SEED = 34;

    public WorldGenerator() {
        this.rand = new Random(SEED);
    }

    public void generate_random_world() {
        World world = new World(50, 50);

        // Bootstrap the initial board generation process
        // Create first room
        Point new_build_site = world.random_interior_cell(rand);
        Side door_side = Side.TOP;
        Point second_door_site = new Point(new_build_site.x(), new_build_site.y() + 2);
        PlacementInstructions initial_placement_instructions = new PlacementInstructions(new_build_site, door_side, second_door_site);
        Room initial_room = new Room(world, initial_placement_instructions, rand, world.get_board());
        world.render();
        initial_room.grow_to_random_size(rand);

        // Create second room
        Door initial_door = initial_room.get_doors().remove(0);
        PlacementInstructions second_room_plan = initial_door.get_placement_instructions_for_neighbor();
        Room second_room = new Room(world, second_room_plan, rand, world.get_board());
        second_room.grow_to_random_size(rand);

        world.unused_doors_left();


        // Let the rest of the board emerge
        while (!world.too_congested() && world.unused_doors_left()) {
            Door door_to_nowhere = world.get_random_door_to_build_off(rand);
            PlacementInstructions placement_instructions = door_to_nowhere.get_placement_instructions_for_neighbor();
            EnclosedSpace new_space = new Room(world, placement_instructions, rand, world.get_board());
            new_space.grow_to_random_size(rand);
        }
        world.render();
    }




}
