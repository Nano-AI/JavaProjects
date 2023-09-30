/**
 * @author Aditya Bankoti
 * @date 8/30/2023
 * @description A simple program that uses the DrawPanel.java class to create cool images using java.awt.
 * This app creates a gradient dot panel, then creates a pastel like photo.
 */

// import statements
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Scanner;

public class DrawShapes {
    public static void main(String[] args) {
        // set width and height
        int WIDTH = 600;
        int HEIGHT = 600;

        // setup the drawing panel
        DrawingPanel dp = new DrawingPanel(WIDTH, HEIGHT);

        // get the graphics and convert it to graphics 2d for more functions
        Graphics2D g = (Graphics2D) (dp.getGraphics());

        // establish radius of circles
        int r = 20;

        // colors for gradient
        Color c1 = new Color(255, 0, 0);
        Color c2 = new Color(0, 0, 255);
        Color c3 = new Color(155, 0, 155);
        // create a gradient
        GradientPaint gp1 = new GradientPaint(0, 0, c1, WIDTH, HEIGHT, c2);
        GradientPaint gp2 = new GradientPaint(0, 0, c3, WIDTH, HEIGHT, c1);
        GradientPaint gp3 = new GradientPaint(0, 0, Color.BLACK, WIDTH, HEIGHT, Color.WHITE);

        // set a black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // padding between each dot
        int padding = 30;

        int row = 0, col = 0;
        // create canvas with grid of circles with a gradient
        for (int i = 0; i < WIDTH; i += r + padding) {
            for (int j = 0; j < HEIGHT; j += r + padding) {
                // create a pattern of boxes of colors
                if (row % 2 == col % 2) {
                    g.setPaint(gp3);
                } else {
                    g.setPaint(gp1);
                }
                // create a circle or oval
                // g.fillRect(i, j, r, r);
                g.fillOval(i, j, r, r);
                col++;
            }
            row++;
        }

        // create buffered image to read pixels of image`
        BufferedImage bi = null;

        // try and read image
        try {
            // read the image
            bi = ImageIO.read(new File("./beach.jpg"));
        } catch(Exception e) {
            // throw error if something happened
            e.printStackTrace();
        }

        // create a new panel with the size of the image
        DrawingPanel image = new DrawingPanel(bi.getWidth(), bi.getHeight());

        // create an integer array of pixels that will store an RGB value for a pixel
        int[] pixel;

        // get the graphics for the panel
        Graphics2D ig = image.getGraphics();

        // set a resolution for each pixel
        int res = 0;

        Scanner s = new Scanner(System.in);
        System.out.print("Enter the minimum resolution: ");
        int min_res = s.nextInt();
        System.out.print("Enter the maximum resolution: ");
        int max_res = s.nextInt();

        // iterate with a radom number between [25, 30) that will be the new res
        for (int m = max_res; m >= min_res; m--) {
            // iterate through the entire image row first
            for (int i = 0; i < bi.getHeight(); i += res) {
                // iterate through each pixel in the row
                for (int j = 0; j < bi.getWidth(); j += res) {
                    // set the resolution randomly
                    res = (int) (Math.random() * m);
                    // get the pixel in position (i, j) and store it into the pixel as a
                    // 3 int array
                    pixel = bi.getRaster().getPixel(j, i, new int[3]);
                    // set the new color of the pixel
                    ig.setColor(new Color(pixel[0], pixel[1], pixel[2]));
                    // fill an oval with a custom size
                    ig.fillOval(j, i, res, res);
                }
            }
        }
    }
}
