package byog.Core;

import java.io.Serializable;
import java.util.Random;



/**
The Position class is used to store x and y coordinates.
 */
public class Position implements Serializable {
    private static final long serialVersionUID = -1840043548237938012L;
    private int x;
    private int y;

    private static final int XBOUND = 60;
    private static final int YBOUND = 30;

    /**
     * the constructor of Position objects.
     * @param x the x coordinate of the given Position.
     * @param y the y coordinate of the given Position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * to return the x coordinate of given Position.
     * */
    public int  getX() {
        return this.x;
    }

    /**
     * to return the y coordinate of given Position.
     * */
    public int getY() {
        return this.y;
    }

    /**
     * creates a new Position object according to the given Position.
     * @param dx difference of the x coordinate between the given Position and the new one.
     * @param dy difference of the y coordinate between the given Position and the new one.
     * @return return the new Position.
     */
    public Position move(int dx, int dy) {
        Position p = new Position(0, 0);
        p.x = this.x + dx;
        p.y = this.y + dy;
        return p;
    }


    /**
     * randomly generates a Position object within certain bounds.
     * @param RANDOM
     * @return
     */
    public static Position randomPosition(Random RANDOM) {
        int randomX = RANDOM.nextInt(XBOUND) + 5;
        int randomY = RANDOM.nextInt(YBOUND) + 5;
        return new Position(randomX, randomY);
    }


}
