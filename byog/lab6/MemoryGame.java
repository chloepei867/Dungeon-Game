package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

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

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();


        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i += 1) {
            int index = rand.nextInt(26);
            sb.append(CHARACTERS[index]);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        int midW = width/2;
        int midH = width/2;
        StdDraw.clear();
        StdDraw.clear(Color.black);

        if (!gameOver) {
            Font font1 = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font1);
            StdDraw.textLeft(0, height-1, "Round: " + round);
            StdDraw.text(midW, height-1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(width, height-1, ENCOURAGEMENT[round%ENCOURAGEMENT.length]);
            StdDraw.line(0, height-2, width, height-2);
            int mx = (int) StdDraw.mouseX();
            int my = (int) StdDraw.mouseY();

            StdDraw.text(mx, my, "It worked!!!");

        }

        StdDraw.setPenColor(Color.white);
        Font font2 = new Font("Monaco",Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(midW, midH,s);
        StdDraw.show();


    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i += 1) {
            drawFrame(letters.substring(i, i+1));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String input = "";

        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = StdDraw.nextKeyTyped();
            input += String.valueOf(c);
            drawFrame(input);
        }
        StdDraw.pause(500);
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;

        //TODO: Establish Game loop
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round:"+ round + "! Good Luck!");
            StdDraw.pause(1000);

            String s = generateRandomString(round);
            flashSequence(s);

            playerTurn = true;
            String userInput = solicitNCharsInput(round);

            if (userInput.equals(s)) {
                drawFrame("Correct, well done!");
                StdDraw.pause(2000);
                round++;
            } else {
                drawFrame("Game Over! You made it to round:" + round);
                gameOver = true;
            }
        }
    }
}
