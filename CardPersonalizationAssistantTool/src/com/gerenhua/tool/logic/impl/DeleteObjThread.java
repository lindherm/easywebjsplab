package com.gerenhua.tool.logic.impl;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.gerenhua.tool.log.Log;
import com.gerenhua.tool.logic.apdu.CommonAPDU;
import com.watchdata.commons.lang.WDAssert;
import com.watchdata.commons.lang.WDStringUtil;

public class DeleteObjThread extends Thread {
	public JTree tree;
	public CommonAPDU commonAPDU;
	public static Log logger = new Log();

	public DeleteObjThread(JTree tree, CommonAPDU commonAPDU) {
		this.tree = tree;
		this.commonAPDU = commonAPDU;
	}

	@Override
	public void run() {
		DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		String selNodeName = (selNode != null) ? selNode.toString() : null;
		if (WDAssert.isNotEmpty(selNodeName)) {

			String aid = selNodeName.substring(0, selNodeName.indexOf(";"));
			String p1 = "00";
			String p2 = "00";
			String aidLen = WDStringUtil.paddingHeadZero(Integer.toHexString(aid.length() / 2), 2);
			String lc = WDStringUtil.paddingHeadZero(Integer.toHexString(aid.length() / 2 + 2), 2);

			if (selNode.getParent().toString().equalsIgnoreCase("Application Instances")) {
				p2 = "00";
			} else {
				p2 = "80";
			}

			String cmd = "80E4" + p1 + p2 + lc + "4F" + aidLen + aid;
			try {
				String resp = commonAPDU.send(cmd);
				String sw = resp.substring(resp.length() - 4);
				if (sw.equalsIgnoreCase("9000")) {
					CardInfoThread.expandTree(tree, true);
					tree.updateUI();
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

}
