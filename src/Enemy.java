import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy {
	protected int x, y, width, height, lastX, lastY;
	protected double velocityX, velocityY, speed;
	protected Player player;
	
	public Enemy(int x, int y, int width, int height, Player player) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.player = player;
		start();
	}
	public void start() {
		speed = 5;
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
		
		// math for following player
	    double angle = Math.atan2((player.y - y), (player.x - x));
	    velocityX = speed * Math.cos(angle);
	    velocityY = speed * Math.sin(angle);

		x += velocityX;
		y += velocityY;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(255, 0, 0));
		g.fillOval(x, y, width, height);
	}
}