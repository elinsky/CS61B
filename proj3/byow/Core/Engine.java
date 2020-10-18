package byow.Core;

import byow.Core.Board.Board;
import byow.Core.Board.BoardGenerator;
import byow.Core.Objects.*;
import byow.Core.Input.KeyboardInputSource;
import byow.Core.Input.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 60;
    private boolean game_active = true;
    private String game_state = "menu";
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Coin> coins = new ArrayList<>();
    private Board board;
    private int seed;
    private Random rand;
    private Player player;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        display_menu();
        KeyboardInputSource input_source = new KeyboardInputSource();
        while (input_source.possibleNextInput() & game_active) {
            char key = input_source.getNextKey();
            game_active = process_key(key);
        }

//        String selection = solicit_play_selection();
//        switch (selection) {
//            case "n":
//                display_seed_menu();
//                int seed = solicit_seed();
//                play_game(new Random(seed));
//                break;
//            case "l":
//                // TODO
//                break;
//            case "q":
//                // TODO
//                break;
//        }
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
        StringInputDevice input_device = new StringInputDevice(input);
        while (input_device.possibleNextInput() & game_active) {
            char key = input_device.getNextKey();
            game_active = process_key(key);
        }
        return board.get_board();
    }

    // return value indicates if the game continues
    private boolean process_key(char key) {
        boolean keep_playing = true;
        switch (game_state) {
            case "menu":
                switch (key) {
                    case 'N':
                        game_state = "select_seed";
                        display_seed_menu();
                        break;
                    case 'L':
                        // TODO
                        break;
                    case 'Q':
                        keep_playing = false;
                        break;
                }
                break;
            case "select_seed":
                if (key == 'S') {
                    game_state = "play";
                    initialize_game();
                } else {
                    seed = seed * 10 + Character.getNumericValue(key);
                }
                break;
            case "play":
                play_round(key);
                break;
            case "You Win":
                end_game(game_state);
                break;
            case "You Lose":
                end_game(game_state);
                break;
        }
        // TODO
        return keep_playing;
    }

    private void display_menu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16); // Each cell is 16x16 pixels
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
        StdDraw.pause(1);
    }

    private void display_seed_menu() {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.clear(Color.BLACK);

        // Draw Title
        StdDraw.text(WIDTH / 2.0, HEIGHT / 1.5, "Enter a four digit number");

        StdDraw.show();
        StdDraw.pause(1);
    }

    private int solicit_seed() {
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

//    private String solicit_play_selection() {
//        String selection = "";
//        Set<String> valid_selections = new HashSet<String>(Arrays.asList("n", "l", "q"));
//        while (!valid_selections.contains(selection)) {
//            selection = KeyListener.get_keypress(1);
//        }
//
//        return selection;
//    }

    private void end_game(String message) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, message);
        StdDraw.show();
        StdDraw.pause(1);
    }

    private void add_objects_to_board(Board board, int num_enemies, int num_coins, Random rand) {
        ArrayList<TETile> floor = new ArrayList<>();
        floor.add(Tileset.FLOOR);

        // Add Enemies to Board
        for (int i = 0; i < num_enemies; i++) {
            enemies.add(new Enemy(board, ObjectUtils.random_cell(board, rand, floor)));
        }

        // Add Coins to Board
        for (int i = 0; i < num_coins; i++) {
            coins.add(new Coin(board, ObjectUtils.random_cell(board, rand, floor)));
        }
    }

    private void initialize_game() {
        rand = new Random(seed);

        // Initialize the board
        BoardGenerator boardGenerator = new BoardGenerator(WIDTH, HEIGHT - 2, rand);
        board = boardGenerator.get_board();
        ter.initialize(WIDTH, HEIGHT);

        // Add player, enemies, and coins to board
        ArrayList<TETile> floor = new ArrayList<>();
        floor.add(Tileset.FLOOR);
        player = new Player(board, ObjectUtils.random_cell(board, rand, floor));
        add_objects_to_board(board, 3, 3, rand);

        ter.renderFrame(board.get_board());
    }

    private void play_round(char key) {
        // TODO
        // TODO - Implement Heads up display with hover over
        // TODO - implement coin counter in heads up display
        // TODO - implement AI for ghosts
        // TODO - add multiple levels
        // TODO - refactor so that interactWithKeyboard handles ALL inputs, even from menu

        // Generate and draw the board
//        BoardGenerator boardGenerator = new BoardGenerator(WIDTH, HEIGHT - 2, rand);
//        board = boardGenerator.get_board();
//        ter.initialize(WIDTH, HEIGHT);
//        ter.renderFrame(board.get_board());

        // Add player, enemies, and coins to board
//        ArrayList<TETile> floor = new ArrayList<>();
//        floor.add(Tileset.FLOOR);
//        Player player = new Player(board, ObjectUtils.random_cell(board, rand, floor));
//        add_objects_to_board(board, 3, 3, rand);
//        ter.renderFrame(board.get_board());

        // Main game loop.  Each loop every sprite gets a turn.
        // Player takes turn
        player.move(key);
        ter.renderFrame(board.get_board());

        // Check to see if player collected a coin
        ArrayList<Coin> collected = new ArrayList<>();
        for (Coin coin : coins) {
            if (player.location().equals(coin.location())) {
                coin.collect();
                collected.add(coin);
            }
        }
        coins.removeAll(collected);

        // Check to see if player died
        for (Enemy enemy: enemies)
            if (player.location().equals(enemy.location())) {
                game_state = "You Lose";
            }
        ter.renderFrame(board.get_board());

        // Check for player win
        if (coins.size() == 0) {
            game_state = "You Win";
        }
        ter.renderFrame(board.get_board());

        // Enemies take turns
        for (Enemy enemy : enemies) {
            enemy.take_turn();
            ter.renderFrame(board.get_board());
            if (enemy.location().equals(player.location())) {
                game_state = "You Lose";
            }
        }
        ter.renderFrame(board.get_board());
    }
}