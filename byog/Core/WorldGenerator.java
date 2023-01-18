package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.Core.Room;
import edu.princeton.cs.introcs.StdDraw;

import java.lang.reflect.Array;
import java.util.*;


/**
 * draws a world that contains random tiles.
 */
public class WorldGenerator {
    private int WIDTH;
    private int HEIGHT;
    //2800000
    private static long SEED;
    private Random RANDOM;
    private Player player;

    private TETile[][] world;
    private static ArrayList<Room> existingRooms;

    public WorldGenerator(int width, int height, long seed) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.SEED = seed;
        this.RANDOM = new Random(SEED);
        existingRooms = new ArrayList<>();
    }

    public TETile[][] generateWorld() {
        initializeWorld();
        generateRandomRooms();
        connectRooms();
//        player = initializePlayer();
        setDoor();
        setCoins();

        return world;
    }

    public Position initializePlayer() {
        int i = RANDOM.nextInt(existingRooms.size());
        Room r = existingRooms.get(i);
        int x = r.getCenterX();
        int y = r.getCenterY();
        Position player = new Position(x,y);
        world[x][y] = Tileset.PLAYER;
        return player;
    }

    public void initializeWorld() {
        // initialize tiles
        this.world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
//
//    private Player initializePlayer() {
//        int i = RANDOM.nextInt(existingRooms.size());
//        Room r = existingRooms.get(i);
//        int x = r.getCenterX();
//        int y = r.getCenterY();
//        Player player = new Player(world, new Position(x,y), 5);
//        player.display(x,y);
//        return player;
//    }

    private void setDoor() {
        while (true) {
            int xx = RANDOM.nextInt(WIDTH);
            int yy = RANDOM.nextInt(HEIGHT);
            if (world[xx][yy] == Tileset.WALL &&
                    ((world[xx + 1][yy] == Tileset.FLOOR
                    || world[xx - 1][yy] == Tileset.FLOOR
                    || world[xx][yy + 1] == Tileset.FLOOR
                    || world[xx][yy - 1] == Tileset.FLOOR)) &&
                    ((world[xx + 1][yy] == Tileset.NOTHING
                            || world[xx - 1][yy] == Tileset.NOTHING
                            || world[xx][yy + 1] == Tileset.NOTHING
                            || world[xx][yy - 1] == Tileset.NOTHING))) {
                world[xx][yy] = Tileset.LOCKED_DOOR;
                break;
            }
        }
    }

    private void setCoins() {
        int coinsNum = RANDOM.nextInt(10) + 5;
        int count = 0;
        int x, y;
        while (count < coinsNum) {
            x = RANDOM.nextInt(WIDTH);
            y = RANDOM.nextInt(HEIGHT);
            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.COIN;
                count++;
            }
        }
    }


    /**
     * generates random numbers of random rooms
     * and stores these rooms in the existingRooms list.
     * and sorts the rooms according to its x-coordinate.
     */
    private void generateRandomRooms() {
        int randomRoomNum = RANDOM.nextInt(25) + 600;

        for (int i = 0; i < randomRoomNum; i += 1) {
            Position randomPos = Position.randomPosition(RANDOM);
            int randomWidth = RANDOM.nextInt(10) + 5;
            int randomHeight = RANDOM.nextInt(10) + 5;
//            int randomWidth = RANDOM.nextInt(10) + 3;
//            int randomHeight = RANDOM.nextInt(10) + 3;
            Room newroom = new Room(randomPos, randomWidth, randomHeight);
            if (!roomOverLap(existingRooms, newroom) && !outOfBound(newroom)) {
                drawRoom(randomPos, randomWidth, randomHeight);
                existingRooms.add(newroom);
            }
        }

        /**sort the rooms according to x coordinator of their position.*/
        Collections.sort(existingRooms, new Comparator<Room>() {
            @Override
            public int compare(Room a, Room b) {
                return (a.getXcor()-b.getXcor());
            }
        });
    }

    private void connectRooms() {
        Room room1 = existingRooms.get(0);
        int lx = room1.getCenterX(), ly = room1.getCenterY();
        for (int i = 1; i < existingRooms.size(); i += 1) {
            Room room = existingRooms.get(i);
//            int x = RANDOM.nextInt(room.getWidth()-2) + room.getXcor() +1;
//            int y = RANDOM.nextInt(room.getHeight()-2) + room.getYcor() +1;
            int x = room.getXcor();
            int y = room.getCenterY();
            drawHallway(lx, ly, x, y);
            lx = x;
            ly = y;
        }
    }

    /**
     * to draw a room filled with "FLOOR" inside.
     *      WWWWWWWWWW
     *      WFFFFFFFFW
     *      WFFFFFFFFW
     *      WWWWWWWWWW
     * @param p is the bottom left position of the room created in the world.
     * @param width the width of the room created in the world.
     * @param height the height of the room created in the world.
     */
    private void drawRoom(Position p, int width, int height) {
        int startPosX = p.getX();
        int startPosY = p.getY();
        for (int x = startPosX; x < startPosX + width; x += 1) {
            for (int y = startPosY; y < startPosY + height; y += 1) {
                if (x == startPosX || x == startPosX + width -1 || y == startPosY || y == startPosY + height - 1) {
//                    world[x][y] = Tileset.WALL;
                    world[x][y] = TETile.colorVariant(Tileset.WALL,50,50,50, RANDOM);
//                    world[x][y] = Tileset.PINK_WALL();
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    private boolean roomOverLap(List<Room> existingRooms, Room newroom) {
        if (existingRooms.isEmpty()) {
            return false;
        }
        for (Room exiroom: existingRooms) {
            if (roomOverLap(exiroom, newroom)) {
                return true;
            }
        }
        return false;
    }
    private boolean roomOverLap(Room room1, Room room2) {
//        if (room1.p.getX() + room1.height - 1 < room2.p.getX() || room2.p.getX() + room2.height - 1 < room1.p.getX() ||
//            room1.p.getY() + room1.width - 1 < room2.p.getY() || room2.p.getY() + room2.width - 1 < room1.p.getY()) {
//            return false;
//        } else {
//            return true;
//        }
        if (room1.getXcor() == room2.getXcor()) {
            return true;
        }
        //There are always space between two rooms.
        if (room1.getXcor() + room1.getWidth() < room2.getXcor() || room2.getXcor() + room2.getWidth() < room1.getXcor() ||
                room1.getYcor() + room1.getHeight() < room2.getYcor() || room2.getYcor() + room2.getHeight() < room1.getYcor()) {
            return false;
        } else {
            return true;
        }
//        if (room1.p.getX() + room1.height + 1< room2.p.getX() || room2.p.getX() + room2.height + 1< room1.p.getX() ||
//                room1.p.getY() + room1.width< room2.p.getY() || room2.p.getY() + room2.width < room1.p.getY()) {
//            return false;
//        } else {
//            return true;
//        }

    }

    private boolean outOfBound(Room room) {
        if (room.getXcor() <= 3 || room.getYcor() <= 3 || room.getXcor() + room.getWidth() >= WIDTH - 3
                || room.getYcor() + room.getHeight() >= HEIGHT-3) {
            return true;
        }
        return false;
    }

    /**
     * to draw L-shaped hallway between two Position objects.
     * @param (x1,y1) the Position object representing one end.
     * @param (x2,y2) the Position object representing the other end.
     */
    private void drawHallway(int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) return;
        if (y1 == y2) {
            int start = Math.min(x1, x2);
            int len = Math.abs(x1-x2);
            for (int i = start; i <= start+len; i += 1) {
                floorSurroundedByWall(i, y1);
            }
        }
        if (x1 == x2) {
            int start = Math.min(y1, y2);
            int len = Math.abs(y1-y2);
            for (int i = start; i <= start+len; i += 1) {
                floorSurroundedByWall(x1, i);
            }
        }
        if (x1 < x2 && y1 < y2) {
            for (int i = x1; i <= x2; i += 1) {floorSurroundedByWall(i, y1);}
            for (int j = y1; j <= y2; j += 1) {floorSurroundedByWall(x2, j);}
        }
        if (x1 < x2 && y2 < y1) {
            for (int i = x1; i <= x2; i += 1) {floorSurroundedByWall(i, y1);}
            for (int j = y2; j <= y1; j += 1) {floorSurroundedByWall(x2, j);}
        }
        if (x2 < x1 && y2 < y1) {
            for (int i = x2; i <= x1; i += 1) {floorSurroundedByWall(i, y2);}
            for (int j = y2; j <= y1; j += 1) {floorSurroundedByWall(x1, j);}
        }
        if (x2 < x1 && y1 < y2) {
            for (int i = x2; i <= x1; i += 1) {floorSurroundedByWall(i, y1);}
            for (int j = y1; j <= y2; j += 1) {floorSurroundedByWall(x2, j);}
        }
    }

    /**
     * draws a shape like this:
     * The size is 3 x 3.
     *       WWW
     *       WFW
     *       WWW
     * W represents wall, which is only built when NOTHING in there.
     * F represents floor.
     * */
    private void floorSurroundedByWall(int x, int y) {
        world[x][y] = Tileset.FLOOR;
        int[][] directions = {{-1,0},{1,0},{0,1},{0,-1}, {-1,-1},{1,1},{-1,1},{1,-1}};
        for (int[] direction: directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (newX < 0 || newY < 0 || newX >= world.length || newY >= world[0].length) {
                throw new IllegalArgumentException("cannot build walls out of the bound of the world");
            }
            world[newX][newY] = world[newX][newY] == Tileset.NOTHING ? Tileset.WALL : world[newX][newY];
//            world[newX][newY] = world[newX][newY] == Tileset.NOTHING ? Tileset.PINK_WALL() : world[newX][newY];
//            TETile wall = TETile.colorVariant(Tileset.WALL,50,50,50, RANDOM);
//            if (!world[newX][newY].equals(Tileset.NOTHING)) {
//                return;
//            }
//            world[newX][newY] = Tileset.PINK_WALL();
//            world[newX][newY] = world[newX][newY].equals(Tileset.NOTHING) ? Tileset.WALL : world[newX][newY];
        }
    }
}
