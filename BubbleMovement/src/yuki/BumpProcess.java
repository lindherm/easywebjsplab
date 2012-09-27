package yuki;

import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;

/**
 * ���ڴ������ݵ���ײ�Ĺ����࣬����������߽���ײ�������໥��ײ���ж��ʹ���
 * @author ��ȴȻ
 */
public class BumpProcess {
	
	/**
	 * �ж�������ÿһ�������Ƿ񵽴�߽磬������߽磬��ı������ٶȡ�
	 * @param bubbles   The list of bubble
	 * @param canvas  The canvas which bubbles on
	 */
	public void ifBumpBorder(List<Bubble> bubbles, JPanel canvas) {
		for (Bubble currentBubble : bubbles) {
			
			//�ж�������x�������Ƿ񵽴�򳬹��߽磬������򳬹��߽磬��ı�x�����ٶ�
			if (currentBubble.getPosition().x >= canvas.getSize().width - currentBubble.getRadius()) {
				currentBubble.setVelocityX(-Math.abs(currentBubble.getVelocity().x));
			}
			
			//�ж�������x�������Ƿ񵽴�򳬹��߽磬������򳬹��߽磬��ı�x�����ٶ�
			if (currentBubble.getPosition().x <= currentBubble.getRadius()){
				currentBubble.setVelocityX(Math.abs(currentBubble.getVelocity().x));
			}
			
			//�ж�������y�������Ƿ񵽴�򳬹��߽磬������򳬹��߽磬��ı�y�����ٶ�
			if(currentBubble.getPosition().y >= canvas.getSize().height - currentBubble.getRadius()) {
				currentBubble.setVelocityY(-Math.abs(currentBubble.getVelocity().y));
			}
			
			//�ж�������y�������Ƿ񵽴�򳬹��߽磬������򳬹��߽磬��ı�y�����ٶ�
			if(currentBubble.getPosition().y <= currentBubble.getRadius()) { 
				currentBubble.setVelocityY(Math.abs(currentBubble.getVelocity().y));
			}
		}
	}
	
	/**
	 * �ж������е��������������Ƿ�����ײ����������ײ����ı��������ݵ��ٶ�
	 * @param bubbles   The list of bubble
	 */
	public void ifBumpEachother(List<Bubble> bubbles) {
		for (int i = 0; i < bubbles.size(); i++) {
			for (int j = 1; j < bubbles.size() && j != i; j++) {
				
				//��ȡ�����ݵ�Բ�ľ���������ݵİ뾶֮��
				Point2D.Float position1 = bubbles.get(i).getPosition();
				Point2D.Float position2 = bubbles.get(j).getPosition();
				double distance = Math.pow(position1.x - position2.x, 2) + Math.pow(position1.y - position2.y, 2);
				double sumRadius = Math.pow(bubbles.get(i).getRadius() + bubbles.get(j).getRadius(), 2);
				
				//�������ݵ�Բ�ľ���С�ڵ��������ݵİ뾶֮�ͣ��������ݷ�����ײ�����������ݵ��ٶ�
				if (distance <= sumRadius) {
					bumpedEachother(bubbles.get(i), bubbles.get(j));
				}
			}
		}
	}
	
	/**
	 * ������ײ�������ݵ��ٶ�
	 * @param bubble1   one of the bubbles which bumped eachother
	 * @param bubble2   one of the bubbles which bumped eachother
	 */
	private void bumpedEachother(Bubble bubble1, Bubble bubble2) {

		//��ȡ�����ݵ�����
		float mass1 = bubble1.getMass();
		float mass2 = bubble2.getMass();
		
		//��ȡ�����ݵ�λ��
		Point2D.Float position1 = bubble1.getPosition();
		Point2D.Float position2 = bubble2.getPosition();
		
		//��ȡ�����ݵ��ٶ�
		Point2D.Float velocity1 = bubble1.getVelocity();
		Point2D.Float velocity2 = bubble2.getVelocity();
		
		//������֮������λ��
		Point2D.Float relativePosition = new Point2D.Float(position1.x - position2.x, position1.y - position2.y);
		
		//������֮�������ٶ�
		Point2D.Float relativeVelocity = new Point2D.Float(velocity1.x - velocity2.x, velocity1.y - velocity2.y);
		
		//�ж����ݵ��Ƿ��໥���������໥��������ı������ٶȣ����໥���룬���������������������һ��
		if (relativePosition.x * relativeVelocity.x + relativePosition.y * relativeVelocity.y < 0) {
			//������ײ�������ݵ��ٶ�
			Point2D.Float velocity_1 = new Point2D.Float();
			Point2D.Float velocity_2 = new Point2D.Float();
			
			//�����������ڴ�ֱ�����������ߵķ�����ٶ�
			Point2D.Float velocity1_0 = new Point2D.Float();
			Point2D.Float velocity2_0 = new Point2D.Float();
			
			//������ײǰ�����������������߷�����ٶ�
			Point2D.Float velocity1_1 = new Point2D.Float();
			Point2D.Float velocity2_1 = new Point2D.Float();
			
			//������ײ�������������������߷�����ٶ�
			Point2D.Float velocity_1_1 = new Point2D.Float();
			Point2D.Float velocity_2_1 = new Point2D.Float();
			
			//�������������ߵ�б��Ϊk
			float k = (position1.y - position2.y) / (position1.x - position2.x);
			
			//������ײǰ�����������������߷�����ٶ�
			velocity1_1.x = (velocity1.x + k * velocity1.y) / (1 + k * k);
			velocity1_1.y = k * velocity1_1.x;
			velocity2_1.x = (velocity2.x + k * velocity2.y) / (1 + k * k);
			velocity2_1.y = k * velocity2_1.x;
			
			//�����������ڴ�ֱ�����������ߵķ�����ٶ�
			velocity1_0.x = velocity1.x - velocity1_1.x;
			velocity1_0.y = velocity1.y - velocity1_1.y;
			velocity2_0.x = velocity2.x - velocity2_1.x;
			velocity2_0.y = velocity2.y - velocity2_1.y;
			
			//������ײ�������������������߷�����ٶ�
			velocity_1_1.x = (2 * mass2 * velocity2_1.x + (mass1 - mass2) * velocity1_1.x) / (mass1 + mass2);
			velocity_1_1.y = (2 * mass2 * velocity2_1.y + (mass1 - mass2) * velocity1_1.y) / (mass1 + mass2);
			velocity_2_1.x = (2 * mass1 * velocity1_1.x + (mass2 - mass1) * velocity2_1.x) / (mass1 + mass2);
			velocity_2_1.y = (2 * mass1 * velocity1_1.y + (mass2 - mass1) * velocity2_1.y) / (mass1 + mass2);
			
			//������ײ�������ݵ��ٶ�
			velocity_1.x = velocity1_0.x + velocity_1_1.x;
			velocity_1.y = velocity1_0.y + velocity_1_1.y;
			velocity_2.x = velocity2_0.x + velocity_2_1.x;
			velocity_2.y = velocity2_0.y + velocity_2_1.y;
			
			//�����������ٶ�
			bubble1.setVelocity(velocity_1);
			bubble2.setVelocity(velocity_2);
		}
	}
}
