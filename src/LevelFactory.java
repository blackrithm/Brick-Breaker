
import java.awt.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gazelle
 */
public class LevelFactory {

   private Color color = Color.black;
   private final static int BRICK_SEPARATION_X = 5;
   private final static int BRICK_SEPARATION_Y = 4;
   private final static int NUM_LEVELS = 3;
   private int levelOn = 2;
   
   /**
    * Gets the level array for this factory. A LevelFactory knows what level 
    * it is currently on, so it will always give the correct array.
    * @return 
    */
   public Brick[][] getLevelArray() {
      Brick[][] bricks = new Brick[12][14];

      for (int i = 0; i < bricks.length; i++) {
         for (int j = 0; j < bricks[i].length; j++) {
            switch (levelOn) {
               case 1:
               default:
                  if (j == 5 && i == 6) {
                     bricks[i][j] = wb(i, j);
                  }
                  break;
               case 3:
                  if (j > 2 && j < 13) {
                     color = Color.black;
                     bricks[i][j] = def_b(i, j);
                     bricks[i][j].setColor(color);
                  }
                  break;

               case 2:
                  int A = 10, B = 11 ,C=12, D = 13, E = 14, F = 15;
                  return new Brick[][]{
                        {null    , null    , null    , null    , null    , null    , null    , null    , null    , null    , null    , null    },
                        {null    , null    , bb(2, 1), bb(3, 1), bb(4, 1), bb(5, 1), bb(6, 1), bb(7, 1), bb(8, 1), bb(9, 1), bb(10, 1)    , null    },
                        {null    , null    , bb(2, 2), bb(3, 2), bb(4, 2), bb(5, 2), bb(6, 2), bb(7, 2), bb(8, 2), bb(9, 2), bb(10, 2)    , null    },
                        {null    , null    , rb(2, 3), rb(3, 3), rb(4, 3), rb(5, 3), rb(6, 3), rb(7, 3), rb(8, 3), rb(9, 3), rb(10, 3)    , null    },
                        {null    , null    , rb(2, 4), rb(3, 4), wb(4, 4), rb(5, 4), rb(6, 4), rb(7, 4), wb(8, 4), rb(9, 4), rb(10, 4)    , null    },
                        {null    , null    , rb(2, 5), rb(3, 5), wb(4, 5), rb(5, 5), rb(6, 5), rb(7, 5), wb(8, 5), rb(9, 5), rb(10, 5)    , null    },
                        {null    , null    , rb(2, 6), rb(3, 6), rb(4, 6), rb(5, 6), wb(6, 6), rb(7, 6), rb(8, 6), rb(9, 6), rb(10, 6)    , null    },
                        {null    , null    , rb(2, 7), rb(3, 7), rb(4, 7), rb(5, 7), wb(6, 7), rb(7, 7), rb(8, 7), rb(9, 7), rb(10, 7)    , null    },
                        {null    , null    , rb(2, 8), rb(3, 8), rb(4, 8), rb(5, 8), rb(6, 8), rb(7, 8), rb(8, 8), rb(9, 8), rb(10, 8)    , null    },
                        {null    , null    , rb(2, 9), rb(3, 9), rb(4, 9), rb(5, 9), rb(6, 9), rb(7, 9), rb(8, 9), rb(9, 9), rb(10, 9)    , null    },
                        {null    , null    , rb(2, A), rb(3, A), rb(4, A), rb(5, A), rb(6, A), rb(7, A), rb(8, A), rb(9, A), rb(10, A)    , null    },
                        {null    , null    , rb(2, B), rb(3, B), rb(4, B), rb(5, B), rb(6, B), rb(7, B), rb(8, B), rb(9, B), rb(10, B)    , null    },
                        {null    , null    , null    , rb(3, C), rb(4, C), rb(5, C), rb(6, C), rb(7, C), rb(8, C), rb(9, C)    , null    , null    },
                        {null    , null    , null    , null    , rb(4, D), rb(5, D), rb(6, D), rb(7, D), rb(8, D)    , null    , null    , null    },
                        {null    , null    , null    , null    , null    , rb(5, E), rb(6,E) , rb(7,E) , null    , null    , null    , null    },
                        {null    , null    , null    , null    , null    , null    , rb(6, F), null    , null    , null    , null    , null    },
                        {null    , null    , null    , null    , null    , null    , null    , null    , null    , null    , null    , null    },
                        {null    , null    , null    , null    , null    , null    , null    , null    , null    , null    , null    , null    },
                        {null    , null    , null    , null    , null    , null    , null    , null    , null    , null    , null    , null    },
                  };
            }
         }
      }

      return bricks;
   }
   
   /**
    * Constructs a new LevelFactory. All values are defaults.
    */
   public LevelFactory() {
      
   }
   
   
   /**
    * Utility method that makes a default Brick
    * @param i Index of the brick in the row
    * @param j Index of the brick in the column
    * @return 
    */
   private Brick def_b(int i, int j) {
      return new Brick(BRICK_SEPARATION_X + ((BRICK_SEPARATION_X + Brick.WIDTH) * i),
         BRICK_SEPARATION_Y + ((BRICK_SEPARATION_Y + Brick.HEIGHT) * j));
   }
   
   /**
    * Utility method that makes a red Brick
    * @param i Index of the brick in the row
    * @param j Index of the brick in the column
    * @return 
    */
   private Brick rb(int i, int j) {
      return new Brick(BRICK_SEPARATION_X + ((BRICK_SEPARATION_X + Brick.WIDTH) * i),
         BRICK_SEPARATION_Y + ((BRICK_SEPARATION_Y + Brick.HEIGHT) * j), Color.red);
   }
   
   /**
    * Utility method that makes a blue Brick
    * @param i Index of the brick in the row
    * @param j Index of the brick in the column
    * @return 
    */
   private Brick bb(int i, int j) {
      return new Brick(BRICK_SEPARATION_X + ((BRICK_SEPARATION_X + Brick.WIDTH) * i),
         BRICK_SEPARATION_Y + ((BRICK_SEPARATION_Y + Brick.HEIGHT) * j), Color.blue);
      
   }
   
   /**
    * Utility method that makes a white Brick. It can take TWO hits!
    * @param i Index of the brick in the row
    * @param j Index of the brick in the column
    * @return 
    */
   private Brick wb(int i, int j) {
      return new Brick(BRICK_SEPARATION_X + ((BRICK_SEPARATION_X + Brick.WIDTH) * i),
         BRICK_SEPARATION_Y + ((BRICK_SEPARATION_Y + Brick.HEIGHT) * j), Color.white);
      
   }
   
   /**
    * Access method for number of levels
    */
   public int getNumLevels() {
      return NUM_LEVELS;
   }
   /**
    * Access method for level on
    */
   public int levelOn() {
      return levelOn;
   }

   /**
    * Checks whether this Factory can make more levels
    */
   public boolean hasMoreLevels() {
      return levelOn < NUM_LEVELS;
   }

   /**
    * Increments the level by one
    */
   public void nextLevel() {
      levelOn++;
   }
}
