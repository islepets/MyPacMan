package pacman;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	public boolean right,left,up,down;
	private int speed = 4;
	public int score = 0;
	private int time = 0, targetTime = 10;
	public int imageIndex = 0;
	public int upimageIndex = 0;
	private int lastDir = 1;
	
	public Player(int x, int y){
		setBounds(x,y,32,32);
		
	}
	
	public void tick() {
		if(right && canMove(x + speed, y)) {
			x+=speed;
			lastDir = 1;
		}
		if(left && canMove(x - speed, y)) {
			x-=speed;
			lastDir = -1;
		}
		if(up && canMove(x, y - speed)) {
			y-=speed;
			lastDir = 2;
		}
		if(down && canMove(x, y + speed)) {
			y+=speed;
			lastDir = -2;
		}
		
		
		for(int i = 0; i < Game.level.apples.size(); i++) {
			if(this.intersects(Game.level.apples.get(i))) {
				Game.level.apples.remove(i);
				score+=25;
				break;
			}
		}		
		
		for(int i = 0 ; i < Game.level.enemies.size();i++) {
			if(this.intersects(Game.level.enemies.get(i))) {
				Game.STATE = Game.END;
				return;
			}
		}
		if(Game.level.apples.size() == 0) {
			Game.STATE = Game.NEXT;
			return;
		}
		time++;
		if(time == targetTime) {
			time = 0;
			imageIndex++;
			upimageIndex++;
		}
		
	}
	
	private boolean canMove(int nextx, int nexty) {
			
		Rectangle bounds = new Rectangle(nextx, nexty, width, height); 
		Level level = Game.level;
		
		for(int xx = 0; xx < level.tiles.length; xx++) {
			for(int yy = 0; yy < level.tiles[0].length;yy++) {
				if(level.tiles[xx][yy] != null) {
					if(bounds.intersects(level.tiles[xx][yy]))
						return false;
				}
			}
		}
		return true;
	}
	
	
	public void render(Graphics g) {
		if(lastDir == 1)
			g.drawImage(SpiritSheet.player[imageIndex%2], x, y, width, height, null);
		else if(lastDir == -1)
			g.drawImage(SpiritSheet.player[imageIndex%2], x+32, y, -width, height, null);
		else if(lastDir == 2)
			g.drawImage(SpiritSheet.uplayer[upimageIndex%2], x, y, width, height, null);
		else if(lastDir == -2)
			g.drawImage(SpiritSheet.uplayer[imageIndex%2], x, y+32, width, -height, null);
	}
	
	
	
}
