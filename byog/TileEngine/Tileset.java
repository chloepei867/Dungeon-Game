package byog.TileEngine;

import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

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

public class Tileset implements Serializable {
    public static final TETile PLAYER = new TETile('@', Color.white, Color.black, "player");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");

    public static final TETile WALL0 = new TETile('#', new Color(217, 125, 245, 252), new Color(239, 165, 245, 245),
            "wall");
    public static final TETile WALL1 = new TETile('#', new Color(238, 210, 246, 252), new Color(240, 166, 246, 245),
            "wall");
    public static final TETile WALL2 = new TETile('#', new Color(218, 145, 239, 189), new Color(215, 149, 220, 255),
            "wall");
    public static final TETile WALL3 = new TETile('#', new Color(215, 172, 229, 255), new Color(236, 145, 243, 255),
            "wall");
    public static final TETile WALL4 = new TETile('#', new Color(110, 96, 115, 255), new Color(214, 165, 217, 255),
            "wall");
    private static final TETile[] PINKWALL = {
            WALL0,
            WALL1,
            WALL2,
            WALL3,
            WALL4,
    };

    public static int wallIndex = 0;
    public static TETile PINK_WALL() {
        TETile ret = PINKWALL[wallIndex];
        wallIndex = (wallIndex+1)%PINKWALL.length;
        return ret;
    }

    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile COIN = new TETile('$', Color.yellow, Color.black, "coin");

    private static final long serialVersionUID = 5158709291016569974L;
}


