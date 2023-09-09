import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Starfield extends Frame {

    private float velocity;
    private Star[] stars;
    private static int width, height;
    private int numStars;

    static {
        width = 1280;
        height = 720;
    }

    public Starfield(float velocity, int numStars) {

        this.velocity = velocity;
        this.numStars = numStars;
        stars = new Star[numStars];

        for (int i = 0; i < numStars; i++)
            stars[i] = new Star(width, height);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setBackground(Color.BLACK);
    }

    @Override
    public void paint(Graphics g) {

        Graphics tmp = g;
        Image buffImage = createImage(width, height);
        g = buffImage.getGraphics();

        g.setColor(Color.WHITE);
        for (int i = 0; i < numStars; i++) {
            stars[i].display(g);
            stars[i].update(velocity);
        }

        tmp.drawImage(buffImage, 0, 0, null);
    }

    public static void main(String[] args) {

        long currentTime;

        Starfield starfield = new Starfield(1, 400);
        starfield.setSize(width, height);
        starfield.setVisible(true);

        currentTime = System.currentTimeMillis();

        while (true) {
            
            if (System.currentTimeMillis() - currentTime >= 10) {
                starfield.repaint();
                currentTime = System.currentTimeMillis();
            }
        }
    }
}