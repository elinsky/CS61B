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

import static edu.princeton.cs.algs4.StdDraw.mouseX;
import static edu.princeton.cs.algs4.StdDraw.mouseY;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 60;
    private boolean isGameActive = true;
    private String gameState = "menu";
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
        endGame(gameState);
    }

    private void processMouse(int x, int y) {
        if (gameState.equals("play")) {
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

    // return value indicates if the game continues
    private void processKey(char key) {
        switch (gameState) {
            case "menu":
                switch (key) {
                    case 'N' -> {
                        gameState = "select_seed";
                        displaySeedMenu();
                    }
                    case 'L' -> {
                        loadGame();
                        gameState = "play";
                    }
                    case 'Q' -> {
                        isGameActive = false;
                        gameState = "Goodbye";
                    }
                }
                break;
            case "select_seed":
                if (key == 'S') {
                    gameState = "play";
                    initializeGame();
                } else {
                    seed = seed * 10 + Character.getNumericValue(key);
                    displaySeedMenu();
                }
                break;
            case "play":
                if (key == ':') {
                    gameState = "command mode";
                } else {
                    playRound(key);
                }
                break;
            case "command mode":
                if (key == 'Q') {
                    saveGame();
                    isGameActive = false;
                    gameState = "Save successful";
                } else {
                    gameState = "play";
                }
            case "You Win":
            case "You Lose":
                isGameActive = false;
                break;
        }
    }

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

    private void endGame(String message) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2.0, HEIGHT / 2.0, message);
        StdDraw.show();
        StdDraw.pause(1);
    }

    private void addObjectsToBoard(Board board, int numEnemies, int numCoins, Random rand) {
        ArrayList<TETile> floor = new ArrayList<>();
        floor.add(Tileset.FLOOR);

        // Add Enemies to Board
        for (int i = 0; i < numEnemies; i++) {
            enemies.add(new Enemy(board, ObjectUtils.randomCell(board, rand, floor)));
        }

        // Add Coins to Board
        for (int i = 0; i < numCoins; i++) {
            coins.add(new Coin(board, ObjectUtils.randomCell(board, rand, floor)));
        }
    }

    private void initializeGame() {
        rand = new Random(seed);

        // Initialize the board
        BoardGenerator boardGenerator = new BoardGenerator(WIDTH, HEIGHT - 2, rand);
        board = boardGenerator.getBoard();
        ter.initialize(WIDTH, HEIGHT);

        // Add player, enemies, and coins to board
        ArrayList<TETile> floor = new ArrayList<>();
        floor.add(Tileset.FLOOR);
        player = new Player(board, ObjectUtils.randomCell(board, rand, floor));
        addObjectsToBoard(board, 3, 3, rand);

        ter.renderFrame(board.getBoard(), "");
    }

    private void playRound(char key) {
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
                gameState = "You Lose";
            }

        // Check for player win
        if (coins.size() == 0) {
            gameState = "You Win";
        }

        // Enemies take turns
        for (Enemy enemy : enemies) {
            enemy.takeTurn();
            if (enemy.location().equals(player.location())) {
                gameState = "You Lose";
            }
        }
        ter.renderFrame(board.getBoard(), "");
    }
}