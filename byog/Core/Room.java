package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;


public class Room {
    private Position p;
    private int width;
    private int height;

    /**
     * constructor of Room object.
     * @param p Position object representing the bottom-left point of the Room.
     * @param w width of the room.
     * @param h height of the room.
     */
    public Room(Position p, int w, int h) {
        this.p = p;
        this.width = w;
        this.height = h;
    }

    public int getXcor() {return this.p.getX();}
    public int getYcor() {
        return this.p.getY();
    }
    //x coordinate of the center point of the room
    public int getCenterX() {
        return this.p.getX() + this.width/2;
    }
    //y coordinate of the center point of the room
    public int getCenterY() {
        return this.p.getY() + this.height/2;
    }
    public Position getPos() { return this.p;}

    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}


//    /**
//     * to draw a room filled with "FLOOR" inside.
//     * @param tiles
//     * @param p is the bottom left position of the room created in tiles.
//     * @param width the width of the room created in tiles.
//     * @param height the height of the room created in tiles.
//     */
//    public static void drawRoom(TETile[][] tiles, Position p, int width, int height) {
//        int startPosX = p.getX();
//        int startPosY = p.getY();
//        for (int x = startPosX; x < startPosX + height; x += 1) {
//            for (int y = startPosY; y < startPosY + width; y += 1) {
//                if (x == startPosX || x == startPosX + height -1 || y == startPosY || y == startPosY + width - 1) {
//                    tiles[x][y] = Tileset.WALL;
//                } else {
//                    tiles[x][y] = Tileset.FLOOR;
//                }
//            }
//        }
//    }

//    /**
//     * to draw L-shaped hallway according to two Position objects.
//     * the hallway is built by three pieces of L-shaped halls.
//     * The middle hall is filled with "FLOOR", and the left and
//     * right hall is filled with "WALL".
//     * @param tiles
//     * @param (x1,y1) the Position object representing one end of the middle hall.
//     * @param (x2,y2) the Position object representing the other end of the middle hall.
//     */
//    public static void drawHallway(TETile[][] tiles, int x1, int y1, int x2, int y2) {
//        if (y1 == y2) {
//            int start = Math.min(x1,x2);
//            int len = Math.abs(x1-x2);
//            for (int i = start; i <= start+len; i += 1) {
//                if (!tiles[i][y1+1].equals(Tileset.FLOOR)) {
//                    tiles[i][y1] = Tileset.WALL;
//                }
//                if (!tiles[i][y1].equals(Tileset.FLOOR)) {
//                    tiles[i][y1] = Tileset.FLOOR;
//                }
//                if (!tiles[i][y1-1].equals(Tileset.FLOOR)) {
//                    tiles[i][y1] = Tileset.WALL;
//                }
//            }
//        }
//        if (x1 == x2) {
//            int start = Math.min(y1, y2);
//            int len = Math.abs(y1-y2);
//            for (int i = start; i <= start+len; i += 1) {
//                if (!tiles[x1-1][i].equals(Tileset.FLOOR)) {
//                    tiles[x1-1][i] = Tileset.WALL;
//                }
//                if (!tiles[x1][i].equals(Tileset.FLOOR)) {
//                    tiles[x1][i] = Tileset.FLOOR;
//                }
//                if (!tiles[x1+1][i].equals(Tileset.FLOOR)) {
//                    tiles[x1][i] = Tileset.WALL;
//                }
//            }
//        }
//        if (x1 < x2 && y1 < y2) {
//            drawLTiles(tiles, x1, y1 + 1, x2 - 1, y2, Tileset.WALL);
//            drawLTiles(tiles, x1, y1, x2, y2, Tileset.FLOOR);
//            drawLTiles(tiles, x1, y1 - 1, x2 + 1, y2, Tileset.WALL);
//        }
//        if (x1 < x2 && y2 < y1) {
//            drawLTiles(tiles, x1, y1 + 1, x2 + 1, y2, Tileset.WALL);
//            drawLTiles(tiles, x1, y1, x2, y2, Tileset.FLOOR);
//            drawLTiles(tiles, x1, y1 - 1, x2 - 1, y2, Tileset.WALL);
//        }
//        if (x2 < x1 && y2 < y1) {
//            drawLTiles(tiles, x1, y1 - 1, x2 + 1, y2, Tileset.WALL);
//            drawLTiles(tiles, x1, y1, x2, y2, Tileset.FLOOR);
//            drawLTiles(tiles, x1, y1 + 1, x2 - 1, y2, Tileset.WALL);
//        }
//        if (x2 < x1 && y1 < y2) {
//            drawLTiles(tiles, x1, y1 + 1, x2 + 1, y2, Tileset.WALL);
//            drawLTiles(tiles, x1, y1, x2, y2, Tileset.FLOOR);
//            drawLTiles(tiles, x1, y1 - 1, x2 - 1, y2, Tileset.WALL);
//        }
//    }
//
//    /**another way to draw hallways*/
//    public static void drawHallway_V2(TETile[][] tiles, int x1, int y1, int x2, int y2) {
//        if (x1 == x2 && y1 == y2) return;
//        if (y1 == y2) {
//            int start = Math.min(x1, x2);
//            int len = Math.abs(x1-x2);
//            for (int i = start; i <= start+len; i += 1) {
//                floorSurroundedByWall(tiles, i, y1);
//            }
//        }
//        if (x1 == x2) {
//            int start = Math.min(y1, y2);
//            int len = Math.abs(y1-y2);
//            for (int i = start; i <= start+len; i += 1) {
//                floorSurroundedByWall(tiles, x1, i);
//            }
//        }
//        if (x1 < x2 && y1 < y2) {
//            for (int i = x1; i <= x2; i += 1) {floorSurroundedByWall(tiles, i, y1);}
//            for (int j = y1; j <= y2; j += 1) {floorSurroundedByWall(tiles, x2, j);}
//        }
//        if (x1 < x2 && y2 < y1) {
//            for (int i = x1; i <= x2; i += 1) {floorSurroundedByWall(tiles, i, y1);}
//            for (int j = y2; j <= y1; j += 1) {floorSurroundedByWall(tiles, x2, j);}
//        }
//        if (x2 < x1 && y2 < y1) {
//            for (int i = x2; i <= x1; i += 1) {floorSurroundedByWall(tiles, i, y2);}
//            for (int j = y2; j <= y1; j += 1) {floorSurroundedByWall(tiles, x1, j);}
//        }
//        if (x2 < x1 && y1 < y2) {
//            for (int i = x2; i <= x1; i += 1) {floorSurroundedByWall(tiles, i, y1);}
//            for (int j = y1; j <= y2; j += 1) {floorSurroundedByWall(tiles, x2, j);}
//        }
//
//    }


