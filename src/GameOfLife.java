import java.awt.*;
import java.util.Scanner;

public class GameOfLife {
    int WWIDTH, WHEIGHT, GWIDTH, GHEIGHT;

    DrawingPanel dp;

    int[][] grid;
    public GameOfLife(int windowWidth, int windowHeight, int gridWidth, int gridHeight) {
        WWIDTH = windowWidth;
        WHEIGHT = windowHeight;
        GWIDTH = gridWidth;
        GHEIGHT = gridHeight;

        grid = randomFill(0.10, GWIDTH, GHEIGHT);

        dp = new DrawingPanel(windowWidth, windowHeight);

        while (true) {
            // scanner.nextLine();
            nextGen();
            draw();
        }
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < GWIDTH && y < GHEIGHT;
    }

    public int neighbourSum(int y, int x) {
        int sum = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (inBounds(y + i, x + j) && i != 0 && j != 0) {
                    sum += grid[y + i][x + j];
                }
            }
        }
        return sum;
    }

    public void nextGen() {
        int[][] newGrid = new int[GHEIGHT][GWIDTH];
        for (int i = 0; i < GHEIGHT; i++) {
            for (int j = 0; j < GWIDTH; j++) {
                int sum = neighbourSum(i, j);
                if (sum < 2 || sum > 3) {
                    newGrid[j][i] = 0;
                } else {
                    newGrid[j][i] = 1;
                }
            }
        }
        grid = newGrid;
    }

    public static int[][] randomFill(double percent, int width, int height) {
        int[][] b = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Math.random() < percent) {
                    b[j][i] = 1;
                }
            }
        }
        return b;
    }

    public void draw() {
        Graphics g = dp.getGraphics();
        int ovalWidth = WWIDTH / GWIDTH;
        int ovalHeight = WHEIGHT / GHEIGHT;
        int ovalSize = Math.min(ovalWidth, ovalHeight);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WWIDTH, WHEIGHT);
        g.setColor(Color.BLACK);
        for (int i = 0; i < GHEIGHT; i++) {
            for (int j = 0; j < GWIDTH; j++) {
                if (grid[i][j] == 1) {
                    g.fillRect(i * ovalSize, j * ovalSize, ovalSize, ovalSize);
                }
            }
        }
    }

    public static void main(String[] args) {
        new GameOfLife(900, 900, 100, 100);
    }
}
