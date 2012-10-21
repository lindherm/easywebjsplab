package com.EchelonEditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import twaver.TWaverUtil;
import twaver.swing.FullScreenSupport;
import twaver.swing.TableLayout;

public class DemoPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DemoPane() {
		super(new BorderLayout());
		createTime = new Date();
		fullScreenSupport = new FullScreenSupport(this, "right", KeyStroke.getKeyStroke("F5")) {

			protected JPanel createExpandPane() {
				double rows[] = { -2D };
				double columns[] = { -1D, -2D, -2D };
				JPanel panel = new JPanel(new TableLayout(columns, rows));
				JButton button = new JButton(TWaverUtil.getIcon("/demo/resource/images/exportImage.png"));
				button.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						//TWaverUtil.exportImage(_fld0);
					}
				});
				panel.add(button, "1,0");
				panel.add(getRestoreButton(), "2,0");
				return panel;
			}
		};
		fullScreenSupport.setExpand(true);
	}

	public FullScreenSupport getFullScreenSupport() {
		return fullScreenSupport;
	}

	public void setFullScreenSupport(FullScreenSupport fullScreenSupport) {
		this.fullScreenSupport = fullScreenSupport;
	}

	public Date getCreateTime() {
		return createTime;
	}

	private FullScreenSupport fullScreenSupport;
	private Date createTime;
}
