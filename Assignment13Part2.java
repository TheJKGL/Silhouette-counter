package malakhov.searching_silhouettes;

import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.util.ErrorException;

import java.util.LinkedList;
import java.util.Queue;

public class Assignment13Part2 {

    //Threshold to consider the figure as dark.
    private static final int INTENSITY = 100;

    //Default path to the photo.
    private static final String PATH = "test.png";

    public static void main(String[] args) {
        try {
            GImage image = new GImage(args.length > 0 ? args[0] : PATH);
            Assignment13Part2 object = new Assignment13Part2();
            int number = object.findSilhouettes(image);
            System.out.println(number);
        } catch (ErrorException e) {
            System.out.println("File not found!");
        }
    }

    private int[][] array;
    private boolean[][] visitedArray;
    private final Queue<GPoint> queue = new LinkedList<>();
    private int silhouetteCounter = 1;
    private int counter = 0;
    private int height;
    private int wight;

    /**
     * This method finds the number of silhouettes.
     * It also decomposes images into 1 and 0, 1 - black, 0 - white.
     *
     * @param image Picture with silhouettes.
     * @return number of silhouettes.
     */
    protected int findSilhouettes(GImage image) {
        int[][] pixelArray = image.getPixelArray();
        array = new int[pixelArray.length][pixelArray[0].length];
        visitedArray = new boolean[pixelArray.length][pixelArray[0].length];

        for (int y = 0; y < pixelArray.length - 1; y++) {
            for (int x = 0; x < pixelArray[0].length - 1; x++) {
                int green = GImage.getGreen(pixelArray[y][x]);
                int blue = GImage.getBlue(pixelArray[y][x]);
                int red = GImage.getRed(pixelArray[y][x]);
                int alpha = GImage.getAlpha(pixelArray[y][x]);

                if (green < INTENSITY && blue < INTENSITY && red < INTENSITY && alpha > INTENSITY) {
                    array[y][x] = 1;
                } else {
                    array[y][x] = 0;
                }
                visitedArray[y][x] = false;

            }
        }
        markSilhouettes(image);
        //printBinaryDecomposition();
        return silhouetteCounter - 1;
    }

    /**
     * This method finds the color of the background.
     * Back ground is white or not?
     *
     * @return boolean result.
     */
    public boolean backgroundIsWhite() {
        int whiteCounter = 0;
        int blackCounter = 0;
        for (int i = 0; i < array[0].length; i++) {
            if (array[0][i] == 0) {
                whiteCounter++;
            } else {
                blackCounter++;
            }
        }
        return whiteCounter > blackCounter;
    }

    /**
     * This method outputs the result of binary decomposition of the image.
     */
    private void printBinaryDecomposition() {
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[0].length; x++) {
                System.out.print(array[y][x]);
            }
            System.out.println();
        }
    }

    /**
     * This method finds silhouettes in a binary array,
     * and launches from the first element(1) deep first search.
     * When dfs end, the method looks for the next item(1).
     * It also increments silhouettesСounter after completion dfs.
     */
    public void markSilhouettes(GImage image) {
        for (int y = 0; y < array.length - 1; y++) {
            for (int x = 0; x < array[0].length - 1; x++) {
                if (array[y][x] == (backgroundIsWhite() ? 1 : 0) && !visitedArray[y][x]) {
                    bfs(y, x);
                    //Here we miss the garbage.
                    if (counter > getTheAreaOfTrash(image)) {
                        silhouetteCounter++;
                    }
                    counter = 0;
                } else {
                    visitedArray[y][x] = true;
                }
            }
        }
    }

    /**
     * This method implements the algorithm breadth first search.
     *
     * @param y column number.
     * @param x row number.
     */
    public void bfs(int y, int x) {
        queue.add(new GPoint(y, x));
        while (!queue.isEmpty()) {
            GPoint point = queue.remove();
            int newX = (int) point.getY();
            int newY = (int) point.getX();
            visitedArray[newY][newX] = true;
            array[newY][newX] = silhouetteCounter;
            counter++;
            checkPointsAround(newY, newX);
        }
    }

    /**
     * This method checks points around the start point.
     * And if it fits then it write a point in the queue.
     *
     * @param newX x - coordinate.
     * @param newY y - coordinate.
     */
    public void checkPointsAround(int newY, int newX) {
        GPoint[] points = new GPoint[]{
                new GPoint(newY - 1, newX),
                new GPoint(newY, newX + 1),
                new GPoint(newY + 1, newX),
                new GPoint(newY, newX - 1)};
        for (int i = 0; i < points.length; ) {
            boolean isOutOfBounds =
                    (int) points[i].getY() < 0
                    || (int) points[i].getX() < 0
                    || (int) points[i].getY() >= wight
                    || (int) points[i].getX() >= height;
            if (isOutOfBounds) {
                i++;
                continue;
            }
            boolean isVisited = visitedArray[(int) points[i].getX()][(int) points[i].getY()] || queue.contains(points[i]);
            boolean isNotSuitable = array[(int) points[i].getX()][(int) points[i].getY()] == (backgroundIsWhite() ? 0 : 1);
            if (isNotSuitable || isVisited) {
                i++;
                continue;
            }
            queue.add(points[i]);
            i++;
        }
    }

    /**
     * This method finds the area of trash in the picture.
     * And calculate the per cent of trash.
     *
     * @param image input image.
     * @return per cent of trash.
     */
    public double getTheAreaOfTrash(GImage image) {
        height = (int) image.getHeight();
        wight = (int) image.getWidth();
        double area = height * wight;
        return (area / 100) * 0.1/*per cent*/;
    }
}
