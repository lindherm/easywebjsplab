package com.watchdata.cardcheck.logic.impl;

import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.watchdata.cardcheck.log.Log;
import com.watchdata.cardcheck.logic.apdu.CommonAPDU;
import com.watchdata.cardcheck.utils.Config;

public class CardInfoThread extends Thread {
	public CommonAPDU commonAPDU;
	public JTree tree;
	public static Log logger=new Log();
	public JTextPane textPane;
	public String secrityLevel;
	public String keyVersion;
	public String keyId;
	public String encKey;
	public String macKey;
	public String dekKey;
	
	public CardInfoThread(JTree tree,CommonAPDU commonAPDU,String secrityLevel,String keyVersion,String keyId,String encKey,String macKey,String dekKey,JTextPane textPane){
		this.tree=tree;
		this.commonAPDU=commonAPDU;
		this.textPane=textPane;
		this.secrityLevel=secrityLevel;
		this.keyVersion=keyVersion;
		this.keyId=keyId;
		this.encKey=encKey;
		this.macKey=macKey;
		this.dekKey=dekKey;
		logger.setLogArea(textPane);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String resp;
		DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) dtm.getRoot();
		root.removeAllChildren();
		try {
			HashMap<String, String> res=commonAPDU.reset(Config.getValue("Terminal_Data", "reader"));
			if (!"9000".equals(res.get("sw"))) {
				logger.error("card reset error");
			}
			resp = commonAPDU.send("00A4040000");
			commonAPDU.externalAuthenticate(secrityLevel,keyVersion,keyId,encKey,macKey,dekKey);
			resp = commonAPDU.send("80F28000024F00");

			DefaultMutableTreeNode cardManager = null;
			if (resp.substring(resp.length() - 4).equalsIgnoreCase("9000")) {
				int pos = 0;
				while (pos < resp.length() - 4) {
					int len = Integer.parseInt(resp.substring(pos, 2), 16);
					pos += 2;
					String aid = resp.substring(pos, 2 * len + pos);
					pos += 2 * len;
					String lifeStyleCode = resp.substring(pos, pos + 2);
					pos += 2;
					String privilegesCode = resp.substring(pos, pos + 2);
					pos += 2;
					cardManager = new DefaultMutableTreeNode("Issuer Security Domain=" + aid + ";" + Config.getValue("Card_Lifestyle", lifeStyleCode));
					root.add(cardManager);

					DefaultMutableTreeNode apps = new DefaultMutableTreeNode("Application Instances");
					cardManager.add(apps);

					resp = commonAPDU.send("80F24000024F00");

					if (resp.substring(resp.length() - 4).equalsIgnoreCase("9000")) {
						pos = 0;
						while (pos < resp.length() - 4) {
							len = Integer.parseInt(resp.substring(pos, pos + 2), 16);
							pos += 2;
							aid = resp.substring(pos, 2 * len + pos);
							pos += 2 * len;
							lifeStyleCode = resp.substring(pos, pos + 2);
							pos += 2;
							privilegesCode = resp.substring(pos, pos + 2);
							pos += 2;
							DefaultMutableTreeNode aidNode = new DefaultMutableTreeNode(aid + ";" + Config.getValue("App_Lifestyle", lifeStyleCode));
							apps.add(aidNode);
						}
					}
					DefaultMutableTreeNode loadFiles = new DefaultMutableTreeNode("Load Files");
					cardManager.add(loadFiles);
					resp = commonAPDU.send("80F22000024F00");
					if (resp.substring(resp.length() - 4).equalsIgnoreCase("9000")) {
						pos = 0;
						while (pos < resp.length() - 4) {
							len = Integer.parseInt(resp.substring(pos, pos + 2), 16);
							pos += 2;
							String loadFile = resp.substring(pos, 2 * len + pos);
							pos += 2 * len;
							lifeStyleCode = resp.substring(pos, pos + 2);
							pos += 2;
							privilegesCode = resp.substring(pos, pos + 2);
							pos += 2;

							DefaultMutableTreeNode loadFileNode = new DefaultMutableTreeNode(loadFile + ";" + Config.getValue("App_Lifestyle", lifeStyleCode));
							loadFiles.add(loadFileNode);
						}
					}

					DefaultMutableTreeNode loadFilesAndModules = new DefaultMutableTreeNode("Load Files and Modules");
					cardManager.add(loadFilesAndModules);

					resp = commonAPDU.send("80F21000024F00");

					if (resp.substring(resp.length() - 4).equalsIgnoreCase("9000")) {
						pos = 0;
						while (pos < resp.length() - 4) {
							len = Integer.parseInt(resp.substring(pos, pos + 2), 16);
							pos += 2;
							String loadFile = resp.substring(pos, 2 * len + pos);
							pos += 2 * len;
							lifeStyleCode = resp.substring(pos, pos + 2);
							pos += 2;
							privilegesCode = resp.substring(pos, pos + 2);
							pos += 2;
							String modulesNum= resp.substring(pos, pos + 2);
							pos += 2;
							DefaultMutableTreeNode loadFileNode = new DefaultMutableTreeNode(loadFile + ";" + Config.getValue("App_Lifestyle", lifeStyleCode));
							loadFilesAndModules.add(loadFileNode);
							for (int i = 0; i < Integer.parseInt(modulesNum); i++) {
								len = Integer.parseInt(resp.substring(pos, pos + 2), 16);
								pos += 2;
								String modules = resp.substring(pos, pos + 2 * len);
								pos += 2 * len;

								DefaultMutableTreeNode executableModules = new DefaultMutableTreeNode(modules);
								loadFileNode.add(executableModules);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			expandTree(tree, true);
			tree.updateUI();
		}
	}
	public void expandTree(JTree tree, boolean bo) {
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		expandAll(tree, new TreePath(root), bo);
	}

	private void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

}
