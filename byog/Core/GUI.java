package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class GUI {
    private static int width;
    private static int height;
    private static int midWIDTH = width/2;
    private int midHEIGHT = height/2;


    public void initialize(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width*16 , this.height*16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void startMenu() {
        int midWIDTH = width/2;
        int midHEIGHT = height/2;
        StdDraw.clear();
        StdDraw.clear(Color.black);

        Font Font1 = new Font("Comic Sans Ms", Font.BOLD, 50);
        StdDraw.setFont(Font1);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(midWIDTH, midHEIGHT+10, "WELCOME TO THE DUNGEON GAME!");

        Font Font2 = new Font("Robot", Font.PLAIN, 20);
        StdDraw.setFont(Font2);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(midWIDTH, midHEIGHT+3, "NEW GAME(N)");
        StdDraw.text(midWIDTH, midHEIGHT, "LOAD GAME(L)");
        StdDraw.text(midWIDTH, midHEIGHT-3, "QUIT(Q)");
        StdDraw.text(midWIDTH, midHEIGHT-6, "SAVE&QUIT(:Q)");

        StdDraw.show();
    }

    public void displayMessage(String message) {
        int midWIDTH = width/2;
        int midHEIGHT = height/2;
        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font Font2 = new Font("Comic Sans Ms", Font.PLAIN, 30);
        StdDraw.setFont(Font2);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(midWIDTH, midHEIGHT, message);
        StdDraw.show();



    }

    public void typeSeedDrawFrame(String seed) {
        int midWIDTH = width/2;
        int midHEIGHT = height/2;
        StdDraw.clear();
        StdDraw.clear(Color.black);
        Font Font2 = new Font("Comic Sans Ms", Font.PLAIN, 30);
        StdDraw.setFont(Font2);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(midWIDTH,midHEIGHT, "Enter a seed(press 's' to start): "+ seed);
        StdDraw.show();


    }
}
