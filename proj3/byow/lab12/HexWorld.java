package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static void addHexagon(TETile type, int size, int x_left_corner, int y_left_corner, TETile[][] world) {
        int width = 3 * size - 2;
        drawHorizontalRow(type, x_left_corner, y_left_corner, width, world);
        drawHorizontalRow(type, x_left_corner , y_left_corner - 1, width, world);
        drawTopHalf(type, size, x_left_corner, y_left_corner, world);
        drawBottomHalf(type, size, x_left_corner, y_left_corner, world);
    }

    private static void drawTopHalf(TETile type, int size, int x_left_corner, int y_left_corner, TETile[][] world) {
        int row_width = 3 * size - 4;
        int num_top_rows = size - 1;
        int row_num = 0;
        while (row_num < num_top_rows) {
            drawHorizontalRow(type, x_left_corner + 1, y_left_corner + 1, row_width, world);
            row_width = row_width - 2;
            x_left_corner++;
            y_left_corner++;
            row_num++;
        }
    }

    private static void drawBottomHalf(TETile type, int size, int x_left_corner, int y_left_corner, TETile[][] world) {
        int row_width = 3 * size - 4;
        int num_top_rows = size - 1;
        int row_num = 0;
        while (row_num < num_top_rows) {
            drawHorizontalRow(type, x_left_corner + 1, y_left_corner - 2, row_width, world);
            row_width = row_width - 2;
            x_left_corner++;
            y_left_corner--;
            row_num++;
        }
    }

    private static void drawHorizontalRow(TETile type, int x_start, int y, int length, TETile[][] world) {
        for (int x = x_start; x < x_start + length; x++) {
            world[x][y] = type;
        }
    }

    private static void initializeWorld(TETile[][] world) {
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(40, 40);

        TETile[][] hexWorld = new TETile[40][40];
        initializeWorld(hexWorld);

        addHexagon(Tileset.AVATAR, 6, 20, 20, hexWorld);


        ter.renderFrame(hexWorld, "");
    }
}
