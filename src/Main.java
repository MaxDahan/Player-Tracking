import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Main extends Canvas implements Runnable, KeyListener {
	protected final static int GWIDTH = 1920, GHEIGHT = 1080;
	protected boolean running;
	protected Thread thread;
	protected Player player;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Wall> walls;
	
	public Main() {
		new Frame(this, GWIDTH, GHEIGHT);
		player = new Player(1920/2, 1080/2, 50, 50);
		enemies = new ArrayList<Enemy>() {{add(new Enemy(0, 0, 50, 50, player));}};
		walls = new ArrayList<Wall>() {{add(new Wall(100, 100, 400, 150, player, enemies));}};
		
		start();
	}
	
	// methods ran every frame
	public void update() {
		player.update();
		for(Enemy enemy : enemies) enemy.update();
		for(Wall wall : walls) wall.update();
		
		// collisions
		Rectangle playerBounds = new Rectangle((int) (player.x), (int) (player.y), (int) (player.width), (int) (player.height));
		//checking walls
		for(Wall wall : walls) {
			if(wall.wallBounds.intersects(playerBounds)) {
				player.x = player.lastX;
				player.y = player.lastY;
			}
			for(Enemy enemy : enemies) {
				Rectangle enemyBounds = new Rectangle((int) (enemy.x), (int) (enemy.y), (int) (enemy.width), (int) (enemy.height));
				if(wall.wallBounds.intersects(enemyBounds)) {
					enemy.x = enemy.lastX;
					enemy.y = enemy.lastY;
				}
			}
		}
		//checking enemies
		for(Enemy enemy : enemies) {
			if(new Rectangle(player.x, player.y, player.width, player.height).intersects(new Rectangle(enemy.x, enemy.y, enemy.width, enemy.height))) {
				System.exit(0);
			}
		}
	}
	public void paint() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		////////////////////////////////////////////////
		
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, GWIDTH, GHEIGHT);
		
		player.draw(g);
		for(Enemy enemy : enemies) enemy.draw(g);
		for(Wall wall : walls) wall.draw(g);
		
		////////////////////////////////////////////////
		g.dispose();
		bs.show();
	}

	// interpreting user input
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) {
			stop();
		} else if(key == KeyEvent.VK_W) {
			player.up = true;
		} else if(key == KeyEvent.VK_A) {
			player.left = true;
		} else if(key == KeyEvent.VK_S) {
			player.down = true;
		} else if(key == KeyEvent.VK_D) {
			player.right = true;
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W) {
			player.up = false;
		} else if(key == KeyEvent.VK_A) {
			player.left = false;
		} else if(key == KeyEvent.VK_S) {
			player.down = false;
		} else if(key == KeyEvent.VK_D) {
			player.right = false;
		}
	}

	// methods for set up
	public void start() {
		addKeyListener(this);
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public void stop() {
		running = false;
		thread.stop();
		System.exit(0);
	}
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			paint();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	public static void main(String[] args) {
		new Main();
	}
	public void keyTyped(KeyEvent e) {}
}
