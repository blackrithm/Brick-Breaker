
import java.awt.*;

public class Ball extends GameObject {

    final static int DIAMETER = 10;
   
    /**
     * Constructs a ball at location (x, y) with velocity (vX, vY)
     * @param x The x-coordinate of the ball
     * @param y The y-coordinate of the ball
     * @param velocityX Initial x-velocity
     * @param velocityY Initial y-velocity
     */
    public Ball(int x, int y, int velocityX, int velocityY) {
        super(x, y, velocityX, velocityY, DIAMETER, DIAMETER);
        color = Color.RED;
    }
    
    /**
     * Reverses the velocity if the ball tries to go offscreen, making it
     * "bounce" off the edges.
     */
    public void accelerate() {
        if (x < 0) {
            velocityX = Math.abs(velocityX);
        } else if (x > rightBound) {
            velocityX = -Math.abs(velocityX);
        }
        if (y < 0) {
            velocityY = Math.abs(velocityY);
        } else if (y > bottomBound) {
           //The ball will be off the screen- and I'll handle this in PongCourt
            //throw new UnsupportedOperationException("Lost the game");
        }
    }

    /**
     * Bounce the ball, if necessary
     * */
    public void bounce(Intersection i) {
        switch (i) {
            case NONE:
                break;
            case UP:
                velocityY = -Math.abs(velocityY);
                break;
            case DOWN:
                velocityY = Math.abs(velocityY);
                break;
            case LEFT:
                velocityX = -Math.abs(velocityX);
                break;
            case RIGHT:
                velocityX = Math.abs(velocityX);
                break;
        }
    }
    
    /**
     * Moves the ball WITHOUT CLIPPING. If the ball goes off the bottom, the
     * player will lose, but it can't go off the bottom if it clips.
     */
   @Override
    public void move(){
       x += velocityX;
       y += velocityY; 
       accelerate();
    }
   
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, DIAMETER, DIAMETER);
    }
}
