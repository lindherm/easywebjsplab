$("document").ready(function() {
	Ext.onReady(function() {
		try {
			var jabsorb = new JSONRpcClient("./JSON-RPC");
			// 传list返回list
			var res = jabsorb.helloWorldRPCManager.getListTest({
				"javaClass" : "java.util.ArrayList",
				"list" : [
				    'hello',
				    'gof',
				    'damn'
				]
			});
			// 传bean返回bean
			var resbean = jabsorb.helloWorldRPCManager.getHelloWorldTest({
				"javaClass" : "org.easyweb.helloworld.model.HelloWorld",
				id : "201314",
				menuName : "menu_none",
				parentid : "123456"
			});
			$.each(resbean, function(i, n) {
				alert(i+"|"+n);
			});
		} catch (e) {
			alert(e.name + "|" + e.code + "|" + e.message + "|" + e.javaStack);
		}
	});
});
