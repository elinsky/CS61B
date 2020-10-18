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
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private boolean game_active = true;
    private String game_state = "menu";
    private int score = 0;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Random rand;
    private Board board;
    private ArrayList<Coin> coins = new ArrayList<>();

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

    // Show screen that asks for seed
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

    // Main menu code
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

    public void process_move(Sprite mover, Point destination) {
        // TODO - handle interactions between objects here.  e.g. collect coins or ghost killing you

        // First check to see if destination is traversable
        if (ObjectUtils.is_tile_traversable(destination, board, mover.getTraversable_tiles())) {
            board.set_cell(destination, mover.getShape());
        }

    }

    private void end_game(String message) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, message);
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
        this.board = boardGenerator.get_board();
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(board.get_board());

        ArrayList<TETile> floor = new ArrayList<>();
        floor.add(Tileset.FLOOR);

        // Add Player and Enemies to Board
        Player player = new Player(board, this, ObjectUtils.random_cell(board, rand, floor));
//        enemies.add(new Enemy(board, this, ObjectUtils.random_cell(board, rand, floor)));
//        enemies.add(new Enemy(board, this, ObjectUtils.random_cell(board, rand, floor)));
        enemies.add(new Enemy(board, this, ObjectUtils.random_cell(board, rand, floor)));

        // Add Coins to Board
//        coins.add(new Coin(board, ObjectUtils.random_cell(board, rand, floor)));
        coins.add(new Coin(board, ObjectUtils.random_cell(board, rand, floor)));
//        coins.add(new Coin(board, ObjectUtils.random_cell(board, rand, floor)));

        ter.renderFrame(board.get_board());

        // Main game loop.  Each loop every sprite gets a turn.
        while (game_active) {
            // Player takes turn
            player.take_turn();
            ter.renderFrame(board.get_board()); // TODO DELETE ME
            ter.renderFrame(board.get_board()); // TODO DELETE ME
            // Check to see if player collected a coin
            ArrayList<Coin> collected = new ArrayList<>();
            for (Coin coin : coins) {
                if (player.location().equals(coin.location())) {
                    coin.collect();
                    collected.add(coin);
                }
            }
            coins.removeAll(collected);

            // Check for player win
            if (coins.size() == 0) {
                game_state = "You Win";
                game_active = false;
            }

            ter.renderFrame(board.get_board()); // TODO DELETE ME
            ter.renderFrame(board.get_board()); // TODO DELETE ME

            // Check to see if player died
            for (Enemy enemy: enemies)
                if (player.location().equals(enemy.location())) {
                    game_state = "You Lose";
                    game_active = false;
                }
            ter.renderFrame(board.get_board());
            ter.renderFrame(board.get_board()); // TODO DELETE ME

            // Enemies take turns
            for (Enemy enemy : enemies) {
                enemy.take_turn();
                ter.renderFrame(board.get_board());
                ter.renderFrame(board.get_board()); // TODO DELETE ME
                if (enemy.location().equals(player.location())) {
                    game_state = "You Lose";
                    game_active = false;
                }
            }
        }
        end_game(game_state);
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
