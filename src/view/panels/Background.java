package view.panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Background extends JPanel implements IBackground{

	private static final long serialVersionUID = 7482906460679934615L;
	
	private final String path;
	
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
