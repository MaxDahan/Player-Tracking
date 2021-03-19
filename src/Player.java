import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player {
	protected int x, y, width, height, lastX, lastY;
	protected double velocityX, velocityY, speed;
	protected boolean up, down, left, right;
	
	public Player(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		start();
	}
	public void start() {
		up = false;
		down = false;
		left = false;
		right = false;
		
		speed = 7.0;
		velocityX = 0.0;
		velocityY = 0.0;
	}
	
	public void update() {
		lastX = x;
		lastY = y;
		
		// colliding with edge of screen
		if(x <= 0) {
			velocityX = 0;
			x = 0;
		} else if(x >= Main.GWIDTH) {
			velocityX = 0;
			x = Main.GWIDTH - width;
		}
		if(y <= 0) {
			velocityY = 0;
			y = 0;
		} else if(y >= Main.GHEIGHT) {
			velocityY = 0;
			y = Main.GHEIGHT - height;
		}
		
		// movement
		if(up) {
			velocityY = -speed;
		} else if(down) {
			velocityY = speed;
		} else velocityY = 0;
		if(left) {
			velocityX = -speed;
		} else if(right) {
			velocityX = speed;
		} else velocityX = 0;
		
		x += velocityX;
		y += velocityY;
	}
	public void draw(Graphics2D g) {
		g.setColor(new Color(0, 0, 255));
		g.fillOval(x, y, width, height);
	}
}
