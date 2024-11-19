import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game implements Runnable {
   public void run() {
       
      
      // Top-level frame
      final JFrame frame = new JFrame("Breakout!");
      frame.setLocation(300, 300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Main playing area
      final PongCourt court = new PongCourt();

      frame.add(court, BorderLayout.CENTER);

      // Reset button
      final JPanel panel = new JPanel();
      frame.add(panel, BorderLayout.SOUTH);
      final JButton reset = new JButton("Replay");
      reset.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            court.reset();
         }
      });
      panel.add(reset);
         
      //Info button
      final JButton info = new JButton("Info");
      info.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(court, "Use the arrow keys to move, space bar to launch\n\nFeature List:\n*Explosions \n*Explosion Sounds\n*Trippy Background Music\n*Multiple levels\n*Power-Ups!\n*Multiple Lives\n*Paddle control\n   -Spin\n   -Change in velocity depends on bounce location\n*MULTIBALL!\n*Multi-hit bricks\n*Sweet-spot bricks in the corner for major destruction!", "Information", JOptionPane.INFORMATION_MESSAGE);
            court.requestFocusInWindow();
         }
      });
      panel.add(info);
      
      //Score display
      final JTextPane score = new JTextPane();
      score.setText("Score: " + 0);
      court.setScoreJTP(score);
      panel.add(score);
      
      //Bricks remaining display
      final JTextPane bricksLeft = new JTextPane();
      score.setText("Initializing bricks left");
      court.setBricksLeftJTP(bricksLeft);
      panel.add(bricksLeft);
      
      //Level Display
      final JTextPane level = new JTextPane();
      score.setText("Initializing level...");
      court.setLevelJTP(level);
      panel.add(level);
      
      //Lives display
      final JTextPane lives = new JTextPane();
      score.setText("Initializing lives...");
      court.setLivesJTP(lives);
      panel.add(lives);

      // Put the frame on the screen
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      // Start the game running
      court.reset();
      //info.doClick();

      }

   /*
    * Get the game started!
    */
   public static void main(String[] args) {
       SwingUtilities.invokeLater(new Game());
       
   }

}
