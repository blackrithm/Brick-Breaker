
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Brick extends GameObject {

    //Value fields; self-explanatory
    private final int interval = 35;
    final static int HEIGHT = 12;
    final static int WIDTH = 45;

    //The timer that controls the animation. Instantiated in constructor.
    Timer animationTimer;
    //The number of steps in the animation. 4 is default but 
    //it depends on the color.
    private int ANIMATION_STEPS = 4;
    //The sub-image that should be drawn to the canvas. 
    public int imageOn = 0;
    //Should the brick be drawn, or has the animation finished?
    public boolean visible = true;
    //How many hits the brick has. Some bricks can take more than one hit.
    public int hits = 0;
    
    /**
     * Constructs a generic brick of the default color. Uses the default picture
     * to animate.
     * @param x  x-coord
     * @param y  y-coord
     */
    public Brick(int x, int y) {
        super(x, y, 0, 0, WIDTH, HEIGHT);
    }
    /**
     * Constructs a brick of the specified color
     * @param x x-coord
     * @param y y-coord
     * @param c Color
     */
    public Brick(int x, int y, Color c) {
        super(x, y, 0, 0, WIDTH, HEIGHT);
        color = c;
        //White bricks have more animation steps because they crack before 
        //they break
        if(c == Color.white){
            hits = -1;
            ANIMATION_STEPS = 5;
        }
    }

    @Override
    public void accelerate() {
        //Do nothing. Bricks do not accelerate.
    }

    @Override
    /**
     * Draws the brick to the canvas. Different pictures are used for different-
     * colored bricks.
     */
    public void draw(Graphics g) {
       //Don't draw an invisible brick
        if (visible == false) {
            return;
        }
        g.setColor(color);
        String str;
        if (color.equals(Color.BLUE)) {
            str = "img\\brick_blue.gif";
        } else if (color.equals(Color.RED)) {
            str = "img\\brick_red.gif";
        } else if (color.equals(Color.white)) {
            str = "img\\brick_white.png";
        } else {
            str = "img\\brick.gif";
        }
        Picture.draw(g, str, x, y, WIDTH, HEIGHT, imageOn);

    }

    @Override
    /**
     * Figures out how this brick intersects another GameObject (e.g. a Ball).
     */
    public Intersection intersects(GameObject other) {
        Intersection i = super.intersects(other);
        if (i != Intersection.NONE) {
        }
        return i;
    }
    /**
     * Causes the brick to explode, going through its animation steps and 
     * painting the canvas each time it steps the animation.
     * A brick must have zero hits in order to fully disappear. Bricks taking 
     * multiple hits simply increment the imageOn and the hit counter.
     * @param pc 
     */
    void disappear(final PongCourt pc) {
        if(hits <0){
            hits++;
            imageOn++;
            return;
        }
        //Explode the brick!
        animationTimer = new Timer(interval, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animationTick(pc);
            }
        });
        animationTimer.start();
    }

    /**
     * Represents one tick of the explosion timer, which will increment the 
     * subimage and redraw the brick in its new state. 
     * @param pc The PongCourt on which the brick should be redrawn. Calling 
     * this method will repaint the PongCourt.
     */
    public void animationTick(PongCourt pc) {
        if (imageOn < ANIMATION_STEPS - 1) {
            imageOn++;
        } else {
            visible = false;
            animationTimer.stop();
            pc.repaint();
        }
    }
    /**
     * Plays the explosion sound specified in the field.
     */
    void playSound() {
        String brickBreakSoundFile = "Sound/MirrorShatter.wav";
        Sound brickBreakSound = new Sound(brickBreakSoundFile);
        brickBreakSound.playSoundOnce();
    }
}
