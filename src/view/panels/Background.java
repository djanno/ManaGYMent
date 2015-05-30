package view.panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A generic panel painted by a background image.
 * 
 * @author Federico Giannoni
 *
 */
public class Background extends JPanel implements IBackground {

    private static final long serialVersionUID = 7482906460679934615L;

    private final String path;

    /**
     * Constructs the panel using the string provided to the constructor as the
     * path to the image that is going to be set as the background.
     * 
     * @param path
     *            the path to the image that is going to be set as the
     *            background.
     */
    public Background(final String path) {
        super();
        this.setFocusable(true);
        this.path = path;
    }

    @Override
    public String getBackgroundPath() {
        return this.path;
    }

    @Override
    public void paintComponent(final Graphics g) {
        final Image bg = new ImageIcon(this.getClass().getResource(path)).getImage();
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
    }

}
