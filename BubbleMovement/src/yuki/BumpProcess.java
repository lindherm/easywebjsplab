package yuki;

import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;

/**
 * 用于处理泡泡的碰撞的工具类，包括泡泡与边界碰撞、泡泡相互碰撞的判定和处理。
 * @author 情却然
 */
public class BumpProcess {
	
	/**
	 * 判断数组中每一个泡泡是否到达边界，若到达边界，则改变泡泡速度。
	 * @param bubbles   The list of bubble
	 * @param canvas  The canvas which bubbles on
	 */
	public void ifBumpBorder(List<Bubble> bubbles, JPanel canvas) {
		for (Bubble currentBubble : bubbles) {
			
			//判断泡泡在x正方向是否到达或超过边界，若到达或超过边界，则改变x方向速度
			if (currentBubble.getPosition().x >= canvas.getSize().width - currentBubble.getRadius()) {
				currentBubble.setVelocityX(-Math.abs(currentBubble.getVelocity().x));
			}
			
			//判断泡泡在x负方向是否到达或超过边界，若到达或超过边界，则改变x方向速度
			if (currentBubble.getPosition().x <= currentBubble.getRadius()){
				currentBubble.setVelocityX(Math.abs(currentBubble.getVelocity().x));
			}
			
			//判断泡泡在y正方向是否到达或超过边界，若到达或超过边界，则改变y方向速度
			if(currentBubble.getPosition().y >= canvas.getSize().height - currentBubble.getRadius()) {
				currentBubble.setVelocityY(-Math.abs(currentBubble.getVelocity().y));
			}
			
			//判断泡泡在y负方向是否到达或超过边界，若到达或超过边界，则改变y方向速度
			if(currentBubble.getPosition().y <= currentBubble.getRadius()) { 
				currentBubble.setVelocityY(Math.abs(currentBubble.getVelocity().y));
			}
		}
	}
	
	/**
	 * 判断数组中的任意两个泡泡是否发生碰撞，若发生碰撞，则改变这两泡泡的速度
	 * @param bubbles   The list of bubble
	 */
	public void ifBumpEachother(List<Bubble> bubbles) {
		for (int i = 0; i < bubbles.size(); i++) {
			for (int j = 1; j < bubbles.size() && j != i; j++) {
				
				//获取两泡泡的圆心距离和两泡泡的半径之和
				Point2D.Float position1 = bubbles.get(i).getPosition();
				Point2D.Float position2 = bubbles.get(j).getPosition();
				double distance = Math.pow(position1.x - position2.x, 2) + Math.pow(position1.y - position2.y, 2);
				double sumRadius = Math.pow(bubbles.get(i).getRadius() + bubbles.get(j).getRadius(), 2);
				
				//若两泡泡的圆心距离小于等于两泡泡的半径之和，则两泡泡发生碰撞，处理两泡泡的速度
				if (distance <= sumRadius) {
					bumpedEachother(bubbles.get(i), bubbles.get(j));
				}
			}
		}
	}
	
	/**
	 * 处理碰撞后两泡泡的速度
	 * @param bubble1   one of the bubbles which bumped eachother
	 * @param bubble2   one of the bubbles which bumped eachother
	 */
	private void bumpedEachother(Bubble bubble1, Bubble bubble2) {

		//获取两泡泡的质量
		float mass1 = bubble1.getMass();
		float mass2 = bubble2.getMass();
		
		//获取两泡泡的位置
		Point2D.Float position1 = bubble1.getPosition();
		Point2D.Float position2 = bubble2.getPosition();
		
		//获取两泡泡的速度
		Point2D.Float velocity1 = bubble1.getVelocity();
		Point2D.Float velocity2 = bubble2.getVelocity();
		
		//两泡泡之间的相对位置
		Point2D.Float relativePosition = new Point2D.Float(position1.x - position2.x, position1.y - position2.y);
		
		//两泡泡之间的相对速度
		Point2D.Float relativeVelocity = new Point2D.Float(velocity1.x - velocity2.x, velocity1.y - velocity2.y);
		
		//判断泡泡的是否相互靠近，若相互靠近，则改变泡泡速度，若相互背离，则不作处理，避免两泡泡黏在一起。
		if (relativePosition.x * relativeVelocity.x + relativePosition.y * relativeVelocity.y < 0) {
			//定义碰撞后两泡泡的速度
			Point2D.Float velocity_1 = new Point2D.Float();
			Point2D.Float velocity_2 = new Point2D.Float();
			
			//定义两泡泡在垂直于两球心连线的方向的速度
			Point2D.Float velocity1_0 = new Point2D.Float();
			Point2D.Float velocity2_0 = new Point2D.Float();
			
			//定义碰撞前两泡泡在两球心连线方向的速度
			Point2D.Float velocity1_1 = new Point2D.Float();
			Point2D.Float velocity2_1 = new Point2D.Float();
			
			//定义碰撞后两泡泡在两球心连线方向的速度
			Point2D.Float velocity_1_1 = new Point2D.Float();
			Point2D.Float velocity_2_1 = new Point2D.Float();
			
			//定义两球心连线的斜率为k
			float k = (position1.y - position2.y) / (position1.x - position2.x);
			
			//计算碰撞前两泡泡在两球心连线方向的速度
			velocity1_1.x = (velocity1.x + k * velocity1.y) / (1 + k * k);
			velocity1_1.y = k * velocity1_1.x;
			velocity2_1.x = (velocity2.x + k * velocity2.y) / (1 + k * k);
			velocity2_1.y = k * velocity2_1.x;
			
			//计算两泡泡在垂直于两球心连线的方向的速度
			velocity1_0.x = velocity1.x - velocity1_1.x;
			velocity1_0.y = velocity1.y - velocity1_1.y;
			velocity2_0.x = velocity2.x - velocity2_1.x;
			velocity2_0.y = velocity2.y - velocity2_1.y;
			
			//计算碰撞后两泡泡在两球心连线方向的速度
			velocity_1_1.x = (2 * mass2 * velocity2_1.x + (mass1 - mass2) * velocity1_1.x) / (mass1 + mass2);
			velocity_1_1.y = (2 * mass2 * velocity2_1.y + (mass1 - mass2) * velocity1_1.y) / (mass1 + mass2);
			velocity_2_1.x = (2 * mass1 * velocity1_1.x + (mass2 - mass1) * velocity2_1.x) / (mass1 + mass2);
			velocity_2_1.y = (2 * mass1 * velocity1_1.y + (mass2 - mass1) * velocity2_1.y) / (mass1 + mass2);
			
			//计算碰撞后两泡泡的速度
			velocity_1.x = velocity1_0.x + velocity_1_1.x;
			velocity_1.y = velocity1_0.y + velocity_1_1.y;
			velocity_2.x = velocity2_0.x + velocity_2_1.x;
			velocity_2.y = velocity2_0.y + velocity_2_1.y;
			
			//重设两泡泡速度
			bubble1.setVelocity(velocity_1);
			bubble2.setVelocity(velocity_2);
		}
	}
}
