package com.watchdata.cardcheck.app;

import java.awt.*;
import javax.swing.*;

/**
 * @title MainToolBar.java
 * @description 工具栏
 * @author pei.li 2012-3-15
 * @version 1.0.0
 * @modify
 * @copyright watchdata
 */
public class MainToolBar extends JToolBar {

	private static final long serialVersionUID = 2984892748128712241L;
	private ImageIcon newImage = null;
	private ImageIcon openImage = null;
	private ToolButton newButton = null;
	private ToolButton openButton = null;
	String proPath = System.getProperty("user.dir");

	// private JButton defaultBorderButton = null;

	public MainToolBar() {
		super();
		setFloatable(false);
		setMargin(new Insets(2, 0, 2, 0));
		newImage = ImageHelper.loadImage(proPath + "/resources/images/new.png");
		newButton = new ToolButton(newImage);
		newButton.setToolTipText("Opens a new document (just a demo).");
		openImage = ImageHelper.loadImage(proPath + "/resources/images/open.png");
		openButton = new ToolButton(openImage);
		openButton
				.setToolTipText("Opens the file chooser dialog to open an existing document (just a demo).");
		/*
		 * saveImage = ImageHelper.loadImage("save.png"); saveButton = new
		 * ToolButton(saveImage);
		 * saveButton.setToolTipText("Saves the active document (just a demo)."
		 * ); cutImage = ImageHelper.loadImage("cut.png"); cutButton = new
		 * ToolButton(cutImage);cutButton.setToolTipText(
		 * "Cuts the marked contens out of your document (just a demo).");
		 * copyImage = ImageHelper.loadImage("copy.png"); copyButton = new
		 * ToogleToolButton(copyImage);copyButton.setToolTipText(
		 * "Copies the marked contens out of your document into the clipboard (just a demo)."
		 * ); pasteImage = ImageHelper.loadImage("paste.png"); pasteButton = new
		 * ToolButton(pasteImage);pasteButton.setToolTipText(
		 * "Inserts the contens of the clipboard into your document (just a demo)."
		 * ); pasteButton.setEnabled(false); undoImage =
		 * ImageHelper.loadImage("undo.png"); undoButton = new
		 * ToolButton(undoImage);
		 * undoButton.setToolTipText("Undos the last action (just a demo).");
		 * redoImage = ImageHelper.loadImage("redo.png"); redoButton = new
		 * ToolButton(redoImage);
		 * redoButton.setToolTipText("Redos the last action (just a demo).");
		 */

		// defaultBorderButton = new JButton("DefaultBorder");
		// defaultBorderButton.setFocusable(false);
		// defaultBorderButton.putClientProperty("paintToolBarBorder",
		// Boolean.FALSE);

		add(newButton);
		add(openButton);

		// addSeparator();

	}

	private class ToolButton extends JButton {

		private static final long serialVersionUID = 5279663293362613446L;

		public ToolButton(Icon icon) {
			super(icon);
			setMargin(new Insets(4, 4, 4, 4));
		}

		public boolean isFocusTraversable() {
			return false;
		}

		public void requestFocus() {
		}
	}

	@SuppressWarnings("unused")
	private class ToogleToolButton extends JToggleButton {

		private static final long serialVersionUID = -919172504966497674L;

		public ToogleToolButton(Icon icon) {
			super(icon);
			setMargin(new Insets(4, 4, 4, 4));
		}

		public boolean isFocusTraversable() {
			return false;
		}

		public void requestFocus() {
		}
	}
}
