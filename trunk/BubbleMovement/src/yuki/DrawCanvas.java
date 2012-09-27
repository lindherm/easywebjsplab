package yuki;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class DrawCanvas extends JPanel {
	private static final long serialVersionUID = -4438102681056694552L;
	
	private List<Bubble> bubbles = new ArrayList<Bubble>();
	private float gravity;
	
	private BumpProcess bumpProcess = new BumpProcess();
	
	public DrawCanvas() {
		new Thread() {
			public void run(){
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setOpaque(false);
					repaint();
				}
			}
		}.start();
	}
	
	public void paintComponent(Graphics g) {
		update(g);
	}
	
	public void update(Graphics g) {
		translate(0.2);
		for (Bubble currentBubble : bubbles) {
			currentBubble.drawBubble(g);
		}
	}
	
	private void translate(double time){
		bumpProcess.ifBumpEachother(bubbles);
		bumpProcess.ifBumpBorder(bubbles, this);
		for (Bubble currentBubble : bubbles) {
			Point2D.Float position = new Point2D.Float();
			
			position.x = (float) (currentBubble.getPosition().x + currentBubble.getVelocity().x * time / 2);
			position.y = (float) (currentBubble.getPosition().y + currentBubble.getVelocity().y * time / 2);
			currentBubble.setPosition(position);
			
			currentBubble.setVelocityY((float) (currentBubble.getVelocity().y + gravity * time));
			
			position.x = (float) (currentBubble.getPosition().x + currentBubble.getVelocity().x * time / 2);
			position.y = (float) (currentBubble.getPosition().y + currentBubble.getVelocity().y * time / 2);
			currentBubble.setPosition(position);
		}
	}
	
	public void addBubble(float radius, float density, Point2D.Float velocity) {
		boolean isIntersect = true;
		while (isIntersect) {
			isIntersect = false;
			float positionX = (float) Math.random() * (this.getWidth() - 2 * radius) + radius;
			float positionY = (float) Math.random() * (this.getHeight() - 2 * radius) + radius;
			for (Bubble currentBubble : bubbles) {
				Point2D.Float currentPosition = currentBubble.getPosition();
				double distance = Math.pow(currentPosition.x - positionX, 2) + Math.pow(currentPosition.y - positionY, 2);
				double sumRadius = Math.pow(currentBubble.getRadius() + radius, 2);
				if (distance <= sumRadius) {
					isIntersect = true;
					break;
				}
			}
			if (!isIntersect) {
				Point2D.Float position = new Point2D.Float(positionX, positionY);
				bubbles.add(new Bubble(radius, density, position, velocity));
			}
		}
	}
	
	public void addBubbles(int num) {
		int added = 0;
		boolean isIntersect;
		while (added < num) {
			isIntersect = false;
			float radius = (float) Math.random() * 30 + 60;
			float positionX = (float) Math.random() * (this.getWidth() - 4 * radius) + 2 * radius;
			float positionY = (float) Math.random() * (this.getHeight() - 4 * radius) + 2 * radius;
			for (Bubble currentBubble : bubbles) {
				Point2D.Float currentPosition = currentBubble.getPosition();
				double distance = Math.pow(currentPosition.x - positionX, 2) + Math.pow(currentPosition.y - positionY, 2);
				double sumRadius = Math.pow(currentBubble.getRadius() + radius, 2);
				if (distance <= sumRadius) {
					isIntersect = true;
					break;
				}
			}
			if (!isIntersect) {
				Point2D.Float position = new Point2D.Float(positionX, positionY);
				float velocityX = (float) (Math.pow(-1, ((int) (Math.random() * 2))) * (Math.random() * 10 + 10));
				float velocityY = (float) (Math.pow(-1, ((int) (Math.random() * 2))) * (Math.random() * 10 + 10));
				Point2D.Float velocity = new Point2D.Float(velocityX, velocityY);
				bubbles.add(new Bubble(radius, 10, position, velocity));
				added++;
			}
		}
	}

	public List<Bubble> getBubbles() {
		return bubbles;
	}
	
	public void emptyBubbles() {
		bubbles = new ArrayList<Bubble>();
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
}
