import java.awt.Graphics;
import java.util.Random;

public class Star {

    private int x, y, z;
    private int lastX, lastY;
    private final int maxDepth;
    private final static int maxRadius = 12;
    final private static Random rng = new Random(123);
    private final int width;
    private final int height;

    public Star(int screenWidth, int screenHeight) {

        width = screenWidth;
        height = screenHeight;
        this.x = rng.nextInt(-screenWidth, screenWidth);
        this.y = rng.nextInt(-screenHeight, screenHeight);
        maxDepth = screenHeight < screenWidth ? screenWidth : screenHeight;
        this.z = rng.nextInt(0, maxDepth);
        lastX = x;
        lastY = y;
    }

    public Star(int screenWidth, int screenHeight, int x, int y) {

        width = screenWidth;
        height = screenHeight;
        this.x = x;
        this.y = y;
        maxDepth = screenHeight < screenWidth ? screenWidth : screenHeight;
        this.z = rng.nextInt(0, maxDepth);
        lastX = x;
        lastY = y;
    }

    public Star(int screenWidth, int screenHeight, int x, int y, int z) {

        width = screenWidth;
        height = screenHeight;
        this.x = x;
        this.y = y;
        maxDepth = screenHeight < screenWidth ? screenWidth : screenHeight;
        this.z = z;
        lastX = x;
        lastY = y;
    }

    public void update(float velocity) {

        z -= velocity;
        //Resets the star when we pass it
        if (z < 0) {
            z = maxDepth;
            lastX = -1;
            lastY = -1;
        }
    }

    public void display(Graphics g) {

        //Calculate the radius of the star. The farther away the star, the smaller it appears
        int r = maxRadius - (int) map(z, 0, maxDepth, 0, maxRadius);

        int relativeX, relativeY;
        
        relativeX = (int) map((float) x / z, 0, 1, 0, width);
        relativeY = (int) map((float) y / z, 0, 1, 0, height);
        //Translate everything by width/2 since we're working with the coordinates translated to the middle of the screen.
        //That means that the translated coordinates (0, 0) are actually (width/2, height/2)
        relativeX += width / 2;
        relativeY += height / 2;

        //If star was reset, reset the last positions
        if (lastX < 0)
            lastX = relativeX;
        if (lastY < 0)
            lastY = relativeY;

        //Draw the star
        g.fillOval(relativeX, relativeY, r, r);
        //Draw the streak
        g.drawLine(relativeX+r/2, relativeY+r/2, lastX, lastY);

        lastX = relativeX;
        lastY = relativeY;
    }

    private float map(float number, float startInit, float endInit, float startFinal, float endFinal) {

        //See what percentile the number was on the original axis
        float percentage = (number - startInit) / (endInit - startInit);

        //Return the equivalent number for the same percentile but on a different axis
        return percentage * (endFinal - startFinal);
    }
}