//    /**
//     * to draw a L-shaped hall.
//     * @param tiles
//     * @param (x1,y1) the Position object representing one end of the hall.
//     * @param (x2,y2) the Position object representing the other end of the hall.
//     * @param t the tile objects used to build the hall.
//     */
//    public static void drawLTiles(TETile[][] tiles, int x1, int y1, int x2, int y2, TETile t) {
//
//        int Pos1X = x1;
//        int Pos1Y = y1;
//        int Pos2X = x2;
//        int Pos2Y = y2;
//        int startPosX = Math.min(Pos1X, Pos2X);
//        int sizeX = Math.abs(Pos1X - Pos2X);
//        int startPosY = Math.min(Pos1Y, Pos2Y);
//        int sizeY = Math.abs(Pos1Y - Pos2Y) + 1;
//        // to draw the horizontal line.
//        for (int x = startPosX; x < startPosX + sizeX; x +=1) {
//            if (tiles[x][Pos1Y] != Tileset.FLOOR) {
//                tiles[x][Pos1Y] = t;
//            }
//        }
//        // to draw the vertical line.
//        for (int y = startPosY; y < startPosY + sizeY; y +=1) {
//            if (tiles[startPosX + sizeX][y] != Tileset.FLOOR) {
//                tiles[startPosX + sizeX][y] = t;
//            }
//        }
//    }
//    /**
//     * draws a shape like this:
//     *       WWW
//     *       WFW
//     *       WWW
//     * W represents wall, which is only built when NOTHING in there.
//     * F represents floor.
//     * */
//    public static void floorSurroundedByWall(TETile[][] tiles, int x, int y) {
//        tiles[x][y] = Tileset.FLOOR;
//        int[][] directions = {{-1,0},{1,0},{0,1},{0,-1}, {-1,-1},{1,1},{-1,1},{1,-1}};
//        for (int[] direction: directions) {
//            int newX = x + direction[0];
//            int newY = y + direction[1];
//            if (newX < 0 || newY < 0 || newX >= tiles.length || newY >= tiles[0].length) {
//                throw new IllegalArgumentException("cannot build walls out of the bound of the world");
//            }
//            tiles[newX][newY] = tiles[newX][newY] == Tileset.NOTHING ? Tileset.WALL : tiles[newX][newY];
//        }
//    }

