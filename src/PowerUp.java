
import java.awt.Graphics;


public abstract class PowerUp extends GameObject {

   final public static int DIAMETER = 15;
   
   /**
    * Make a generic power-up. Power-ups fall at a random rate, at least 4 but
    * as much as 10. Since PowerUp is abstract, it is only used by subclasses.
    * @param x x-coord
    * @param y y-coord
    * @param velocityX Not strictly necessary. This might be used if you wanted
    * power-ups to move diagonally and only wanted to change one line of code.
    * @param velocityY Not strictly necessary. This might be useful for changing
    * how power-ups in general fall down.
    */
   public PowerUp(int x, int y, int velocityX, int velocityY) {
      super(x, y, 0, (int)(Math.random() * 6) + 4, DIAMETER, DIAMETER/2);

   }
   
   /**
    * Power-ups do not accelerate.
    */
   @Override
   public void accelerate() {
   }
   
   /**
    * Power-ups move, but do not accelerate or clip.
    */
   public void move(){
      y += velocityY; 
      x += velocityX;
   }
   
   /**
    * Draw the given power-up. Power ups look like pills, so they are twice
    * as wide as they are tall.
    * @param g 
    */
   @Override
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillOval(x, y, DIAMETER, DIAMETER/2);
   }
   
   /**
    * Returns an array of power-ups for use in randomPowerUp().
    * @param x x-coord for each power-up
    * @param y y-coord for each power-up
    * @return  
    */
   private static PowerUp[] powerUpOptions(int x, int y) {
      return new PowerUp[]
      {new SmallPaddlePowerUp(x, y), new BigPaddlePowerUp(x, y), 
      new OneUpPowerUp(x, y), new BronzeBallPowerUp(x, y), 
      new MultiBallPowerUp(x, y)};

   }
   
   /**
    * Select a random power-up from all of the available options.
    * @param x x-coord of power-up
    * @param y y-coord of power-up
    * @return 
    */
   public static PowerUp randomPowerUp(int x, int y) {
      PowerUp[] options = powerUpOptions(x, y);
      return options[(int)(Math.random() * options.length)];
   }
}
