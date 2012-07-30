package com.echeloneditor.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import com.echeloneditor.actions.FindAndReplaceAction;
import com.echeloneditor.utils.Config;

/**
 * A Find and Replace Dialog. The dialog will also act as a listener to Document changes so that all highlights are updated if the document is changed.
 * 
 * @author Ayman Al-Sairafi
 */
public class FindAndReplaceDialog extends JDialog implements CaretListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JTextComponent jTextComponent;

	private JButton jBtnNext;
	private JButton jBtnPrev;
	private JButton jBtnReplace;
	private JButton jBtnReplaceAll;
	private JCheckBox jChkIgnoreCase;
	private JCheckBox jChkRegex;
	private JCheckBox jChkWrap;
	private JComboBox jCmbFind;
	private JComboBox jCmbReplace;
	private JLabel jLblFind;
	private JLabel jLblReplace;

	/**
	 * Creates new form FindDialog
	 * 
	 * @param text
	 * @param dsd
	 *            DocumentSerachData
	 */
	public FindAndReplaceDialog(JTextComponent text) {
		super((JFrame) SwingUtilities.getRoot(text));
		this.jTextComponent = text;

		initComponents();
		// textComponent.addCaretListener(this);
		setLocationRelativeTo(text.getRootPane());
		getRootPane().setDefaultButton(jBtnNext);
		// SwingUtils.addEscapeListener(this);
		jBtnReplaceAll.setEnabled(text.isEditable() && text.isEnabled());
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	private void initComponents() {
		jLblFind = new JLabel();
		jBtnNext = new JButton();
		jBtnPrev = new JButton();
		jBtnReplaceAll = new JButton();
		jChkWrap = new JCheckBox();
		jChkRegex = new JCheckBox();
		jChkIgnoreCase = new JCheckBox();
		jLblReplace = new JLabel();
		jCmbReplace = new JComboBox();
		jCmbFind = new JComboBox();
		jBtnReplace = new JButton();

		java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
		setTitle(bundle.getString("ReplaceDialog.title")); // NOI18N
		setResizable(false);

		jLblFind.setLabelFor(jCmbFind);
		jLblFind.setText(bundle.getString("ReplaceDialog.jLblFind.text")); // NOI18N

		jBtnNext.setIcon(new ImageIcon(FindAndReplaceDialog.class.getResource("/com/echeloneditor/resources/images/go-next.png"))); // NOI18N
		jBtnNext.setText(bundle.getString("ReplaceDialog.jBtnNext.text")); // NOI18N
		jBtnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Object object = jCmbFind.getSelectedItem();
				if (object == null || object.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入要查找的字符！", "提示框", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String text = object.toString();
				FindAndReplaceAction.find(jTextComponent, text, true, jChkIgnoreCase.isSelected(), jChkWrap.isSelected());
			}
		});

		jBtnPrev.setIcon(new ImageIcon(FindAndReplaceDialog.class.getResource("/com/echeloneditor/resources/images/go-previous.png"))); // NOI18N
		jBtnPrev.setText(bundle.getString("ReplaceDialog.jBtnPrev.text")); // NOI18N
		jBtnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Object object = jCmbFind.getSelectedItem();
				if (object == null || object.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入要查找的字符！", "提示框", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				String text = jCmbFind.getSelectedItem().toString();

				FindAndReplaceAction.find(jTextComponent, text, false, jChkIgnoreCase.isSelected(), jChkWrap.isSelected());
			}
		});

		jBtnReplaceAll.setIcon(new ImageIcon(FindAndReplaceDialog.class.getResource("/com/echeloneditor/resources/images/edit-find-replace-all.png"))); // NOI18N
		jBtnReplaceAll.setText(bundle.getString("ReplaceDialog.jBtnReplaceAll.text")); // NOI18N
		jBtnReplaceAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Object object = jCmbFind.getSelectedItem();
				Object object2 = jCmbReplace.getSelectedItem();
				if (object == null || object.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入要查找的字符！", "提示框", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				if (object2 == null || object2.equals("")) {
					object2 = "";
				}
				// Create an object defining our search parameters.
				SearchContext context = new SearchContext();

				context.setSearchFor(object.toString());
				context.setReplaceWith(object2.toString());

				context.setMatchCase(jChkIgnoreCase.isSelected());
				context.setRegularExpression(jChkRegex.isSelected());
				context.setWholeWord(jChkWrap.isSelected());
				context.setSearchForward(true);

				SearchEngine.replaceAll((RTextArea) jTextComponent, context);
			}
		});

		jChkWrap.setText(bundle.getString("ReplaceDialog.jChkWrap.text")); // NOI18N
		jChkWrap.setToolTipText(bundle.getString("ReplaceDialog.jChkWrap.toolTipText")); // NOI18N

		jChkRegex.setText(bundle.getString("ReplaceDialog.jChkRegex.text")); // NOI18N

		jChkIgnoreCase.setText(bundle.getString("ReplaceDialog.jChkIgnoreCase.text")); // NOI18N

		jLblReplace.setLabelFor(jCmbReplace);
		jLblReplace.setText(bundle.getString("ReplaceDialog.jLblReplace.text"));

		jCmbReplace.setEditable(true);

		jCmbFind.setEditable(true);

		jBtnReplace.setIcon(new ImageIcon(FindAndReplaceDialog.class.getResource("/com/echeloneditor/resources/images/edit-find-replace.png"))); // NOI18N
		jBtnReplace.setText(bundle.getString("ReplaceDialog.jBtnReplace.text")); // NOI18N
		jBtnReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Object object = jCmbFind.getSelectedItem();
				Object object2 = jCmbReplace.getSelectedItem();
				if (object == null || object.equals("")) {
					JOptionPane.showMessageDialog(null, "请输入要查找的字符！", "提示框", JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				if (object2 == null || object2.equals("")) {
					object2 = "";
				}
				// Create an object defining our search parameters.
				SearchContext context = new SearchContext();

				context.setSearchFor(object.toString());
				context.setReplaceWith(object2.toString());

				context.setMatchCase(jChkIgnoreCase.isSelected());
				context.setRegularExpression(jChkRegex.isSelected());
				context.setWholeWord(jChkWrap.isSelected());
				context.setSearchForward(true);

				SearchEngine.replace((RTextArea) jTextComponent, context);

				FindAndReplaceAction.find(jTextComponent, object.toString(), true, jChkIgnoreCase.isSelected(), jChkWrap.isSelected());
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(jLblFind).addComponent(jLblReplace)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jCmbFind, 0, 289, Short.MAX_VALUE).addComponent(jCmbReplace, Alignment.TRAILING, 0, 289, Short.MAX_VALUE).addComponent(jChkRegex, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE).addComponent(jChkWrap, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE).addComponent(jChkIgnoreCase, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(jBtnReplace, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jBtnNext, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE).addComponent(jBtnPrev, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE).addComponent(jBtnReplaceAll, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jLblFind).addComponent(jCmbFind, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jBtnNext)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jBtnPrev).addComponent(jCmbReplace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(jLblReplace)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jBtnReplace).addComponent(jChkWrap, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)).addGap(3).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(jChkRegex).addComponent(jBtnReplaceAll)).addPreferredGap(ComponentPlacement.RELATED).addComponent(jChkIgnoreCase).addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		// updateHighlights();
	}
}
