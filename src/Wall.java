import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Wall {
	protected int x, y, width, height;
	protected Player player;
	protected ArrayList<Enemy> enemies;
	protected Rectangle wallBounds;
	
	public Wall(int x, int y, int width, int height, Player player, ArrayList<Enemy> enemies) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.player = player;
		this.enemies = enemies;
		start();
	}
	public void start() {
		wallBounds = new Rectangle(x, y, width, height);
	}
	
	public void update() {
	
	}
	public void draw(Graphics2D g) {
		g.setColor(new Color(0, 0, 0));
		g.fillRect(x, y, width, height);
	}
}