//    /**
//     * generates random numbers of random rooms
//     * and stores these rooms in the existingRooms list.
//     * and sorts the rooms according to its x-coordinate.
//     * @param tiles
//     * @param RANDOM
//     */
//    public static void randomRooms(TETile[][] tiles, Random RANDOM) {
//        int randomRoomNum = RANDOM.nextInt(RoomNumBound) + 600;
//
//        for (int i = 0; i < randomRoomNum; i += 1) {
//            Position randomPos = Position.randomPosition(RANDOM);
//            int randomWidth = RANDOM.nextInt(RoomWidthBound) + 5;
//            int randomHeight = RANDOM.nextInt(RoomHeigthBound) + 5;
////            int randomWidth = RANDOM.nextInt(10) + 3;
////            int randomHeight = RANDOM.nextInt(10) + 3;
//            Room newroom = new Room(randomPos, randomWidth, randomHeight);
//            if (!roomOverLap(existingRooms, newroom)) {
//                drawRoom(tiles, randomPos, randomWidth, randomHeight);
//                existingRooms.add(newroom);
//            }
//        }
//        /**sort the rooms according to x coordinator of their position.*/
//        Collections.sort(existingRooms, new Comparator<Room>() {
//            @Override
//            public int compare(Room a, Room b) {
//                return (a.getXcor()-b.getXcor());
//            }
//        });
//    }

////    //another version of generating random rooms.
//    public static void randomRooms(TETile[][] tiles, Random RANDOM) {
//        int w = 0, h = 0;
//        int index = 0;
//        int count = 0;
//        int randomRoomNum = RandomUtils.uniform(RANDOM, 25, 35) + 20;
//
//        while (index < randomRoomNum) {
//            if (randomRoomNum <= 25) {
//                w = RandomUtils.uniform(RANDOM, 10,15);
//                h = RandomUtils.uniform(RANDOM, 10,15);
//            } else if (randomRoomNum < 35) {
//                w = RandomUtils.uniform(RANDOM, 6,12);
//                h = RandomUtils.uniform(RANDOM, 6,12);
//            } else if (randomRoomNum <= 40) {
//                w = RandomUtils.uniform(RANDOM, 5,10);
//                h = RandomUtils.uniform(RANDOM, 5,10);
//            }
//            Position p = Position.randomPosition(RANDOM);
//            int randomX = p.getX();
//            int randomY = p.getY();
//            Room newroom = new Room(p, randomX, randomY);
//            if (!roomOverLap(existingRooms, newroom)) {
//                drawRoom(tiles, p, w, h);
//                existingRooms.add(newroom);
//                index++;
//            }
//            count++;
//            if (count >= 10000) {
//                index = randomRoomNum;
//            }
//        }
//        /**sort the rooms according to x coordinator of their position.*/
//        Collections.sort(existingRooms, new Comparator<Room>() {
//            @Override
//            public int compare(Room a, Room b) {
//                return a.getXcor() - b.getXcor();
//            }
//        });
//    }

//    public static boolean roomOverLap(List<Room> existingRooms, Room newroom) {
//        if (existingRooms.isEmpty()) {
//            return false;
//        }
//        for (Room exiroom: existingRooms) {
//            if (roomOverLap(exiroom, newroom)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    private static boolean roomOverLap(Room room1, Room room2) {
////        if (room1.p.getX() + room1.height - 1 < room2.p.getX() || room2.p.getX() + room2.height - 1 < room1.p.getX() ||
////            room1.p.getY() + room1.width - 1 < room2.p.getY() || room2.p.getY() + room2.width - 1 < room1.p.getY()) {
////            return false;
////        } else {
////            return true;
////        }
//        if (room1.getXcor() == room2.getXcor()) {
//            return true;
//        }
//        //There are always space between two rooms.
//        if (room1.p.getX() + room1.height < room2.p.getX() || room2.p.getX() + room2.height < room1.p.getX() ||
//                room1.p.getY() + room1.width < room2.p.getY() || room2.p.getY() + room2.width < room1.p.getY()) {
//            return false;
//        } else {
//            return true;
//        }
////        if (room1.p.getX() + room1.height + 1< room2.p.getX() || room2.p.getX() + room2.height + 1< room1.p.getX() ||
////                room1.p.getY() + room1.width< room2.p.getY() || room2.p.getY() + room2.width < room1.p.getY()) {
////            return false;
////        } else {
////            return true;
////        }
//
//    }

//    static void connectRooms(TETile[][] world, List<Room> existingRooms) {
//        Room room1 = existingRooms.get(0);
//        int lx = room1.getCenterX(), ly = room1.getCenterY();
//        for (int i = 1; i < existingRooms.size(); i += 1) {
//            Room room = existingRooms.get(i);
////            int x = RANDOM.nextInt(room.getWidth()-2) + room.getXcor() +1;
////            int y = RANDOM.nextInt(room.getHeight()-2) + room.getYcor() +1;
//            int x = room.getXcor();
//            int y = room.getCenterY();
//            Room.drawHallway_V2(world, lx, ly, x, y);
//            lx = x;
//            ly = y;
//        }
//    }

}
