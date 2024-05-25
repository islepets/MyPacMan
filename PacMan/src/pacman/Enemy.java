package pacman;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	private  int smart = 1;
	private int random = 0;
	private  int state = random;
	private int right = 0;
	private int left = 1;
	private int up = 2;
	private int down = 3;
	private int dir = -1; 
	public Random randomGen;
	private int time = 0;
	private int targetTime = 60*4;
	private int spd = 4;
	private int lastDir = -1;
	private  int find_path = 2;
	public Enemy(int x, int y) {
		randomGen = new Random();
		setBounds(x, y, 32,32);
		dir = randomGen.nextInt(4);
		
	}
	
	public void tick() {
		if(state == random) {
			if(dir == right) {
				if(canMove(x+spd,y)) {
					if(randomGen.nextInt(100)<50)
						x += spd;
				}else {
					dir = randomGen.nextInt(4);
				}
			}else if(dir == left) {
				if(canMove(x-spd,y)) {
					if(randomGen.nextInt(100)<50)
							x -= spd;
				}else {
					dir = randomGen.nextInt(4);
				}
			}else if (dir == up) {
				if(canMove(x,y-spd)) {
					if(randomGen.nextInt(100)<50)
						y -= spd;
				}else {
					dir = randomGen.nextInt(4);
				}
			}else if(dir == down){
				if(canMove(x,y+spd)) {
					if(randomGen.nextInt(100)<50)
						y += spd;
				}else {
					dir = randomGen.nextInt(4);
				}
			}
		
		time++;
		if(time == targetTime) {
			state = smart;
			time = 0;
		}
			
		}else if(state == smart){
			
			boolean move = false;
			
			if(x < Game.player.x) {
				if(canMove(x+spd,y)) {
					if(randomGen.nextInt(100)<50)
						x+=spd;
					move = true;
					lastDir = right;
				}
			}
			if(x > Game.player.x) {
				if(canMove(x-spd,y)) {
					if(randomGen.nextInt(100)<50)
						x-=spd;
					move = true;
					lastDir = left;
				}
			}
			if(y < Game.player.y) {
				if(canMove(x,y+spd)) {
					if(randomGen.nextInt(100)<50)
						y+=spd;
					move = true;
					lastDir = down;
				}
			}
			if(y > Game.player.y) {
				if(canMove(x,y-spd)) {
					if(randomGen.nextInt(100)<50)
						y-=spd;
					move = true;
					lastDir = up;
				}
			}
			
			if(x == Game.player.x && y ==  Game.player.y) 
				move = true;
			
			if(!move) {
				state = find_path;
			}
			time++;
			if(time == targetTime) {
				state = random;
				time = 0;
			}
				
				
		}else if(state == find_path) {
				if(lastDir == right) {
					if(y < Game.player.y) {
						if(canMove(x,y+spd)) {
							if(randomGen.nextInt(100)<50)
								y+=spd;
							state = smart;
						}
					}else {
						if(canMove(x,y-spd)) {
							if(randomGen.nextInt(100)<50)
								y-=spd;
							state = smart;
						}
					}
					
					if(canMove(x+spd,y)) {
						if(randomGen.nextInt(100)<50)
							x+=spd;
					}
					
				}else if(lastDir == left) {
					if(y < Game.player.y) {
						if(canMove(x,y+spd)) {
							if(randomGen.nextInt(100)<50)
								y+=spd;
							state = smart;
						}
					}else {
						if(canMove(x,y-spd)) {
							if(randomGen.nextInt(100)<50)	
								y-=spd;
							state = smart;
						}
					}
					
					if(canMove(x-spd,y)) {
						if(randomGen.nextInt(100)<50)
							x-=spd;
					}
	
				}else if(lastDir == up) {
					
					if(x < Game.player.x) {
						if(canMove(x+spd,y)) {
							if(randomGen.nextInt(100)<50)
								x+=spd;
							state = smart;
						}
					}else {
						if(canMove(x-spd,y)) {
							if(randomGen.nextInt(100)<50)
								x-=spd;
							state = smart;
						}
					}
					
					if(canMove(x,y-spd)) {
						if(randomGen.nextInt(100)<50)
							y-=spd;
					}
					
					
				}else if(lastDir == down) {
					if(x < Game.player.x) {
						if(canMove(x+spd,y)) {
							if(randomGen.nextInt(100)<50)
								x+=spd;
							state = smart;
						}
					}else {
						if(canMove(x-spd,y)) {
							if(randomGen.nextInt(100)<50)
								x-=spd;
							state = smart;
						}
					}
					
					if(canMove(x,y+spd)) {
						if(randomGen.nextInt(100)<50)
							y+=spd;
					}
					
				}
				time++;
				if(time == targetTime) {
					state = random;
					time = 0;
				}
			
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
		if(Game.LEVEL == 1)
			g.drawImage(Game.spiritSheet.getSpirit(0, 16),x,y,width,height,null);
		else if(Game.LEVEL == 2) {
			g.drawImage(Game.spiritSheet.getSpirit(16, 16),x,y,width,height,null);
		}
		else if(Game.LEVEL == 3)
			g.drawImage(Game.spiritSheet.getSpirit(32, 16),x,y,width,height,null);
	}
}
