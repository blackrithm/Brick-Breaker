
import java.awt.Color;

public class SmallPaddlePowerUp extends PowerUp{
   
   /**
    * Actually a power-down, it makes your paddle smaller. It's red in color.
    * Red means bad!
    * @param x x-coord
    * @param y y-coord
    */
   public SmallPaddlePowerUp(int x, int y) {
      super(x, y, 0, 0);
      color = Color.RED;
   }
   
   
   
}
