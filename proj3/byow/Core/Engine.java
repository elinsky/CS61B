package byow.Core;

import byow.Core.Board.Board;
import byow.Core.Board.BoardGenerator;
import byow.Core.BoardPiece.*;
import byow.Core.Input.KeyboardInputSource;
import byow.Core.Input.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.*;

import static byow.Core.GameState.*;
import static edu.princeton.cs.algs4.StdDraw.mouseX;
import static edu.princeton.cs.algs4.StdDraw.mouseY;

/**
 * An engine manages a game on a board that is full of coins, players, and enemies.  The engine decides who's turn it is.
 * An engine is in charge of updating the board when a sprite moves.  An engine also listens to input from a mouse and
 * keyboard, and dispatches the input to the appropriate handling methods.  An engine also determines the rules of the
 * game, and enforces those rules.  An engine is also responsible for refreshing the screen at appropriate intervals.
 * Finally, an engine is in charge of displaying the game's menu items.
 */
public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 60;
    private boolean isGameActive = true;
    private GameState gameState = MENU;
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
        displayMenu();
        KeyboardInputSource inputSource = new KeyboardInputSource();

        while (isGameActive) {
            if (inputSource.possibleNextInput()) {
                char key = inputSource.getNextKey();
                processKey(key);
            }
            processMouse((int) mouseX(), (int) mouseY());
        }
        if (gameState == WIN) {
            endGame("You Win");
        } else {
            endGame("You Lose");
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
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        StringInputDevice inputDevice = new StringInputDevice(input);
        while (inputDevice.possibleNextInput()) {
            char key = inputDevice.getNextKey();
            processKey(key);
        }
        return board.getBoard();
    }

    /**
     * Helper method for 'interactWithKeyboard'.  Given x y coordinates, if the game state is 'play', this method will
     * check to see if it is a valid point on the board.  If it is a valid point, then it will display the description
     * of the cell at (x, y) on the heads up display at the top left of the game window.
     */
    private void processMouse(int x, int y) {
        if (gameState == PLAY) {
            Point mousePoint = new Point(x, y);
            if (!board.isValidPoint(mousePoint)) {
                return;
            }
            TETile hover = board.getCell(mousePoint);
            String cellDescription = hover.description();
            ter.renderFrame(board.getBoard(), cellDescription);
        }
    }

    /**
     * This is a dispatch method that takes a keypress as input, and depending on the state of the game, will process
     * that keypress accordingly.
     */
    private void processKey(char key) {
        switch (gameState) {
            case MENU:
                switch (key) {
                    case 'N' -> {
                        gameState = SELECT_SEED;
                        displaySeedMenu();
                    }
                    case 'L' -> {
                        loadGame();
                        gameState = PLAY;
                    }
                    case 'Q' -> {
                        isGameActive = false;
                        gameState = GOODBYE;
                    }
                }
                break;
            case SELECT_SEED:
                if (key == 'S') {
                    gameState = PLAY;
                    initializeGame();
                } else {
                    seed = seed * 10 + Character.getNumericValue(key);
                    displaySeedMenu();
                }
                break;
            case PLAY:
                if (key == ':') {
                    gameState = COMMAND_MODE;
                } else {
                    playRound(key);
                }
                break;
            case COMMAND_MODE:
                if (key == 'Q') {
                    saveGame();
                    isGameActive = false;
                    gameState = SUCCESSFUL_SAVE;
                } else {
                    gameState = PLAY;
                }
            case WIN:
            case LOSE:
                isGameActive = false;
                break;
        }
    }

    /**
     * This method checks for a ./save_data file.  If present, it will load the saved game for you.
     */
    private void loadGame() {
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

    /**
     * Saves the game state to the ./save_data file.
     */
    private void saveGame() {
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

    /**
     * Displays the main menu.  This is the first screen that players see when starting up the game.
     */
    private void displayMenu() {
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

    /**
     * If the player decides to start a new game, this menu prompts the user for a random seed to be used to generate
     * the game board.
     */
    private void displaySeedMenu() {
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

    /**
     * Displays a screen that informs the user that the game is over.  The 'message' is the message that is displayed
     * on the screen.
     */
    private void endGame(String message) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, message);
        StdDraw.show();
        StdDraw.pause(1);
    }

    /**
     * Adds enemies and coins to a newly created board. It will add these at random locations.
     * @param board board to add these objects to.
     * @param numEnemies number of enemies to place on the board.
     * @param numCoins number of coins to place on the board.
     * @param rand Random object used to choose random locations for these objects.
     */
    private void addObjectsToBoard(Board board, int numEnemies, int numCoins, Random rand) {
        Set<TETile> validCells = new HashSet<>();
        validCells.add(Tileset.FLOOR);

        // Add Enemies to Board
        for (int i = 0; i < numEnemies; i++) {
            enemies.add(new Enemy(board, BoardPieceUtils.randomCell(board, rand, validCells)));
        }

        // Add Coins to Board
        for (int i = 0; i < numCoins; i++) {
            coins.add(new Coin(board, BoardPieceUtils.randomCell(board, rand, validCells)));
        }
    }

    /**
     * Once a random seed has been set, 'initializeGame' generates the random board, adds the player, enemies, and coins
     * to it, and then displays the game board to the player.
     */
    private void initializeGame() {
        rand = new Random(seed);

        // Initialize the board
        BoardGenerator boardGenerator = new BoardGenerator();
        board = boardGenerator.getBoard(WIDTH, HEIGHT - 2, rand);
        ter.initialize(WIDTH, HEIGHT);

        // Add player, enemies, and coins to board
        Set<TETile> validCalls = new HashSet<>();
        validCalls.add(Tileset.FLOOR);
        player = new Player(board, BoardPieceUtils.randomCell(board, rand, validCalls));
        addObjectsToBoard(board, 3, 3, rand);

        ter.renderFrame(board.getBoard(), "");
    }

    /**
     * This method ensures that all sprites (players and enemies) take a turn playing.  The method checks to see if
     * coins have been collected.  It checks to see if a player died.  And it checks for win and loss scenarios. After
     * the turn is over, the method will render the board.
     */
    private void playRound(char key) {
        // Player takes turn
        player.move(key);

        // Check to see if player collected a coin
        HashSet<Coin> collected = new HashSet<>();
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
                gameState = LOSE;
            }

        // Check for player win
        if (coins.size() == 0) {
            gameState = WIN;
        }

        // Enemies take turns
        for (Enemy enemy : enemies) {
            enemy.takeTurn();
            if (enemy.location().equals(player.location())) {
                gameState = LOSE;
            }
        }
        ter.renderFrame(board.getBoard(), "");
    }
}