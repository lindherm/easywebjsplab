$("document").ready(function() {
	Ext.onReady(function() {
		Ext.QuickTips.init();
		// 树定义及初始化
		var tree = new Ext.tree.TreePanel({
			title : "功能菜单",
			el : 'tree',
			width : 400,
			height : 300,
			useArrows : true,
			border : true,
			animate : true,
			autoScroll : true,
			frame : true,
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
			id : '0',
			text : 'JSON TREE TEST',
			draggable : false,
			leaf : false
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
					alert(n.id + "|" + n.text);
				});
			},
			'checkchange' : function(node, checked) {
				if (checked) {
					alert("选中");
				} else {
					alert("不选中");
				}
			}

		});
	});

});