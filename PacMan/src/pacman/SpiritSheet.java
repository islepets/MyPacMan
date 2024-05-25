package pacman;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpiritSheet {
	
	public static BufferedImage[] player;
	public static BufferedImage[] uplayer;
	private BufferedImage sheet;
	public SpiritSheet(String path) {
		try {
			sheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = new BufferedImage[2];
		uplayer = new BufferedImage[2];
		player[0] = getSpirit(0,0);
		player[1] = getSpirit(16,0);
		uplayer[0] = getSpirit(32,0);
		uplayer[1] = getSpirit(48,0);
	}
	
	
	public BufferedImage getSpirit(int xx, int yy) {
		return sheet.getSubimage(xx, yy, 16, 16);
	}
}
