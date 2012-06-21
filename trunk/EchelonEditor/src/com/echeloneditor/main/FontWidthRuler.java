package com.echeloneditor.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

public class FontWidthRuler extends JPanel {

	private static final long serialVersionUID = 1L;
	// 度量单位初始化
	public static final int PIXEL = 0;
	public static final int MM = 1;
	// 水平或者垂直放置
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	// 水平尺子的高度和垂直尺子的宽度
	public static final int SIZE = 25;

	// 尺子背景颜色
	private final Color BACKGROUND = new Color(238, 238, 255);
	// 刻度的颜色
	// private final Color TICK_COLOR = Color.black;
	private final Color TICK_COLOR = new Color(Integer.parseInt("78", 16), Integer.parseInt("78", 16), Integer.parseInt("78", 16));// 787878
	// 游标的颜色
	private final Color SPIN_COLOR = Color.BLACK;
	// 字体
	private Font FONT = new Font("Courier New", Font.PLAIN, 10);
	// 长刻度和短刻度的尺寸
	private final int LONG_TICK = 14;
	private final int SHORT_TICK = 8;

	private int unit = 10;
	// 数字格式化类
	private NumberFormat nf = NumberFormat.getInstance();
	// 尺子摆向参数
	private int orientation;

	// 精度
	private double precision;

	private Dimension dimension = new Dimension();

	// 是否绘尺子
	public boolean NeedPaint = true;

	// 度量单位默认为0：像素1：毫米
	private int type = 0;

	private ArrayList<Integer> spinList = new ArrayList<Integer>();
	private int spinNumber = 0;

	private final int SIDE = 24;

	public JEditorPane editorPane;

	// 创建尺子的构造函數
	public FontWidthRuler(int orient, double preci, JEditorPane editorPane) {
		orientation = orient;
		precision = preci;
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		setBorder(BorderFactory.createEmptyBorder());

		this.editorPane = editorPane;
	}

	// 添加一个位置游标
	public void addSpin(int location) {
		removeAllSpin();
		// location = location * unit;
		if (orientation == HORIZONTAL) {
			spinList.add(location);
		}
		spinNumber++;
	}

	public void removeAllSpin() {
		spinList.clear();
		spinNumber = 0;
	}

	// 改变尺子的度量单位
	public void changeType() {
		if (type == MM)
			type = PIXEL;
		else
			type = MM;
		repaint();
	}

	public String getType() {
		if (type == MM)
			return "mm";
		else
			return "px";
	}

	public void setType(int type) {
		if (type != PIXEL && type != MM)
			return;
		this.type = type;
		repaint();
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

	public void setPreferredHeight(int ph) {
		dimension.setSize(SIZE, ph);
		setPreferredSize(dimension);
	}

	public void setPreferredWidth(int pw) {
		dimension.setSize(pw, SIZE);
		setPreferredSize(dimension);
	}

	// 绘制标尺
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		FontMetrics fontMetrics = editorPane.getFontMetrics(editorPane.getFont());
		int charWidth = fontMetrics.charWidth('A');
		unit = charWidth;

		if (NeedPaint == false)
			return;

		Rectangle clipBounds = g.getClipBounds();

		Rectangle bounds = new Rectangle(getPreferredSize().width, clipBounds.height);
		// Fill clipping area with dirty brown/orange.
		g.setColor(BACKGROUND);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

		// Do the ruler labels in a small font that's black.
		g.setFont(FONT);
		g.setColor(TICK_COLOR);

		// Some vars we need.
		int i;
		int end = 0;
		int start = 0;
		int tickLength = 0;
		String text = null;

		// Use clipping bounds to calculate first and last tick locations.
		if (orientation == HORIZONTAL) {
			start = bounds.x / unit;
			end = bounds.x + bounds.width;
		} else {
			start = (bounds.y / unit);
			end = bounds.y + bounds.height;
		}

		// Make a special case of 0 to display the number
		// within the rule and draw a units label.
		text = ((type == MM) ? "mm" : "px");
		if (orientation == HORIZONTAL) {
			g.drawString(text, 15, 10);
		} else {
			g.drawString(text, 1, 20);
		}

		// draw tick
		for (i = start + 3; i < end; i += unit) {
			if (i % (unit * 10) == 3) {
				tickLength = LONG_TICK;
				text = (type == PIXEL) ? Integer.toString(i / unit) : nf.format(i * precision);
			} else {
				tickLength = SHORT_TICK;
				text = null;
			}

			if (orientation == HORIZONTAL) {
				g.drawLine(i, SIDE - 1, i, SIDE - tickLength - 1);
				if (text != null)
					g.drawString(text, i + 2, SIDE - LONG_TICK);
			} else {
				g.drawLine(SIDE - 1, i, SIDE - tickLength - 1, i);
				if (text != null)
					g.drawString(text, 2, i + 10);
			}
		}

		// draw side
		if (orientation == HORIZONTAL) {
			g.fillRect(bounds.x, SIDE - 1, bounds.width, 1);
		} else {
			g.fillRect(SIDE - 2, bounds.y, 2, bounds.height);
		}

		// draw spin
		//g.setColor(SPIN_COLOR);
		for (i = 0; i < spinNumber; i++) {
			int location = spinList.get(i);
			if (location <= 0) {
				location = 0;
			}
			location = (location / unit) * unit + 3;
			text = String.valueOf(location / unit);
			g.drawLine(location - unit, 0, location + unit, 0);
			g.drawLine(location, 0, location, SIZE - LONG_TICK-2);
			g.drawString(text, location + 2, SIDE - LONG_TICK);
		}
	}
}
