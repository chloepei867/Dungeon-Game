package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = -7929299362054276011L;
    private Position p;
    private int health;
    private final TETile[][] world;

    /**
     * constructor of player object.
     * */
    public Player(TETile[][] world, Position p, int health) {
        this.world = world;
        this.p = p;
        this.health = health;
    }

    /**
     * return the player's position.
     * */
    public Position getPos() {
        return this.p;
    }

    public int getHealth() {return this.health;}

    public void increaseHealth(int h) {
        this.health += h;
    }

    public void decreaseHealth(int h) {
        this.health -= h;
    }

//    public void up() {
//        if (canMove(0, 1)) {
////            world[p.getX()][p.getY()] = Tileset.FLOOR;
//            this.p = p.move(0,1);
////            world[p.getX()][p.getY()] = Tileset.PLAYER;
//        }
//    }
//
//    public void down() {
//        if (canMove(0, -1)) {
////            world[p.getX()][p.getY()] = Tileset.FLOOR;
//            this.p = p.move(0,-1);
////            world[p.getX()][p.getY()] = Tileset.PLAYER;
//        }
//    }
//
//    public void left() {
//        if (canMove(-1, 0)) {
////            world[p.getX()][p.getY()] = Tileset.FLOOR;
//            this.p = p.move(-1,0);
////            world[p.getX()][p.getY()] = Tileset.PLAYER;
//        }
//    }
//
//    public void right() {
//        if (canMove(1, 0)) {
////            world[p.getX()][p.getY()] = Tileset.FLOOR;
//            this.p = p.move(1,0);
////            world[p.getX()][p.getY()] = Tileset.PLAYER;
//        }
//    }

    public void setPos(Position p) {
//        display(p.getX(),p.getY());
        this.p = p;
//        return this;
    }

    public void setHealth(int h) {this.health = h;}

    private boolean canMove(int dx, int dy) {
        int i = this.p.getX() + dx;
        int j = this.p.getY() + dy;
        return world[i][j] != Tileset.WALL;
    }

    public void display(int x, int y) {
        world[x][y] = Tileset.PLAYER;
    }




}
