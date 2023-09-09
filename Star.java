import java.awt.Graphics;
import java.util.Random;

public class Star {

    //The x,y and z coordinates of the star
    private int x, y, z;
    //The last relative x and y coordinates before updating the star's position
    private int lastX, lastY;
    //Max z value
    private final int maxDepth;
    //Maximum star radius
    private final static int maxRadius = 15;
    final private static Random rng = new Random(123);
    //Width and height of the frame
    private final int width;
    private final int height;
    //Velocity with which we are moving through the field
    private float velocity = 0;

    public Star(int screenWidth, int screenHeight) {

        width = screenWidth;
        height = screenHeight;
        this.x = rng.nextInt(-screenWidth, screenWidth);
        this.y = rng.nextInt(-screenHeight, screenHeight);
        maxDepth = screenHeight < screenWidth ? screenWidth : screenHeight;
        this.z = rng.nextInt(0, maxDepth);
        lastX = -1;
        lastY = -1;
    }

    public Star(int screenWidth, int screenHeight, float velocity) {

        width = screenWidth;
        height = screenHeight;
        this.x = rng.nextInt(-screenWidth, screenWidth);
        this.y = rng.nextInt(-screenHeight, screenHeight);
        maxDepth = screenHeight < screenWidth ? screenWidth : screenHeight;
        this.z = rng.nextInt(0, maxDepth);
        lastX = -1;
        lastY = -1;
        this.velocity = velocity;
    }

    public Star(int screenWidth, int screenHeight, int x, int y) {

        width = screenWidth;
        height = screenHeight;
        this.x = x;
        this.y = y;
        maxDepth = screenHeight < screenWidth ? screenWidth : screenHeight;
        this.z = rng.nextInt(0, maxDepth);
        lastX = -1;
        lastY = -1;
    }

    public Star(int screenWidth, int screenHeight, int x, int y, int z) {

        width = screenWidth;
        height = screenHeight;
        this.x = x;
        this.y = y;
        maxDepth = screenHeight < screenWidth ? screenWidth : screenHeight;
        this.z = z;
        lastX = -1;
        lastY = -1;
    }

    /**
     * @param velocity the velocity with which we are moving through the field
     * Updates the star's position with a custom velocity (this is done in order to have backwards compatibility)
     */
    public void update(float velocity) {

        this.velocity = velocity;
        z -= velocity;
        //Resets the star's position when we pass it
        if (z < 0)
            reset();
    }

    /**
     * Updates the star's position with the last set velocity
     */
    public void update() {

        z -= velocity;
        //Resets the star's position when we pass it
        if (z < 0)
            reset();
    }

    /**
     * 
     * @param velocity
     * Sets a new velocity
     */
    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    /**
     * 
     * @param g Graphics context of the frame to which we want to display the star
     * Method for displaying the star, represented as an ellipse and of the apparent streak
     * due to the relative velocity represented as a line.
     */
    public void display(Graphics g) {

        //Calculate the radius of the star. The farther away the star, the smaller it appears
        //and the faster we move, the smaller it appears
        int r = maxRadius - (int) map(z, 0, maxDepth, 0, maxRadius);
        r /= Math.sqrt(velocity+1);

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
        if (starIsInside(relativeX, relativeY))
            g.fillOval(relativeX, relativeY, r, r);
        else {
            reset();
            return;
        }
        //Draw the streak
        g.drawLine(relativeX+r/2, relativeY+r/2, lastX+r/2, lastY+r/2);

        lastX = relativeX;
        lastY = relativeY;
    }

    /**
     * 
     * @param number The number to be transformed
     * @param startInit Start of the initial range (inclusive)
     * @param endInit End of the initial range (inclusive)
     * @param startFinal Start of the second range (inclusive)
     * @param endFinal End of the second range (inclusive)
     * @return The equivalent number on the second range
     * Utility function to map a number from one scale to an equivalent number on a different scale
     */
    private float map(float number, float startInit, float endInit, float startFinal, float endFinal) {

        //See what percentile the number was on the original axis
        float percentage = (number - startInit) / (endInit - startInit);

        //Return the equivalent number for the same percentile but on a different axis
        return percentage * (endFinal - startFinal);
    }

    /**
     * 
     * @param x The x coordinate of the star
     * @param y The y coordinate of the star
     * @return true if the star is inside the frame and false if it isn't
     */
    private boolean starIsInside(int x, int y) {
        return x >= 0 && x <= width &&
               y >= 0 && y <= height;
    }

    /**
     * Resets the star's position
     */
    private void reset() {
        z = maxDepth;
        lastX = -1;
        lastY = -1;
    }
}