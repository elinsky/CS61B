package byow.Core;

import byow.Core.Board.Board;
import byow.Core.Board.BoardGenerator;
import byow.Core.Objects.*;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;
    private boolean game_active = true;
    private String game_state = "menu";
    private int score = 0;
    private ArrayList<Sprite> sprites = new ArrayList<>();
    private Random rand;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        String selection = solicit_play_selection();
        switch (selection) {
            case "n":
                int seed = solicit_seed();
                this.rand = new Random(seed);
                play_game();
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
            // TODO - handle all different length numbers
            // TODO - handle case where user inputs characters other than numbers.
            // TODO - display numbers as you type them.
            String key = KeyListener.get_keypress(1);
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
            selection = KeyListener.get_keypress(1);
        }

        return selection;
    }

    private void lose_game() {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, "You Lose");
        StdDraw.show();
    }

    private void play_game() {
        // TODO
        // TODO - Implement Heads up display with hover over
        // TODO - implement coin counter in heads up display
        // TODO - implement AI for ghosts
        // TODO - implement ability to collect coins
        // TODO - implement ability to die
        // TODO - add ability to win by collecting all the coins
        // TODO - add multiple levels
        // TODO - refactor so that interactWithKeyboard handles ALL inputs, even from menu

        BoardGenerator boardGenerator = new BoardGenerator(WIDTH, HEIGHT - 2, rand);
        Board board = boardGenerator.get_board();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(board.get_board());

        sprites.add(new Player(board, Tileset.AVATAR, ObjectUtils.random_walkable_cell(board, rand)));
        sprites.add(new Enemy(board, Tileset.ORANGE_GHOST, ObjectUtils.random_walkable_cell(board, rand)));
        sprites.add(new Enemy(board, Tileset.BLUE_GHOST, ObjectUtils.random_walkable_cell(board, rand)));
        sprites.add(new Enemy(board, Tileset.PINK_GHOST, ObjectUtils.random_walkable_cell(board, rand)));
        Coin coin1 = new Coin(board, ObjectUtils.random_walkable_cell(board, rand));
        Coin coin2 = new Coin(board, ObjectUtils.random_walkable_cell(board, rand));
        Coin coin3 = new Coin(board, ObjectUtils.random_walkable_cell(board, rand));
        ter.renderFrame(board.get_board());

        // Main game loop
        while (game_active) {
            for (Sprite sprite : sprites) {
                sprite.take_turn();
                // TODO - check for kills
                // TODO - check for collecting coins
            }
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
