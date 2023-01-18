package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.Core.WorldGenerator;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Game {
    TERenderer ter = new TERenderer();
    GUI gui = new GUI();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 75;
    public static final int HEIGHT = 45;
    private Random RANDOM;
    private Position player;
    private TETile[][] world;
    private boolean gameOver;
    private int HEALTH;
    private long SEED;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        gui.initialize(WIDTH,HEIGHT);
        gui.startMenu();
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = Character.toLowerCase(StdDraw.nextKeyTyped());
            switch (key) {
                case ('n'): {
                    newGame();
                    break;
                }
                case ('l'): {
                    loadGame();
                    break;
                }
                case ('q'): {
                    gameOver = true;
                    System.exit(0);
                    break;
                }
            }
//            playerMove(key);
        }
    }

    private void setSeed() {
        String input = "";
        gui.typeSeedDrawFrame(input);

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if (Character.toLowerCase(key) == 's') {
                SEED = Long.parseLong(input);
                break;
            } else if (Character.isDigit(key)) {
                input += String.valueOf(key);
                gui.typeSeedDrawFrame(input);
            }
        }
    }

    public void newGame() {
        setSeed();
        initializeWorld();
//        StdDraw.setPenColor(Color.WHITE);
//        StdDraw.text(10, HEIGHT-10, "You won!");
//        StdDraw.show();
//        StdDraw.pause(2000);
        play();
    }

    private void play() {
        Thread worker = new Thread(() -> {
            while (HEALTH > 0) {
                try {
                    Thread.sleep(1000*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                StdDraw.enableDoubleBuffering();
                HEALTH--;
                if (HEALTH == 0) {
                    gameOver = true;
                    gui.displayMessage("Time out! You failed...");
                }
            }});

        worker.start();
//        System.out.println("when start playing, player at:" + player.getX() + " "+player.getY());
        gameOver = false;
        while (!gameOver) {
            showDescription();
            showHUD();
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = Character.toLowerCase(StdDraw.nextKeyTyped());
            if (HEALTH == 0) {
                gameOver = true;
//                gui.displayMessage("Time out! You failed...");
            }
            switch (key) {
                case (':'): {
                    while (true) {
                        if (!StdDraw.hasNextKeyTyped()) {
                            continue;
                        }
                        if (Character.toLowerCase(StdDraw.nextKeyTyped()) == 'q') {
//                            System.out.println("player coordinate:" + player.getX() + " " + player.getY());
                            saveGame();
                            gui.displayMessage("Game Saved Successfully!");
                            StdDraw.pause(2000);
                            System.exit(0);
                        } else {
                            break;
                        }
                    }
                }
                default:
//                    System.out.println("when start playing, player at before moving:" + player.getX() + " "+player.getY());
                    playerMove(world, key);
            }
        }

        while (gameOver) {
            StdDraw.pause(2000);
            gui.displayMessage("Do you want to restart?(y/n): ");
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = Character.toLowerCase(StdDraw.nextKeyTyped());
            switch (key) {
                case 'y': {
                    worker.stop();
                    playWithKeyboard();
                }
                case 'n': {
                    System.exit(0);
                }
                default:
            }
        }
    }

    private void playerMove(TETile[][] world, char key) {
        Position newPos;
        switch (key) {
            case 'w': {
                newPos = new Position(player.getX(), player.getY()+1);
//                if (world[newPos.getX()][newPos.getY()].equals(Tileset.WALL)) {
//                    System.out.println("you can not move here");
//                    newPos = new Position(player.getX(), player.getY());
//                }
                break;
            }
            case 's': {
                newPos = new Position(player.getX(), player.getY()-1);
//                if (world[newPos.getX()][newPos.getY()].equals(Tileset.WALL)) {
//                    System.out.println("you can not move here");
//                    newPos = new Position(player.getX(), player.getY());
//                }
                break;
            }
            case 'a': {
                newPos = new Position(player.getX()-1, player.getY());
//                if (world[newPos.getX()][newPos.getY()].equals(Tileset.WALL)) {
//                    System.out.println("you can not move here");
//                    newPos = new Position(player.getX(), player.getY());
//                }
                break;
            }
            case 'd': {
                newPos = new Position(player.getX()+1, player.getY());
//                if (world[newPos.getX()][newPos.getY()].equals(Tileset.WALL)) {
//                    System.out.println("you can not move here");
//                    newPos = new Position(player.getX(), player.getY());
//                }
                break;
            }
            default:
                return;
        }
        updatePlayer(world, newPos);
    }


    private void updatePlayer(TETile[][] world, Position p) {
//        int x = p.getX();
//        int y = p.getY();
        if (world[p.getX()][p.getY()].equals(Tileset.COIN)) {
            HEALTH += 1;
        }
        if (world[p.getX()][p.getY()].equals(Tileset.LOCKED_DOOR)) {
            gameOver = true;
//            ter.renderFrame(world);
            gui.displayMessage("Congratulations! You've successfully got out of the dungeon!");
            StdDraw.show();
            StdDraw.pause(500);
        }
        if (!world[p.getX()][p.getY()].equals(Tileset.WALL)) {
            world[p.getX()][p.getY()] = Tileset.PLAYER;
            world[player.getX()][player.getY()] = Tileset.FLOOR;
            player = new Position(p.getX(),p.getY());
//            System.out.println("when updating, player at:" + player.getX() + " "+player.getY());
//            System.out.println("update 以后when start playing, player at:" + player.getX() + " "+player.getY());
//            System.out.println("here here here");
        }
    }

    private void initializeWorld() {
        WorldGenerator worldGenerator = new WorldGenerator(WIDTH,HEIGHT,SEED);
        world = worldGenerator.generateWorld();

        player = worldGenerator.initializePlayer();
        HEALTH = 5;
//        System.out.println("initially, player is at:" + player.getX() + " "+ player.getY());
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }

    private void showDescription() {
        int x = (int)StdDraw.mouseX();
        int y = (int)StdDraw.mouseY();


        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            ter.renderFrame(world);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(7, HEIGHT-3, world[x][y-1].description());
            StdDraw.enableDoubleBuffering();
            StdDraw.show();
        }
    }

    private void showHUD() {
//        ter.renderFrame(world);
        int midWidth = WIDTH/2;
        int midHeight = HEIGHT/2;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(midWidth, HEIGHT-3, "HP: " + String.join("", Collections.nCopies(HEALTH,"❤")));
        StdDraw.textRight(WIDTH-7,HEIGHT-3,sdf.format(date));
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        StdDraw.pause(100);
    }

    private void saveGame() {
        File file = new File("./save_game.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            //serializing 'world'
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(world);
            System.out.println("when saving, the player is at: " + player.getY() + " " + player.getY());
            oos.writeObject(player);
            oos.writeObject(HEALTH);
//            oos.writeObject(player.getPos());
//            oos.writeObject(player.getHealth());
            oos.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private void loadGame() {
        initializeWorld();

        world = (TETile[][]) getSavedGame()[0];
        player = (Position) getSavedGame()[1];
        HEALTH = (int) getSavedGame()[2];
//        System.out.println("after loading, player is at:"+player.getX() + " "+player.getY());
//        Player p = new Player(world, playerPos, 10);
//        player = p;
//        int health = (int) getSavedGame()[2];
//        player = (Player) getSavedGame()[1];
//        player.setPos(playerPos);
//        player.setHealth(health);
        ter.initialize(80, 50);
        ter.renderFrame(world);
        play();
    }

    private Object[] getSavedGame() {
        File file = new File("./save_game.txt");
        TETile[][] world = null;
        Position player = null;
        int health = 0;
//        Player player = null;
//        Position playerPos = null;
//        int playerHealth = 0;
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                world = (TETile[][]) ois.readObject();
                player = (Position) ois.readObject();
                health = (int) ois.readObject();
//                System.out.println("when getting saved value, player is at: "+player.getX() + " "+player.getY());
//                playerHealth = (int) ois.readObject();
//                player = (Player) ois.readObject();

            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found");
                System.exit(0);
            }
        }
//        return new Object[]{world, playerPos, playerHealth};
//        return world;
        return new Object[]{world, player, health};
    }

    private void countDown() {
        Thread worker = new Thread(() -> {
            while (HEALTH > 0) {
                try {
                    Thread.sleep(1000*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                StdDraw.enableDoubleBuffering();
                HEALTH--;
                if (HEALTH == 0) {
                    gameOver = true;
                    gui.displayMessage("Time out! You failed...");
                }
            }});

        worker.start();

    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard.
     * If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;


        return finalWorldFrame;
    }
}
