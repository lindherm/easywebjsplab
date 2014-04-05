/*
 * CaptionButtonUI.java
 *
 * Created on June 9, 2007, 8:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.gerenhua.cardcheck.utils.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * 这个类时CaptionButton组建的UI类。
 * 该UI类是个有状态UI类。
 * 
 * @author William Chen
 * @mail rehte@hotmail.com
 */
public class CaptionButtonUI extends ComponentUI implements MouseMotionListener,
        MouseListener, FocusListener{
    //缺省前景色和缺省字体
    private static final Color DEFAULT_FOREGROUND=new Color(33,93,198);
    private static final Font DEFAULT_FONT=new Font("Dialog", Font.BOLD, 12);
    //定义一些缺省属性的值，比如颜色、字体、尺寸、间隙、缺省图标、虚线框的stroke等等。
    private static final Color LIGHTER=new Color(255,255,255);
    private static final Color DARKER=new Color(198,211,247);
    private static final int TEXT_LEADING_GAP=14;
    private static final int IMAGE_TAILING_GAP=12;
    private static final Color HOVERED_COLOR=new Color(66,142,255);
    private static Stroke DASHED_STROKE=new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {1}, 0);
    private static Icon iconExpanded;
    private static Icon iconFoldered;
    private static Icon hoveredExpanded;
    private static Icon hoveredFoldered;
    static{
        //初始化
        iconExpanded=new ImageIcon(CaptionButtonUI.class.getResource("/com/gerenhua/cardcheck/resources/images/menu/expanded.png"));
        iconFoldered=new ImageIcon(CaptionButtonUI.class.getResource("/com/gerenhua/cardcheck/resources/images/menu/foldered.png"));
        hoveredExpanded=new ImageIcon(CaptionButtonUI.class.getResource("/com/gerenhua/cardcheck/resources/images/menu/hovered_expanded.png"));
        hoveredFoldered=new ImageIcon(CaptionButtonUI.class.getResource("/com/gerenhua/cardcheck/resources/images/menu/hovered_foldered.png"));
    }
    
    //是否画虚线框
    private boolean armed;
    //文字左边间隙
    private int textLeadingGap=TEXT_LEADING_GAP;
    //图像邮编间隙
    private int imageTailingGap=IMAGE_TAILING_GAP;
    //渐变色起始色
    private Color lightColor=LIGHTER;
    //渐变色结束色
    private Color darkColor=DARKER;
    //当前鼠标是否浮动在上面
    private boolean hovered;
    //鼠标浮动在标题上方时标题的颜色
    private Color hoveredColor=HOVERED_COLOR;

    //该UI实例对应的CaptionButton
    protected CaptionButton button;
    
    public static ComponentUI createUI(JComponent c) {
        return new CaptionButtonUI();
    }
    /** Creates a new instance of CaptionButtonUI */
    public CaptionButtonUI() {
    }
    //安装CaptionButton的LAF
    public void installUI(JComponent c) {
        //设置缺省属性
        button=(CaptionButton)c;        
        button.setForeground(DEFAULT_FOREGROUND);
        button.setFont(DEFAULT_FONT);        
        button.setFocusable(true);
        
        //添加事件处理器
        button.addMouseListener(this);
        button.addMouseMotionListener(this);
        button.addFocusListener(this);
    }
    //卸载UI    
    public void uninstallUI(JComponent c) {
        //卸载事件处理器
        button.removeMouseListener(this);
        button.removeMouseMotionListener(this);
        button.removeFocusListener(this);
    }
    //画CaptionButton的背景。继承CaptionButtonUI的子类可以覆盖该方法自定义背景
    protected void paintBackground(Graphics g){
        int w=button.getWidth();
        int h=button.getHeight();
        Graphics2D g2d=(Graphics2D)g;
        GradientPaint gp=new GradientPaint(1,1,lightColor, w-2,1, darkColor);
        g2d.setPaint(gp);
        g2d.fillRect(1,1,w-2,h-1);
        gp=new GradientPaint(2,0,lightColor, w-4,0, darkColor);
        g2d.setPaint(gp);
        g2d.fillRect(2,0,w-4,1);
        g2d.setColor(lightColor);
        g2d.drawLine(0,2,0,h-1);
        g2d.setColor(darkColor);
        g2d.drawLine(w-1,2,w-1,h-1);
    }
    
    public void paint(Graphics g, JComponent c) {
        paintBackground(g);
        paintCaptionText(g);
        paintIcon(g);
        if(armed)
            paintArmed(g);
    }

    //画虚线框，继承CaptionButtonUI的子类可以覆盖该方法自定义焦点获得外观
    protected void paintArmed(Graphics g) {
        Graphics2D g2d=(Graphics2D)g;
        g2d.setColor(Color.black);
        g2d.setStroke(DASHED_STROKE);
        g2d.drawRoundRect(1,1,button.getWidth()-3,button.getHeight()-3,2,2);
    }
    
    //画标题栏展开、关闭图标，继承CaptionButtonUI的子类可以覆盖该方法自定义展开、关闭图标
    protected void paintIcon(Graphics g) {
        Icon icon=null;
        if(hovered)
            icon=button.isExpanded()?hoveredExpanded:hoveredFoldered;
        else
            icon=button.isExpanded()?iconExpanded:iconFoldered;
        int x=button.getWidth()-imageTailingGap-icon.getIconWidth();
        int y=(button.getHeight()-icon.getIconHeight())/2;
        icon.paintIcon(button, g, x, y);
    }
    //画标题栏文字，继承CaptionButtonUI的子类可以覆盖该方法自定义文字的外观
    protected void paintCaptionText(Graphics g) {
        FontMetrics fm=g.getFontMetrics();
        if(button.getText()!=null){
            Color foreground=button.getForeground();
            Color color=hovered?hoveredColor:foreground;
            g.setColor(color);
            int y=(button.getHeight()-fm.getHeight())/2+fm.getAscent();
            g.drawString(button.getText(), textLeadingGap, y);
        }
    }
    //处理按下鼠标事件
    public void mousePressed(MouseEvent e) { 
        //改换外观
        setArmed(true);
        //折叠或者展开
        button.setExpanded(!button.isExpanded());
        //触发选择事件
        ItemEvent evt=new ItemEvent(button,
                button.isExpanded()?0:1,
                button.getText(),
                button.isExpanded()?ItemEvent.SELECTED:ItemEvent.DESELECTED);
        button.fireItemStateChanged(evt);
        //获得焦点
        button.requestFocus();
    }
    //鼠标进入事件、浮动事件
    public void mouseEntered(MouseEvent e) {
        //浮动
        setHovered(true);
        //改鼠标外观
        button.setCursor(
                Cursor.getPredefinedCursor(
                Cursor.HAND_CURSOR));
    }
    //鼠标退出事件
    public void mouseExited(MouseEvent e) {
        //浮动消失
        setHovered(false);
    }    
    void setHovered(boolean b){
        hovered=b;
        button.repaint();
    }
    //焦点消失事件
    public void focusLost(FocusEvent e) {
        setArmed(false);
    }
    void setArmed(boolean b){
        armed=b;
        button.repaint();
    }
    public void mouseDragged(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void focusGained(FocusEvent e) {
    }
}
