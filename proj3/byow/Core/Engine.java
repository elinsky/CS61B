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
import java.io.*;
import java.util.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;
    private boolean game_active = true;
    private String game_state = "menu";
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private Board board;
    private int seed;
    Random rand;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        display_menu();
        KeyboardInputSource input_source = new KeyboardInputSource();
        while (input_source.possibleNextInput() & game_active) {
            char key = input_source.getNextKey();
            process_key(key);
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
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        StringInputDevice input_device = new StringInputDevice(input);
        while (input_device.possibleNextInput()) {
            char key = input_device.getNextKey();
            process_key(key);
        }
        return board.get_board();
    }

    // return value indicates if the game continues
    private void process_key(char key) {
        switch (game_state) {
            case "menu":
                switch (key) {
                    case 'N' -> {
                        game_state = "select_seed";
                        display_seed_menu();
                    }
                    case 'L' -> {
                        load_game();
                        game_state = "play";
                    }
                    case 'Q' -> {
                        game_active = false;
                        game_state = "Goodbye";
                    }
                }
                break;
            case "select_seed":
                if (key == 'S') {
                    game_state = "play";
                    initialize_game();
                } else {
                    seed = seed * 10 + Character.getNumericValue(key);
                    display_seed_menu();
                }
                break;
            case "play":
                if (key == ':') {
                    game_state = "command mode";
                } else {
                    play_round(key);
                }
                break;
            case "command mode":
                if (key == 'Q') {
                    save_game();
                    game_active = false;
                    game_state = "Save successful";
                } else {
                    game_state = "play";
                }
            case "You Win":
            case "You Lose":
                game_active = false;
                break;
        }
    }

    private void load_game() {
        File f = new File("./save_data");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                enemies = (ArrayList<Enemy>) os.readObject();
                coins = (ArrayList<Coin>) os.readObject();
                player = (Player) os.readObject();
                board = (Board) os.readObject();
                seed = (int) os.readObject();
                rand = (Random) os.readObject();
                ter = (TERenderer) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
    }

    private void save_game() {
        File f = new File("./save_data");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(enemies);
            os.writeObject(coins);
            os.writeObject(player);
            os.writeObject(board);
            os.writeObject(seed);
            os.writeObject(rand);
            os.writeObject(ter);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
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
        StdDraw.text(WIDTH / 2.0, 40, "Enter a number: ");
        StdDraw.text(WIDTH / 2.0, 37, Integer.toString(seed));
        StdDraw.text(WIDTH / 2.0, 34, "Then press 'S' to start");

        StdDraw.show();
        StdDraw.pause(1);
    }

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
        // Player takes turn
        player.move(key);

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

        // Check for player win
        if (coins.size() == 0) {
            game_state = "You Win";
        }

        // Enemies take turns
        for (Enemy enemy : enemies) {
            enemy.take_turn();
            if (enemy.location().equals(player.location())) {
                game_state = "You Lose";
            }
        }
        ter.renderFrame(board.get_board());
    }
}