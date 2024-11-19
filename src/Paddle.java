
import java.awt.*;

public class Paddle extends GameObject {

   final static int HEIGHT = 14;
   final static int WIDTH = 70;
   
   /**
    * Makes a paddle in the default position on a PongCourt.
    * @param courtwidth The width of the PongCourt
    * @param courtheight The height of the PongCout
    */
   public Paddle(int courtwidth, int courtheight) {
      super((courtwidth - WIDTH) / 2, courtheight - HEIGHT - HEIGHT, 0, 0, WIDTH, HEIGHT);
      color = Color.BLACK;
   }
   
   /**
    * Prevents the paddle from going off the edge
    */
   public void accelerate() {
      if (x < 0 || x > rightBound) {
         velocityX = 0;
      }
      if (y < 0 || y > bottomBound) {
         velocityY = 0;
      }
   }
   
   
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillRect(x, y, width, height);
   }
}
