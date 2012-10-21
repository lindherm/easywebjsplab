package com.EchelonEditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import twaver.Element;
import twaver.ElementAttribute;
import twaver.Layer;
import twaver.TDataBox;
import twaver.TWaverConst;
import twaver.TWaverUtil;
import twaver.table.TElementTable;

public class TablePanel extends JPanel {

	public TablePanel(final TDataBox tableBox, final TDataBox originalBox) {
		tabbedPane = new JTabbedPane();
		listener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String action = e.getActionCommand();
				Element element = tableBox.getLastSelectedElement();
				if (element == null)
					return;
				Layer layer = (Layer) element.getID();
				if ("top".equals(action)) {
					originalBox.getLayerModel().moveToTop(layer);
					tableBox.moveToTop(element);
				} else if ("up".equals(action)) {
					originalBox.getLayerModel().moveToUp(layer);
					tableBox.moveToUp(element);
				} else if ("down".equals(action)) {
					originalBox.getLayerModel().moveToDown(layer);
					tableBox.moveToDown(element);
				} else if ("bottom".equals(action)) {
					originalBox.getLayerModel().moveToBottom(layer);
					tableBox.moveToBottom(element);
				}
			}
		};
		this.tableBox = tableBox;
		this.originalBox = originalBox;
		initGUI();
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		JPanel customDrawPanel = getCustomDrawPanel();
		tabbedPane.addTab("CustomDraw", customDrawPanel);
		JPanel layerPanel = getLayerPanel();
		tabbedPane.addTab("    Layer   ", layerPanel);
		add(tabbedPane, "Center");
	}

	private JPanel getLayerPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		java.util.List list = new ArrayList();
		list.add(createAttribute("id", TWaverUtil.getString("layer.id"), null, false));
		list.add(createAttribute("name", TWaverUtil.getString("layer.name"), java.lang.String.class, false));
		list.add(createAttribute("visible", TWaverUtil.getString("layer.visible"), java.lang.Boolean.class, true));
		list.add(createAttribute("selectable", TWaverUtil.getString("layer.selectable"), java.lang.Boolean.class, true));
		list.add(createAttribute("movable", TWaverUtil.getString("layer.movable"), java.lang.Boolean.class, true));
		list.add(createAttribute("resizable", TWaverUtil.getString("layer.resizable"), java.lang.Boolean.class, true));
		list.add(createAttribute("alpha", TWaverUtil.getString("layer.alpha"), java.lang.Float.class, true));
		list.add(createAttribute("description", TWaverUtil.getString("layer.description"), java.lang.String.class, true));
		TElementTable table = getTable(list);
		panel.add(getContrlPane(table), "North");
		panel.add(new JScrollPane(table), "Center");
		return panel;
	}

	private JPanel getCustomDrawPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		java.util.List list = new ArrayList();
		String prefix = "twaver.BaseElement.";
		list.add(createAttribute("custom.draw.shape.factory", TWaverUtil.getString(prefix + "custom.draw.shape.factory"), null, "twaver.table.editor.EnumTypeEditor@twaver.shape|false", "twaver.table.renderer.EnumTypeRenderer@twaver.shape", false));
		list.add(createAttribute("custom.draw.gradient.factory", TWaverUtil.getString(prefix + "custom.draw.gradient.factory"), null, "twaver.table.editor.EnumTypeEditor@twaver.gradient|false", "twaver.table.renderer.EnumTypeRenderer@twaver.gradient", true));
		list.add(createAttribute("custom.draw.fill.color", TWaverUtil.getString(prefix + "custom.draw.fill.color"), java.awt.Color.class, true));
		list.add(createAttribute("custom.draw.gradient", TWaverUtil.getString(prefix + "custom.draw.gradient"), java.lang.Boolean.class, true));
		list.add(createAttribute("custom.draw.gradient.color", TWaverUtil.getString(prefix + "custom.draw.gradient.color"), java.awt.Color.class, true));
		list.add(createAttribute("custom.draw.outline.color", TWaverUtil.getString(prefix + "custom.draw.outline.color"), java.awt.Color.class, true));
		list.add(createAttribute("custom.draw.outline.stroke", TWaverUtil.getString(prefix + "custom.draw.outline.stroke"), java.awt.Stroke.class, "twaver.table.editor.StrokeEditor", "twaver.table.renderer.StrokeRenderer", true));
		TElementTable table = getTable(list);
		panel.add(getContrlPane(table), "North");
		panel.add(new JScrollPane(table), "Center");
		return panel;
	}

	private TElementTable getTable(java.util.List list) {
		TElementTable table = new TElementTable(tableBox);
		table.getSelectionModel().setSelectionMode(0);
		table.setIteratorByHiberarchy(true);
		table.setElementClass(twaver.Node.class);
		table.registerElementClassAttributes(twaver.Node.class, list);
		table.setAutoResizeMode(4);
		return table;
	}

	private ElementAttribute createAttribute(String key, String displayName, Class clazz, boolean editable) {
		return createAttribute(key, displayName, clazz, null, null, editable);
	}

	private ElementAttribute createAttribute(String key, String displayName, Class clazz, String editor, String renderer, boolean editable) {
		ElementAttribute attribute = new ElementAttribute();
		attribute.setClientPropertyKey(key);
		attribute.setDisplayName(displayName);
		attribute.setEditable(editable);
		attribute.setJavaClass(clazz);
		if (editor != null)
			attribute.setEditorClass(editor);
		if (renderer != null)
			attribute.setRendererClass(renderer);
		return attribute;
	}

	public JToolBar getContrlPane(final TElementTable table) {
		JToolBar toolbar = new JToolBar();
		JButton topButton = createButton("top.png", "top");
		JButton upButton = createButton("up.png", "up");
		JButton downButton = createButton("down.png", "down");
		JButton bottomButton = createButton("bottom.png", "bottom");
		final JCheckBox editableCheckBox = new JCheckBox(TWaverUtil.getString("EDITABLE"), false);
		toolbar.setMinimumSize(TWaverConst.EMPTY_DIMENSION);
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		toolbar.addSeparator();
		toolbar.add(topButton);
		toolbar.add(upButton);
		toolbar.add(downButton);
		toolbar.add(bottomButton);
		editableCheckBox.setSelected(table.isEditable());
		editableCheckBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				table.setEditable(editableCheckBox.isSelected());
			}
		});
		editableCheckBox.setFocusPainted(false);
		toolbar.addSeparator();
		toolbar.add(editableCheckBox);
		return toolbar;
	}

	protected JButton createButton(String image, String actionCommand) {
		javax.swing.Icon icon = TWaverUtil.getIcon("/resource/image/table/" + image);
		JButton button = new JButton(icon);
		button.setMargin(TWaverConst.NONE_INSETS);
		button.setActionCommand(actionCommand);
		button.addActionListener(listener);
		return button;
	}

	private TDataBox tableBox;
	private TDataBox originalBox;
	private JTabbedPane tabbedPane;
	private ActionListener listener;

}
