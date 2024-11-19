import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Provides methods to facilitate drawing images, including backgrounds.
 */
public class Picture {

    /**
     * Keep track of pictures that have already been drawn so that we don't have
     * to load them every time.
     */
    private static Map<String, BufferedImage> cache = new HashMap<>();

    /**
     * Draw an image.
     *
     * @param g The graphics context in which to draw the image.
     * @param filepath The location of the image file.
     * @param x The x-coordinate of where the upper-left corner of the image
     * should be drawn.
     * @param y The y-coordinate of where the upper-left corner of the image
     * should be drawn.
     */
    public static void draw(Graphics g, String filepath, int x, int y) {
        try {
            BufferedImage img;
            if (cache.containsKey(filepath)) {
                img = cache.get(filepath);
            } else {
                img = ImageIO.read(new File(filepath));
                cache.put(filepath, img);
            }
            g.drawImage(img, x, y, null);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Draw a given SUBimage.
     *
     * @param g The graphics context in which to draw the image.
     * @param filepath The location of the image file.
     * @param x The x-coordinate of where the upper-left corner of the image
     * should be drawn.
     * @param y The y-coordinate of where the upper-left corner of the image
     * should be drawn.
     * @param width The width of each subimage
     * @param height The height of each subimage
     * @param subimage The subimage to be drawn
     */
    public static void draw(Graphics g, String filepath, int x, int y, int width, int height, int subimage) {
        try {
            BufferedImage img;
            if (cache.containsKey(filepath)) {
                img = cache.get(filepath);
            } else {
                img = ImageIO.read(new File(filepath));
                cache.put(filepath, img);
            }
            g.drawImage(img.getSubimage(subimage * width, 0, width, height), x, y, null);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Draw a background image that covers the entire window.
     *
     * @param g The graphics context in which to draw the image.
     * @param filepath The location of the background image file.
     * @param windowWidth The width of the window.
     * @param windowHeight The height of the window.
     */
    public static void drawBackground(Graphics g, String filepath, int windowWidth, int windowHeight) {
        try {
            BufferedImage img;
            if (cache.containsKey(filepath)) {
                img = cache.get(filepath);
            } else {
                img = ImageIO.read(new File(filepath));
                cache.put(filepath, img);
            }
            g.drawImage(img, 0, 0, windowWidth, windowHeight, null);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

// Example usage in your game logic:
// In the paintComponent method of your game panel:
//
// @Override
// protected void paintComponent(Graphics g) {
//     super.paintComponent(g);
//     if (isStartingPhase) {
//         Picture.drawBackground(g, "path/to/starting_background.jpg", getWidth(), getHeight());
//     } else {
//         Picture.drawBackground(g, "path/to/game_background.jpg", getWidth(), getHeight());
//     }
// }
//
