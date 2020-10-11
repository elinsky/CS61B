package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        String selection = solicit_play_selection();
        switch (selection) {
            case "n":
                int seed = solicit_seed();
                play_game(seed);
                break;
            case "l":
                // TODO
                break;
            case "q":
                // TODO
                break;
        }
    }

    private int solicit_seed() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // Draw Title
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.5, "Enter a four digit number");

        StdDraw.show();
        StringBuilder seed = new StringBuilder();
        while (seed.length() < 4) {
            // TODO - handle case where user inputs characters other than numbers.
            // TODO - display numbers as you type them.
            String key = EventHandler.get_keypress(1);
            seed.append(key);
        }

        return Integer.parseInt(seed.toString());
    }

    private String solicit_play_selection() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // Draw Title
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.5, "CS61B: THE GAME");

        // Draw Options
        Font subtitle = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(subtitle);
        StdDraw.text(WIDTH / 2.0, HEIGHT * 0.40, "New Game (N)");
        StdDraw.text(WIDTH / 2.0, HEIGHT * 0.35, "Load Game (L)");
        StdDraw.text(WIDTH / 2.0, HEIGHT * 0.30, "Quit (Q)");

        StdDraw.show();

        // Get selection
        String selection = "";
        Set<String> valid_selections = new HashSet<String>(Arrays.asList("n", "l", "q"));
        while (!valid_selections.contains(selection)) {
            selection = EventHandler.get_keypress(1);
        }

        return selection;
    }

    private void play_game(int seed) {
        // TODO
        // TODO - Implement Heads up display with hover over
        // TODO - implement coin counter in heads up display
        // TODO - implement AI for ghosts
        // TODO - implement ability to collect coins
        // TODO - implement ability to die
        // TODO - add ability to win by collecting all the coins
        // TODO - add multiple levels
        // TODO - refactor so that interactWithKeyboard handles ALL inputs, even from menu
        BoardGenerator boardGenerator = new BoardGenerator(WIDTH, HEIGHT - 2, seed);
        Board board = boardGenerator.get_board();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(board.get_board());

        Character player = new Character(board, Tileset.AVATAR);
        Character coin1 = new Character(board, Tileset.COIN);
        Character coin2 = new Character(board, Tileset.COIN);
        Character coin3 = new Character(board, Tileset.COIN);
        Character ghost1 = new Character(board, Tileset.ORANGE_GHOST);
        Character ghost2 = new Character(board, Tileset.BLUE_GHOST);
        Character ghost3 = new Character(board, Tileset.PINK_GHOST);
        Random rand = new Random(seed);
        ter.renderFrame(board.get_board());

        while (true) {
            String key = EventHandler.get_keypress(1);
            switch (key) {
                case "w" -> player.move(Side.TOP);
                case "a" -> player.move(Side.LEFT);
                case "s" -> player.move(Side.BOTTOM);
                case "d" -> player.move(Side.RIGHT);
            }
            ghost1.move(SideUtilities.random_side_except(rand, null));
            ghost2.move(SideUtilities.random_side_except(rand, null));
            ghost3.move(SideUtilities.random_side_except(rand, null));
            ter.renderFrame(board.get_board());
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
