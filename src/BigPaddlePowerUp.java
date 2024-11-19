
import java.awt.Color;

public class BigPaddlePowerUp extends PowerUp {
   
   /**
    * Constructs a Big Paddle power-up, which is magenta in color.
    * @param x Initial x-coord
    * @param y Initial y-coord
    */
   public BigPaddlePowerUp(int x, int y) {
      super(x, y, 0, 6);
      color = Color.MAGENTA;
   }
   
   
}
