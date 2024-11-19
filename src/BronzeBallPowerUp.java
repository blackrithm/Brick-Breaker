 
import java.awt.Color;

public class BronzeBallPowerUp extends PowerUp {
   /**
    * Constructs a bronze-ball PowerUp, which is orange. Bronze balls can
    * punch through bricks like it ain't no thang.
    * @param x x-coord
    * @param y y-coord
    */
   public BronzeBallPowerUp(int x, int y) {
      super(x, y, 0, 0);
      color = Color.ORANGE;
   }
}
