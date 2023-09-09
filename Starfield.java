import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        g.setColor(Color.WHITE);
        for (int i = 0; i < numStars; i++) {
            stars[i].display(g);
            stars[i].update(velocity);
        }
    }

    public static void main(String[] args) {
        
        Starfield starfield = new Starfield(50, 300);
        starfield.setSize(width, height);
        starfield.setVisible(true);

        while (true) {
            starfield.repaint();
            try {
                Thread.sleep(100);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}