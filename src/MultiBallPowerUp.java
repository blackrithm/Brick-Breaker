
import java.awt.Color;

/**
    * Constructs a MultiBall power-up, which is cyan in color.
    * @param x Initial x-coord
    * @param y Initial y-coord
    */
public class MultiBallPowerUp extends PowerUp{
   public MultiBallPowerUp(int x, int y) {
      super(x, y, 0, 6);
      color = Color.CYAN;
   }
}
