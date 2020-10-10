package byow.lab13;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int selection = rand.nextInt(CHARACTERS.length);
            result.append(CHARACTERS[selection]);
        }
        return result.toString();
    }

    public void drawFrame(String s, int round) {
        StdDraw.clear(Color.BLACK);
        // Draw the banner at the top
        StdDraw.line(0, height - 3, width, height - 3);
        StdDraw.text(1, height - 2, "Round: " + round);

        StdDraw.text(width / 2.0, height / 2.0, s);
        StdDraw.show();
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters, int round) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1), round);
            wait_millis(1000);
            drawFrame("", round);
            wait_millis(500);
        }
        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    private void wait_millis(int millis) {
        try
        {
            Thread.sleep(millis);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    }

    public String solicitNCharsInput(int n) {
        StringBuilder result = new StringBuilder();
        drawFrame(result.toString(), n);
        while (result.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                result.append(StdDraw.nextKeyTyped());
                drawFrame(result.toString(), n);
            }
        }
        wait_millis(500);
        return result.toString();
    }

    public void startGame() {
        MemoryGame game = new MemoryGame(50, 50, 21);
        int round = 1;
        while (true) {
            game.drawFrame("Round: " + round, round);
            wait_millis(1000);
            String answer = game.generateRandomString(round);
            game.flashSequence(answer, round);
            String guess = game.solicitNCharsInput(round);
            if (!guess.equals(answer)) {
                game.drawFrame("Game Over!", round);
                wait_millis(1000);
                break;
            }
            round++;

        }
        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Engine loop
    }

}
