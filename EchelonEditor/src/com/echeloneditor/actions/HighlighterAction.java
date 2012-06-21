package com.echeloneditor.actions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

import jsyntaxpane.util.Configuration;

public class HighlighterAction {
	private static final String LINE_HIGHLIGHT = "linehilight"; // NOI18N - used as clientproperty
	public static Configuration config=null;
	private static Color col = new Color(232, 242, 254); // Color used for highlighting the line
	// Installs CurrentLineHilighter for the given JTextComponent
	public static void install(JTextComponent c) {
		try {
			Object obj = c.getHighlighter().addHighlight(0, 0, painter);
			c.putClientProperty(LINE_HIGHLIGHT, obj);
			c.addCaretListener(caretListener);
			c.addMouseListener(mouseListener);
		} catch (BadLocationException ex) {
		}
	}

	// Uninstalls CurrentLineHighligher for the given JTextComponent
	public static void uninstall(JTextComponent c) {
		c.putClientProperty(LINE_HIGHLIGHT, null);
		c.removeCaretListener(caretListener);
		c.removeMouseListener(mouseListener);
	}

	private static CaretListener caretListener = new CaretListener() {
		public void caretUpdate(CaretEvent e) {
			// todo: paint only interested region
			((JTextComponent) e.getSource()).repaint();
		}
	};

	private static MouseAdapter mouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent me) {
			Object obj = ((JTextComponent) me.getSource()).getClientProperty(LINE_HIGHLIGHT);
			((JTextComponent) me.getSource()).getHighlighter().removeHighlight(obj);
			// todo: paint only interested region
			((JTextComponent) me.getSource()).repaint();
		}

		public void mouseReleased(MouseEvent me) {
			try {
				JTextComponent c = ((JTextComponent) me.getSource());
				Object obj = c.getHighlighter().addHighlight(0, 0, painter);
				c.putClientProperty(LINE_HIGHLIGHT, obj);
			} catch (BadLocationException ex) {
			}
		}
	};

	private static Highlighter.HighlightPainter painter = new Highlighter.HighlightPainter() {
		public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
			try {
				if (c.getSelectionStart() == c.getSelectionEnd()) { // if no selection
					Rectangle r = c.modelToView(c.getCaretPosition());
					g.setColor(col);
					g.fillRect(0, r.y, c.getWidth(), r.height);
				}
			} catch (BadLocationException ignore) {
			}
		}
	};
}
