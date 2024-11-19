
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class PongCourt extends JComponent {

   //Pieces of the game state- score, # of lives, etc. Also the time left for
   //each of the power-ups.
   public int score = 0;
   private int lives = 2;
   boolean started = false;
   private int smallPowerUpTimeLeft = -1;
   private int bigPowerUpTimeLeft = -1;
   private int bronzeBallTimeLeft = -1;





   //Text panes that wil be updated when game state changes
   private JTextPane score_jtp = null;
   private JTextPane bricks_left_jtp = null;
   private JTextPane level_jtp = null;
   private JTextPane lives_jtp = null;
   
   //The actual parts of the game- balls (as a List for MultiBall), paddle, 
   //power-ups, background music, 2d array of bricks, and a LevelFactory
   private ArrayList<Ball> balls = new ArrayList<Ball>();
   private Paddle paddle;
   private Sound music;
   Brick[][] bricks;
   ArrayList<PowerUp> powerups = new ArrayList<PowerUp>();
   LevelFactory levelfactory = null;
   
   //Constants that are used by the PongCourt to play Breakout
   private int interval = 35; // Milliseconds between updates.
   private Timer timer;       // Each time timer fires we animate one step.
   final int COURTWIDTH = 605;
   final int COURTHEIGHT = 400;
   final int PADDLE_VEL = 15;  // How fast does the paddle move
   final int POWER_UP_DURATION = 4000;
   private Image backgroundImage;




   public PongCourt() {
      setBorder(BorderFactory.createLineBorder(Color.BLACK));
      setFocusable(true);


      timer = new Timer(interval, new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            tick();
         }
      });
      timer.start();

      addKeyListener(new KeyAdapter() {
         @Override
         public void keyTyped(KeyEvent e) {
         }

         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
               //Move left
               paddle.setVelocity(-PADDLE_VEL, 0);
               if (!started) {
                  for (Ball b : balls) {
                     b.setVelocity(-PADDLE_VEL, 0);
                  }
               }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
               //Move right
               paddle.setVelocity(PADDLE_VEL, 0);
               if (!started) {
                  for (Ball b : balls) {
                     b.setVelocity(PADDLE_VEL, 0);
                  }
               }
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
               reset();
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !started) {
               //This code starts the game when you press SPACEBAR
               int bvelx;
               if (paddle.velocityX == 0) {
                  bvelx = 4;
               } else {
                  bvelx = (paddle.velocityX / Math.abs(paddle.velocityX)) * 7;
               }
               for (Ball b : balls) {
                  b.setVelocity(bvelx, -8);
               }
               started = true;
            }

         }

         @Override
         public void keyReleased(KeyEvent e) {
            //Stop moving the paddle (and the connected balls, if any) when the
            //arrow keys are released
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
               paddle.setVelocity(0, 0);
               if (!started) {
                  for (Ball b : balls) {
                     b.setVelocity(0, 0);
                  }
               }
            }
         }
      });
      
      music = new Sound("sound/DigitalStream.wav");
      music.playSound();
   }

   /**
    * Lose the game, giving the player the option of restarting or quitting.
    */
   public void lose() {
      int option = JOptionPane.showOptionDialog(this, "You lost the game! Would you like to continue?", "Lost the game!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Yes", "No"}, null);
      if (option == 0) {
         reset();
      } else if (option == 1) {
         System.exit(0);
      } else if (option == JOptionPane.CLOSED_OPTION) {
         reset();
      }
   }

   /**
    * Win the game. A player can restart and continue to rack up points or quit.
    */
   public void win() {
      int option = JOptionPane.showOptionDialog(this, "You won the game! Would you like to start playing from the beginning with the same score?", "A winner is you!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Yes", "No"}, null);
      if (option == 0 || option == JOptionPane.CLOSED_OPTION) {
         resetCourt();
         lives_jtp.setText("Lives: " + lives);
         levelfactory = new LevelFactory();
         bricks = levelfactory.getLevelArray();
         level_jtp.setText("Level: " + levelfactory.levelOn());
      } else if (option == 1) {
         System.exit(0);
      }
   }

   /**
    * Set the state of the state of the game to its initial value and prepare
    * the game for keyboard input.
    */
   public void reset() {
      lives = 2;
      lives_jtp.setText("Lives: " + lives);
      levelfactory = new LevelFactory();
      bricks = levelfactory.getLevelArray();
      level_jtp.setText("Level: " + levelfactory.levelOn());
      score = 0;
      score_jtp.setText("Score: " + 0);
      resetCourt();
      requestFocusInWindow();
   }
   /**
    * Increment the level by one and prepare the court for the new level.
    * If there are no more levels, the player will win the game.
    */
   public void newLevel() {
      resetPowerups();
      if (levelfactory.hasMoreLevels()) {
         levelfactory.nextLevel();
         bricks = levelfactory.getLevelArray();
         level_jtp.setText("Level: " + levelfactory.levelOn());
         resetCourt();
      } else {
         win();
      }
   }

   /**
    * Update the game one timestep by moving the ball, paddle, and power-ups.
    */
   void tick() {
      decrementPowerUps();
      
      if (bigPowerUpTimeLeft >= 0) {
         paddle.width = Paddle.WIDTH + 30;
      } else if (smallPowerUpTimeLeft >= 0) {
         paddle.width = Paddle.WIDTH - 30;
      } else {
         paddle.width = Paddle.WIDTH;
      }
      paddle.setBounds(getWidth(), getHeight());
      
      
      //Move all of the balls and the paddle
      for (int i = 0; i < balls.size(); i++) {
         balls.get(i).setBounds(getWidth(), getHeight());
         if (!started
            && ((paddle.x <= 0 && balls.get(i).velocityX < 0) || (paddle.x >= paddle.rightBound && balls.get(i).velocityX > 0))) {
            //Don't move the ball if you're holding it on the paddle and you try to clip the paddle past the screen
         } else {
            balls.get(i).move();
         }
      }
      paddle.move();
      
      //Check all the balls and bounce them off the paddle if they intersect it
      for (int i = 0; i < balls.size(); i++) {
         Intersection paddle_ball = paddle.intersects(balls.get(i));
         if (started && paddle_ball != Intersection.NONE) {
            (new Sound("sound/WoodWhack.wav")).playSoundOnce();
            balls.get(i).bounce(paddle.intersects(balls.get(i)));
            balls.get(i).velocityX += ((balls.get(i).x + balls.get(i).width / 2) - (paddle.x + paddle.width / 2)) / (paddle.width / 9);
            if (paddle.velocityX > 0) {
               balls.get(i).velocityX -= 2;
            } else if (paddle.velocityX < 0) {
               balls.get(i).velocityX += 2;
            }
         }
      }
      
      //Break any necessary bricks. Also count how many are left after breakage.
      int bricksLeft = 0;
      for (int col = 0; col < bricks.length; col++) {
         for (int row = 0; row < bricks[col].length; row++) {
            //Invisible bricks can be set to null
            if (bricks[col][row] == null || !bricks[col][row].visible) {
               bricks[col][row] = null;
            } else {
               bricksLeft++;
            }
            //Loop through each ball, and see if it will interact with the brick
            for (int i = 0; i < balls.size(); i++) {
               if (balls.get(i) == null || bricks[col][row] == null) {
                  continue;
               }
               Intersection inters = bricks[col][row].intersects(balls.get(i));
               //Bounce the ball off brick unless Bronze Ball is active
               if (bronzeBallTimeLeft < 0) {
                  balls.get(i).bounce(inters);
               }
               if (inters != Intersection.NONE) {
                  //BREAK THE BRICK by (maybe) putting in a power-up, exploding
                  // the brick, and updating the score.
                  if (Math.random() * 100 < 10 && bricks[col][row].hits == 0) {
                     powerups.add(PowerUp.randomPowerUp(bricks[col][row].x + bricks[col][row].width / 2 - PowerUp.DIAMETER / 2,
                        bricks[col][row].y));
                  }
                  if (bronzeBallTimeLeft < 0) {
                     bricks[col][row].disappear(this);
                     bricks[col][row].playSound();
                  } else { //if (bronzeBallTimeLeft >= 0)
                     bricks[col][row] = null;
                     //During bronze ball, bricks just poof out of existence
                  }
                  score++;
                  score_jtp.setText("Score: " + score);
               }
            }
         }
      }
      //Set the bricks left display
      bricks_left_jtp.setText("Bricks left: " + bricksLeft);
      if (bricksLeft == 0) {
         newLevel();
      }
      
      //Handle all of the power-ups by moving/activating them
      for (int i = 0; i < powerups.size(); i++) {
         PowerUp p = powerups.get(i);
         if (p != null && Math.abs(p.y) > COURTHEIGHT) {
            powerups.set(i, null);
            powerups.remove(i);
         }
         
         //Don't let null power-ups clog the list
         if (p == null) {
            powerups.remove(i);
            continue;
         }
         p.move();
         if (p.intersects(paddle) != Intersection.NONE) {
            activatePowerUp(p);
            powerups.set(i, null);
         }
      }
      
      //Remove balls that are off the screen. If the ball removed is the last
      //ball, then the player loses a life and may lose the game!
      for (int i = 0; i < balls.size(); i++) {
         Ball b = balls.get(i);
         if (b != null && Math.abs(b.y) > COURTHEIGHT) {
            balls.remove(b);
            if (balls.isEmpty()) {
               if (lives > 0) {
                  lives--;
                  lives_jtp.setText("Lives: " + lives);
                  resetCourt();
               } else {
                  lose();
               }
            }
         }
      }
      repaint(); // Repaint indirectly calls paintComponent.

   }
   
   /**
    * Reset the court to its starting state. Don't touch the scores/lives.
    */
   private void resetCourt() {
      started = false;
      paddle = new Paddle(COURTWIDTH, COURTHEIGHT);
      balls.clear();
      Ball ball = new Ball(((COURTWIDTH - Ball.DIAMETER) / 2),
         (COURTHEIGHT - 2 * Paddle.HEIGHT - Ball.DIAMETER), 0, 0);
      balls.add(ball);
      ball.setBounds(getWidth(), getHeight());
      paddle.setBounds(getWidth(), getHeight());
      resetPowerups();
   }
   
   /**
    * Activate a given power-up.
    * @param p The power-up to activate
    */
   private void activatePowerUp(PowerUp p) {
      if (p instanceof OneUpPowerUp) {
         lives++;
         lives_jtp.setText("Lives: " + lives);
      } else if (p instanceof BronzeBallPowerUp) {
         bronzeBallTimeLeft = POWER_UP_DURATION;// measured in milliseconds
         for (Ball ball : balls) {
            ball.color = Color.orange;
         }
      } else if (p instanceof SmallPaddlePowerUp) {
         if (bigPowerUpTimeLeft > 0) {
            bigPowerUpTimeLeft = -1;
            smallPowerUpTimeLeft = -1;
            paddle.width = Paddle.WIDTH;
         } else {
            smallPowerUpTimeLeft = POWER_UP_DURATION;
         }
      } else if (p instanceof BigPaddlePowerUp) {
         if (smallPowerUpTimeLeft > 0) {
            bigPowerUpTimeLeft = -1;
            smallPowerUpTimeLeft = -1;
            paddle.width = Paddle.WIDTH;
         } else {
            bigPowerUpTimeLeft = POWER_UP_DURATION;
         }
      } else if (p instanceof MultiBallPowerUp) {
         Ball b1 = new Ball(paddle.x + paddle.width / 2, paddle.y - paddle.height, 4, -8);
         Ball b2 = new Ball(paddle.x + paddle.width / 2, paddle.y - paddle.height, -5, -8);
         if (bronzeBallTimeLeft >= 0) {
            b1.setColor(Color.ORANGE);
            b2.setColor(Color.ORANGE);
         }
         balls.add(b1);
         balls.add(b2);
      }
   }
   
   /**
    * Make the power-up timers go down by Interval. It allows power-ups to
    * only be active for a given length of time (i.e. PowerUpDuration
    */
   private void decrementPowerUps() {
      if (bronzeBallTimeLeft >= 0) {
         bronzeBallTimeLeft -= interval;
      }
      if (bigPowerUpTimeLeft >= 0) {
         bigPowerUpTimeLeft -= interval;
      }
      if (smallPowerUpTimeLeft >= 0) {
         smallPowerUpTimeLeft -= interval;
      }
      if (bronzeBallTimeLeft < 0) {
         for (Ball ball : balls) {
            ball.color = Color.red;
         }
      }
   }

   /**
    * Paint everything on the court!
    * @param g The GraphicsContext to draw in.
    */
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g); // Paint background, border



      for (int i = 0; i < balls.size(); i++) {
         balls.get(i).draw(g);
      }
      paddle.draw(g);
      for (int col = 0; col < bricks.length; col++) {
         for (int row = 0; row < bricks[col].length; row++) {
            Brick brick = bricks[col][row];
            if (brick != null) {
               brick.draw(g);
            }
         }
      }
      for (int i = 0; i < powerups.size(); i++) {
         if (powerups.get(i) != null) {
            powerups.get(i).draw(g);
         }
      }

   }
   
   /**
    * Set the score display to a given JTextPane. Used by Game.
    * @param j The JTextPane to set to.
    */
   public void setScoreJTP(JTextPane j) {
      score_jtp = j;
   }
   
   /**
    * Set the bricks remaining display to a given JTextPane. Used by Game.
    * @param j The JTextPane to set to.
    */
   public void setBricksLeftJTP(JTextPane j) {
      bricks_left_jtp = j;
   }
   
   
   
   /**
    * Set the level display to a given JTextPane. Used by Game.
    * @param j The JTextPane to set to.
    */
   public void setLevelJTP(JTextPane j) {
      level_jtp = j;
   }
   
   /**
    * Set the lives display to a given JTextPane. Used by Game.
    * @param j The JTextPane to set to.
    */
   public void setLivesJTP(JTextPane j) {
      lives_jtp = j;
   }
   
   /**
    * Reset all the power-up related states of the PongCourt.
    */
   private void resetPowerups() {
      powerups.clear();
      bigPowerUpTimeLeft = -1;
      smallPowerUpTimeLeft = -1;
      bronzeBallTimeLeft = -1;
   }
   
   /**
    * Get the preferred dimensions of this component.
    * @return 
    */
   @Override
   public Dimension getPreferredSize() {
      return new Dimension(COURTWIDTH, COURTHEIGHT);
   }
}
