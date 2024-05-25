package pacman;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;
	public static int WIDTH = 640;
	public static int HEIGHT = 480;
	public static final String TITLE = "Pac-Man";

	private static JFrame frame;
	private Thread thread;
	public static Player player;
	public static Level level;
	public static SpiritSheet spiritSheet;

	public static final int PAUSE_SCREEN = 0;
	public static final int GAME = 1;
	public static int STATE = -1;
	public static int NEXT = 2;
	public static int END = -2;
	public static int LEVEL;
	public boolean isEnter = false;

	private int time = 0;
	private int targetFrames = 30;
	private boolean showText = true;

	public static int sec = 0;
	public static int min = 0;
	private int timer = 0;

	public Game() {
	    setPreferredSize(new Dimension(WIDTH, HEIGHT));
	    addKeyListener(this);
	    STATE = PAUSE_SCREEN;
	}

	public synchronized void start() {
	    if (isRunning)
	        return;
	    isRunning = true;
	    thread = new Thread(this);
	    thread.start();

	}

	public synchronized void stop() {
	    if (!isRunning) return;
	    isRunning = false;
	    try {
	        thread.join();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}

	private void tick() {
	    if (STATE == GAME) {
	        player.tick();
	        level.tick();
	        timer++;
	        sec = timer / 60;
	        min = sec / 60;
	        sec = sec % 60;
	        if (level.apples.size() == 0 && LEVEL == 3) {
	            STATE = END;
	            time = 0;
	            min = 0;
	            timer = 0;
	            sec = 0;
	        }
	    } else if (STATE == PAUSE_SCREEN) {
	        time++;
	        min = 0;
	        timer = 0;
	        sec = 0;
	        if (time == targetFrames) {
	            time = 0;
	            if (showText) {
	                showText = false;
	            } else {
	                showText = true;
	            }
	        }
	        if (isEnter) {
	            isEnter = false;
	            player = new Player(WIDTH / 2, HEIGHT / 2);
	            level = new Level("/map/map.png");
	            LEVEL = 1;
	            spiritSheet = new SpiritSheet("/sprites/spriteshit.png");
	            STATE = GAME;
	        }
	    }
	}

	private void render(Graphics g) {
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

	    if (STATE == GAME) {
	        player.render(g);
	        level.render(g);
	        g.setColor(Color.WHITE);
	        g.setFont(new Font("Arial", Font.BOLD, 12));
	        g.drawString("SCORE: " + player.score, 10, 20);
	        g.drawString("TIME: " + String.format("%02d:%02d", min, sec), 570, 20);
	    } else if (STATE == PAUSE_SCREEN) {
	        int boxWidth = 500;
	        int boxHeight = 200;
	        int xx = Game.WIDTH / 2 - boxWidth / 2;
	        int yy = Game.HEIGHT / 2 - boxHeight / 2;
	        g.setColor(new Color(0, 0, 150));
	        g.fillRect(xx, yy, boxWidth, boxHeight);
	        g.setColor(Color.WHITE);
	        g.setFont(new Font(Font.DIALOG, Font.BOLD, 26));
	        if (showText) {
	            g.drawString("Press SPACE to start the game", xx + 60, yy + 80);
	            g.drawString("OR", xx + 220, yy + 120);
	            g.drawString("Press ESC to back", xx + 130, yy + 160);
	        }
	    } else if (STATE == NEXT) {
	        LEVEL++;
	        player.render(g);
	        if (LEVEL == 2) {
	            level = new Level("/map/map1.png");
	            player.score += 150;
	        } else if (LEVEL == 3) {
	            level = new Level("/map/map2.png");
	            player.score += 150;
	        }
	        level.render(g);
	        g.setColor(Color.WHITE);
	        g.setFont(new Font("Arial", Font.BOLD, 12));
	        g.drawString("SCORE: " + player.score, 10, 20);
	        STATE = GAME;
	    } else if (STATE == END) {
	        int boxWidth = 500;
	        int boxHeight = 200;
	        int xx = Game.WIDTH / 2 - boxWidth / 2;
	        int yy = Game.HEIGHT / 2 - boxHeight / 2;
	        g.setColor(new Color(0, 0, 150));
	        g.fillRect(xx, yy, boxWidth, boxHeight);
	        g.setColor(Color.WHITE);
	        g.setFont(new Font(Font.DIALOG, Font.BOLD, 26));
	        g.drawString("Thanks for play on this game ❤️", xx + 30, yy + 70);
	        g.drawString("Your score: " + player.score, xx + 30, yy + 110);
	        g.drawString("Your time: " + String.format("%02d:%02d", min, sec), xx + 30, yy + 150);
	        g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
	        g.drawString("Press the SPACE bar to continue", xx + 100, yy + 300);
	        
	        try {
			    BufferedReader reader = new BufferedReader(new FileReader("table.txt"));
			    String line;
			    boolean isDuplicate = false;
			    while ((line = reader.readLine()) != null) {
			        String[] parts = line.split(" ");
			        if (parts.length >= 3) {
			            String name = parts[0];
			            int score = Integer.parseInt(parts[1]);
			            if (name.equals(StartMenu.name)) {
			                isDuplicate = true;
			                if (Game.player.score > score) {
			                    PrintWriter writer = new PrintWriter("temp.txt");
			                    String updateLine = StartMenu.name + " " + Game.player.score + " " + String.format("%02d:%02d", Game.min, Game.sec);
			                    writer.println(updateLine);
			                    while ((line = reader.readLine()) != null) {
			                        writer.println(line);
			                    }
			                    writer.close();
			                    reader.close();
			                    new File("table.txt").delete();
			                    File file = new File("temp.txt");
			                    file.renameTo(new File("table.txt"));
			                }
			                break;
			            }
			        }
			    }
			    if (!isDuplicate) {
			        PrintWriter toFile = new PrintWriter(new FileWriter("table.txt", true));
			        toFile.println(StartMenu.name + " " + Game.player.score + " " + String.format("%02d:%02d", Game.min, Game.sec));
			        toFile.close();
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
	             
	    }
	}

	@Override
	public void run() {
	    requestFocus();
	    int fps = 0;
	    double timer = System.currentTimeMillis();
	    long lastTime = System.nanoTime();
	    double targetTick = 60.0;
	    double delta = 0;
	    double ns = 1000000000 / targetTick;

	    while (isRunning) {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;

	        while (delta >= 1) {
	            tick();
	            repaint();
	            fps++;
	            delta--;
	        }
	        if (System.currentTimeMillis() - timer >= 1000) {
	            System.out.println(fps);
	            fps = 0;
	            timer += 1000;
	        }
	    }
	    stop();
	}

	public static void main(String[] args) {
		Game game = new Game();
	    frame = new JFrame();
	    frame.setTitle(Game.TITLE);
	    frame.add(game);
	    frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Game.class.getResource("/map/backgroundbutton_1.jpg")));
	    frame.setResizable(false);
	    frame.pack();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    game.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	    if (STATE == GAME) {
	        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
	            player.right = true;
	        if (e.getKeyCode() == KeyEvent.VK_LEFT)
	            player.left = true;
	        if (e.getKeyCode() == KeyEvent.VK_UP)
	            player.up = true;
	        if (e.getKeyCode() == KeyEvent.VK_DOWN)
	            player.down = true;
	    } else if (STATE == PAUSE_SCREEN) {
	        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	            isEnter = true;
	        }
	        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	        	StartMenu.main(new String[]{});
	        	isRunning = false;
				frame.dispose(); 
	        }
	    } else if (STATE == END) {
	        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	            STATE = PAUSE_SCREEN;
	        }
	    }
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
	        player.right = false;
	    if (e.getKeyCode() == KeyEvent.VK_LEFT)
	        player.left = false;
	    if (e.getKeyCode() == KeyEvent.VK_UP)
	        player.up = false;
	    if (e.getKeyCode() == KeyEvent.VK_DOWN)
	        player.down = false;

	}

	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    render(g);
	}
}
