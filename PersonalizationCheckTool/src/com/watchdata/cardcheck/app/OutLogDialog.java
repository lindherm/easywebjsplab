package com.watchdata.cardcheck.app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

public class OutLogDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public OutLogDialog() {
		setSize(1000, 600);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		RSyntaxTextArea textArea = new RSyntaxTextArea();

		RTextScrollPane sp = new RTextScrollPane(textArea);
		sp.setFoldIndicatorEnabled(true);
		
		Gutter gutter = sp.getGutter();
		gutter.setBookmarkingEnabled(true);
		
		String imagePath="/com/watchdata/cardcheck/resources/images/bookmark.png";
		ImageIcon imageIcon=new ImageIcon(OutLogDialog.class.getResource(imagePath));
		gutter.setBookmarkIcon(imageIcon);

		InputStream in = getClass().getResourceAsStream("/com/watchdata/cardcheck/resources/templates/eclipse.xml");
		try {
			Theme theme = Theme.load(in);
			theme.apply(textArea);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		textArea.setFont(new Font("宋体", Font.PLAIN,14));
		panel.add(sp);
		this.setLocationRelativeTo(null);
	}

}
