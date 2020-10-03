package byow.Core;

/**
 * This class generates a world, and displays it, allowing you to visually test the map.
 */
public class WorldVisualTest {

    public static void main(String[] args) {
        World world = new World(50, 50, 21);
        world.render();
    }
}
