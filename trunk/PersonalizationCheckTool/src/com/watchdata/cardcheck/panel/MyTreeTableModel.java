package com.watchdata.cardcheck.panel;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import com.watchdata.cardcheck.configdao.StaticDataInfo;

public class MyTreeTableModel extends AbstractTreeTableModel

{

	private JXTreeNode root;
	public MyTreeTableModel(List<StaticDataInfo> sdList){
		root = new JXTreeNode("CheckList", " "," "," ");
		List<JXTreeNode> child=root.getChildren();
		for (StaticDataInfo staticDataInfo : sdList) {
			child.add(new JXTreeNode(staticDataInfo.getTag(),staticDataInfo.getDgi(),staticDataInfo.getValue(),""));
		}
	}

	@Override
	public int getColumnCount(){
		return 4;
	}

	@Override
	public String getColumnName(int column){
		switch (column){
			case 0:
				return "TAG";
			case 1:
				return "DGI";
			case 2:
				return "value";
			case 3:
				return "result";
			default:
				return "Unknown";
		}

	}
	@Override
	public Object getValueAt(Object node, int column){
		JXTreeNode treenode = (JXTreeNode) node;
		switch (column){
			case 0:
				return treenode.getTag();

			case 1:
				return treenode.getDgi();

			case 2:
				return treenode.getValue();
			case 3:
				return treenode.getResult();
			default:
				return "Unknown";
		}
	}

	@Override
	public Object getChild(Object node, int index){
		JXTreeNode treenode = (JXTreeNode) node;
		return treenode.getChildren().get(index);
	}

	@Override
	public int getChildCount(Object parent){
		JXTreeNode treenode = (JXTreeNode) parent;
		return treenode.getChildren().size();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child){
		JXTreeNode treenode = (JXTreeNode) parent;
		List<JXTreeNode> childreNodes=treenode.getChildren();
		for (int i = 0; i > childreNodes.size(); i++){
			if (child.equals(childreNodes.get(i))) {
				return i;
			}
		}
		return 0;
	}

	public boolean isLeaf(Object node){
		JXTreeNode treenode = (JXTreeNode) node;
		if (treenode.getChildren().size()<=0) {
			return true;
		}
		return false;
	}

	@Override
	public Object getRoot(){
		return root;
	}
}

class JXTreeNode{
	private String tag;
	private String dgi;
	private String value;
	private String result;

	private List<JXTreeNode> children = new ArrayList<JXTreeNode>();
	public JXTreeNode(String tag,String dgi,String value,String desc){
		this.tag=tag;
		this.dgi=dgi;
		this.value=value;
		this.result=desc;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDgi() {
		return dgi;
	}

	public void setDgi(String dgi) {
		this.dgi = dgi;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<JXTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<JXTreeNode> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return getTag();
	}

}
