$(document).ready(function() {
	Ext.onReady(function() {
		try {
			var jsonrpc = new JSONRpcClient("../JSON-RPC");
			// Call a Java method on the server
			var result = jsonrpc.testJsonRpc.getMessage("xiaoliya");
			alert(result);
		} catch (e) {
			alert(e);
		}

	});
});