
import java.awt.Color;

public class OneUpPowerUp extends PowerUp {
   
   /**
    * Constructs a 1-up power-up, which is green in color.
    * @param x Initial x-coord
    * @param y Initial y-coord
    */
   public OneUpPowerUp(int x, int y) {
      super(x, y, 0, 0);
      color = Color.GREEN;
   }
}
