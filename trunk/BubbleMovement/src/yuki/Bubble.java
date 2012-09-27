package yuki;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;

public class Bubble {
	private float mass;
	private float radius;//°ë¾¶
	private float density;//ÃÜ¶È
	private Point2D.Float position = new Point2D.Float(0, 0);
	private Point2D.Float velocity = new Point2D.Float(0, 0);
	private Image img;
	
	public Bubble(float radius, float density, Point2D.Float position, Point2D.Float velocity) {
		this.radius = radius;
		this.density = density;
		this.position = position;
		this.velocity = velocity;
		this.mass = (float) (4 * Math.PI * density * radius / 3);
		img = FileUtil.getImage(true, "bubble" + (int) (Math.random() * 6) + ".png");
	}
	
	public void drawBubble(Graphics g){
		g.drawImage(img, (int) (position.x - radius), (int) (position.y - radius), 2 * (int) radius, 2 * (int) radius, null);
	}
	
	public float getMass() {
		return mass;
	}
	
	public void setMass(float mass) {
		this.mass = mass;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}
	
	public Point2D.Float getPosition() {
		return position;
	}
	
	public void setPosition(Point2D.Float position) {
		this.position = position;
	}
	
	public void setPositionX(float positionX) {
		this.position.x = positionX;
	}
	
	public void setPositionY(float positionY) {
		this.position.y = positionY;
	}
	
	public Point2D.Float getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Point2D.Float velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocityX(float velocityX) {
		this.velocity.x = velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocity.y = velocityY;
	}
}
