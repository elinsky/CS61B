package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    private static final Color floor_gray = new Color(89, 86, 82);
    public static final TETile AVATAR = new TETile('@', Color.white, floor_gray, "you", "/Users/brianelinsky/Dropbox/ActiveProjects/CS61B/cs61b_code/skeleton-sp19/proj3/byow/TileEngine/link.png");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", "/Users/brianelinsky/Dropbox/ActiveProjects/CS61B/cs61b_code/skeleton-sp19/proj3/byow/TileEngine/wall.png");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), floor_gray,
            "floor", "/Users/brianelinsky/Dropbox/ActiveProjects/CS61B/cs61b_code/skeleton-sp19/proj3/byow/TileEngine/floor.png");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing", "/Users/brianelinsky/Dropbox/ActiveProjects/CS61B/cs61b_code/skeleton-sp19/proj3/byow/TileEngine/nothing.png");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, floor_gray, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, floor_gray, "mountain");
    public static final TETile COIN = new TETile('O', Color.YELLOW, floor_gray, "coin", "/Users/brianelinsky/Dropbox/ActiveProjects/CS61B/cs61b_code/skeleton-sp19/proj3/byow/TileEngine/Retro-Coin-icon.png");
    public static final TETile BLUE_GHOST = new TETile('X', Color.YELLOW, floor_gray, "ghost", "/Users/brianelinsky/Dropbox/ActiveProjects/CS61B/cs61b_code/skeleton-sp19/proj3/byow/TileEngine/blue-ghost.png");
}


