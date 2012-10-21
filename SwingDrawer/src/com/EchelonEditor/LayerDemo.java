package com.EchelonEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import twaver.DataBoxQuickFinder;
import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.Element;
import twaver.EnumType;
import twaver.EnumTypeManager;
import twaver.Layer;
import twaver.Node;
import twaver.ResizableFilter;
import twaver.ResizableNode;
import twaver.TDataBox;
import twaver.TWaverUtil;
import twaver.network.TNetwork;

public class LayerDemo extends DemoPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LayerDemo() {
		box = new TDataBox();
		network = new TNetwork(box);
		tableBox = new TDataBox();
		boxSelectionChange = true;
		tableBoxSelectionChange = true;
		layerFinder = box.createJavaBeanFinder("layerID");
		initBox();
		convertBoxToTableBox();
		initGUI();
		initNetwork();
		initBoxListener();
	}

	private void initBox() {
		Object gradientObj[] = EnumTypeManager.getInstance().getEnumTypes("twaver.gradient");
		Object shapeObj[] = EnumTypeManager.getInstance().getEnumTypes("twaver.shape");
		int size = shapeObj.length;
		for (int i = 0; i < size; i++) {
			EnumType shape = (EnumType) shapeObj[i];
			EnumType gradient = (EnumType) gradientObj[(int) ((double) gradientObj.length * Math.random())];
			String layerId = shape.getValue().toString();
			Layer layer = new Layer(layerId);
			box.getLayerModel().addLayer(layer);
			layer.setAlpha((float) ((double) (int) (Math.random() * 4D) / 10D + 0.69999999999999996D));
			layer.setName(shape.getDisplayName());
			layer.setMovable(true);
			Color gradientColor = Color.WHITE;
			Color fillColor = TWaverUtil.getRandomColor();
			boolean isGradient = true;
			String stroke = "solid.thinnest";
			if (i % size == 0)
				stroke = "zigzag.narrowest";
			else if (i % size == 4)
				stroke = "square.thinnest";
			else if (i % size == 7)
				stroke = "dot.thinnest";
			else if (i % size == 11)
				stroke = "solid.2";
			else if (i % size == 13)
				stroke = "dash.thinnest";
			else if (i % size == 16)
				stroke = "dash.dot.dot.thinnest";
			else if (i % size == 19)
				stroke = "dash.dot.thinnest";
			Color outlineColor = TWaverUtil.getRandomColor();
			layer.putClientProperty("custom.draw.shape.factory", shape.getValue());
			layer.putClientProperty("custom.draw.gradient.factory", gradient.getValue());
			layer.putClientProperty("custom.draw.fill.color", fillColor);
			layer.putClientProperty("custom.draw.gradient", new Boolean(isGradient));
			layer.putClientProperty("custom.draw.gradient.color", gradientColor);
			layer.putClientProperty("custom.draw.outline.stroke", stroke);
			layer.putClientProperty("custom.draw.outline.color", outlineColor);
			for (int j = 0; j < 4; j++) {
				ResizableNode element = new ResizableNode();
				element.setSize(80, 80);
				element.setLayerID(layerId);
				element.putCustomDraw(true);
				element.putCustomDrawShapeFactory(((Integer) (Integer) shape.getValue()).intValue());
				element.putCustomDrawGradientFactory(((Integer) (Integer) gradient.getValue()).intValue());
				element.putCustomDrawFillColor(fillColor);
				element.putCustomDrawGradient(isGradient);
				element.putCustomDrawGradientColor(gradientColor);
				element.putCustomDrawOutlineColor(outlineColor);
				element.setLocation(Math.random() * 800D, Math.random() * 500D);
				element.putCustomDrawOutlineStroke(stroke);
				box.addElement(element);
			}

		}

	}

	private void initGUI() {
		setLayout(new BorderLayout());
		add(network, "Center");
		JSplitPane pane = new JSplitPane(0);
		pane.setDividerSize(3);
		pane.setTopComponent(network);
		pane.setBottomComponent(getControlPanel());
		pane.setDividerLocation(450);
		add(pane, "Center");
	}

	private JPanel getControlPanel() {
		TablePanel layerManagerPane = new TablePanel(tableBox, box);
		return layerManagerPane;
	}

	private void initNetwork() {
		network.setResizableFilter(new ResizableFilter() {

			public boolean isResizable(Element element) {
				return true;
			}
		});
	}

	private void initBoxListener() {
		box.getSelectionModel().addDataBoxSelectionListener(new DataBoxSelectionListener() {

			public void selectionChanged(DataBoxSelectionEvent e) {
				if (boxSelectionChange && tableBoxSelectionChange) {
					boxSelectionChange = false;
					
					java.util.List list = box.getSelectionModel().getAllSelectedElement();
					int size = list.size();
					java.util.List selectList = new ArrayList();
					for (int i = 0; i < size; i++) {
						Element element = (Element) list.get(i);
						Layer layer = box.getLayerModel().getLayerByID(element.getLayerID());
						selectList.add(tableBox.getElementByID(layer));
					}

					tableBox.getSelectionModel().setSelection(selectList);
					boxSelectionChange = true;
				}
			}

		});
		tableBox.getSelectionModel().addDataBoxSelectionListener(new DataBoxSelectionListener() {

			public void selectionChanged(DataBoxSelectionEvent e) {
				if (tableBoxSelectionChange && boxSelectionChange) {
					tableBoxSelectionChange = false;
					java.util.List list = tableBox.getSelectionModel().getAllSelectedElement();
					int size = list.size();
					java.util.List selectList = new ArrayList();
					for (int i = 0; i < size; i++) {
						Element element = (Element) list.get(i);
						Layer layer = (Layer) element.getID();
						java.util.List findList = layerFinder.find(layer.getID());
						if (findList != null)
							selectList.addAll(findList);
					}

					box.getSelectionModel().setSelection(selectList);
					tableBoxSelectionChange = true;
				}
			}
		});
		tableBox.addElementPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				Element element = (Element) evt.getSource();
				Layer layer = (Layer) element.getID();
				String name = TWaverUtil.getPropertyName(evt);
				Object newValue = evt.getNewValue();
				java.util.List findList = layerFinder.find(layer.getID());
				int size = findList.size();
				if ("name".equals(name))
					layer.setName(newValue.toString());
				else if ("visible".equals(name))
					layer.setVisible(((Boolean) (Boolean) newValue).booleanValue());
				else if ("selectable".equals(name))
					layer.setSelectable(((Boolean) (Boolean) newValue).booleanValue());
				else if ("movable".equals(name))
					layer.setMovable(((Boolean) (Boolean) newValue).booleanValue());
				else if ("resizable".equals(name))
					layer.setResizable(((Boolean) (Boolean) newValue).booleanValue());
				else if ("alpha".equals(name))
					layer.setAlpha(((Float) (Float) newValue).floatValue());
				else if ("description".equals(name))
					layer.setDescription(newValue.toString());
				else if ("custom.draw.shape.factory".equals(name)) {
					for (int i = 0; i < size; i++) {
						Node node = (Node) findList.get(i);
						node.putCustomDrawShapeFactory(((Integer) (Integer) newValue).intValue());
					}

				} else if ("custom.draw.gradient.factory".equals(name)) {
					for (int i = 0; i < size; i++) {
						Node node = (Node) findList.get(i);
						node.putCustomDrawGradientFactory(((Integer) (Integer) newValue).intValue());
					}

				} else if ("custom.draw.fill.color".equals(name)) {
					for (int i = 0; i < size; i++) {
						Node node = (Node) findList.get(i);
						node.putCustomDrawFillColor((Color) (Color) newValue);
					}

				} else if ("custom.draw.gradient".equals(name)) {
					for (int i = 0; i < size; i++) {
						Node node = (Node) findList.get(i);
						node.putCustomDrawGradient(((Boolean) (Boolean) newValue).booleanValue());
					}

				} else if ("custom.draw.gradient.color".equals(name)) {
					for (int i = 0; i < size; i++) {
						Node node = (Node) findList.get(i);
						node.putCustomDrawGradientColor((Color) (Color) newValue);
					}

				} else if ("custom.draw.outline.stroke".equals(name)) {
					for (int i = 0; i < size; i++) {
						Node node = (Node) findList.get(i);
						node.putCustomDrawOutlineStroke(newValue.toString());
					}

				} else if ("custom.draw.outline.color".equals(name)) {
					for (int i = 0; i < size; i++) {
						Node node = (Node) findList.get(i);
						node.putCustomDrawOutlineColor((Color) (Color) newValue);
					}

				}
			}
		});
	}

	private void convertBoxToTableBox() {
		java.util.List layers = box.getLayerModel().getLayers();
		int size = layers.size();
		for (int i = 0; i < size; i++) {
			Layer layer = (Layer) layers.get(i);
			if (layer != box.getLayerModel().getDefaultLayer()) {
				Element element = new Node(layer);
				element.putClientProperty("id", layer.getID());
				element.putClientProperty("name", layer.getName());
				element.putClientProperty("visible", layer.isVisible());
				element.putClientProperty("selectable", layer.isSelectable());
				element.putClientProperty("movable", layer.isMovable());
				element.putClientProperty("resizable", layer.isResizable());
				element.putClientProperty("alpha", new Float(layer.getAlpha()));
				element.putClientProperty("description", layer.getDescription());
				element.putClientProperty("custom.draw.shape.factory", layer.getClientProperty("custom.draw.shape.factory"));
				element.putClientProperty("custom.draw.gradient.factory", layer.getClientProperty("custom.draw.gradient.factory"));
				element.putClientProperty("custom.draw.gradient", layer.getClientProperty("custom.draw.gradient"));
				element.putClientProperty("custom.draw.gradient.color", layer.getClientProperty("custom.draw.gradient.color"));
				element.putClientProperty("custom.draw.fill.color", layer.getClientProperty("custom.draw.fill.color"));
				element.putClientProperty("custom.draw.outline.stroke", layer.getClientProperty("custom.draw.outline.stroke"));
				element.putClientProperty("custom.draw.outline.color", layer.getClientProperty("custom.draw.outline.color"));
				tableBox.addElement(element);
			}
		}

	}

	private TDataBox box;
	private TNetwork network;
	private TDataBox tableBox;
	public DataBoxQuickFinder layerFinder;
	private boolean boxSelectionChange;
	private boolean tableBoxSelectionChange;

}
