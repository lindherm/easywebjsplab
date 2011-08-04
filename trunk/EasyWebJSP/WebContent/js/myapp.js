$("document").ready(function() {
	Ext.onReady(function() {
		Ext.QuickTips.init();
		// 树定义及初始化
		var tree = new Ext.tree.TreePanel({
			title : "功能菜单",
			el : 'tree',
			width : 400,
			height : 300,
			border : true,
			animate : true,
			autoScroll : true,
			enableDD : false,
			containerScroll : true,
			checkModel : 'cascade',
			onlyLeafCheckable : false,
			loader : new Ext.tree.TreeLoader({
				dataUrl : 'helloWorldAction_getJson.box',
				baseAttrs : {
					uiProvider : Ext.tree.TreeCheckNodeUI
				}
			})
		});
		var root = new Ext.tree.AsyncTreeNode({
			text : 'JSON TREE TEST',
			draggable : false,
			id : '0'
		});
		tree.setRootNode(root);
		tree.render();
		root.expand();
		// 处理事件
		tree.on({
			'beforeload' : function(node, e) {
				tree.loader.dataUrl = 'helloWorldAction_getJson.box?pid=' + node.id;
			},
			'click' : function(node, e) {
				// 获得所有选中复选框的值是一个json对象
				var nodes = tree.getChecked();
				if (nodes == '' || nodes == "") {
					Ext.MessageBox.alert("提示框", "没有选中的菜单！", null);
					return;
				}
				$.each(nodes, function(i, n) {
					Ext.MessageBox.alert("提示框", n.id + "|" + n.text, null);
				});
			}
		});
	});

});