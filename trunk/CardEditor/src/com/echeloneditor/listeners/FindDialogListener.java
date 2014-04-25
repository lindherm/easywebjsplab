package com.echeloneditor.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.SearchDialogSearchContext;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.SearchEngine;

public class FindDialogListener implements ActionListener {
	public RSyntaxTextArea rSyntaxTextArea;

	public FindDialogListener(RSyntaxTextArea rSyntaxTextArea) {
		this.rSyntaxTextArea = rSyntaxTextArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();

		SearchDialogSearchContext context = null;

		Component component = SwingUtilities.getRoot((Component) e.getSource());
		if (component instanceof FindDialog) {
			FindDialog findDialog = (FindDialog) SwingUtilities.getRoot((Component) e.getSource());
			context = findDialog.getSearchContext();
		} else if (component instanceof ReplaceDialog) {
			ReplaceDialog replaceDialog = (ReplaceDialog) SwingUtilities.getRoot((Component) e.getSource());
			context = replaceDialog.getSearchContext();
		}

		if (FindDialog.ACTION_FIND.equals(command)) {
			if (!SearchEngine.find(rSyntaxTextArea, context)) {
				UIManager.getLookAndFeel().provideErrorFeedback(rSyntaxTextArea);
			}
		} else if (ReplaceDialog.ACTION_REPLACE.equals(command)) {
			if (!SearchEngine.replace(rSyntaxTextArea, context)) {
				UIManager.getLookAndFeel().provideErrorFeedback(rSyntaxTextArea);
			}
		} else if (ReplaceDialog.ACTION_REPLACE_ALL.equals(command)) {
			int count = SearchEngine.replaceAll(rSyntaxTextArea, context);
			JOptionPane.showMessageDialog(null, count + " occurrences replaced.");
		}
	}

}
