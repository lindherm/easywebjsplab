$(document).ready(function() {
	Ext.onReady(function() {
		try {
			var jsonrpc = new JSONRpcClient("../JSON-RPC");
			// Call a Java method on the server
			//var result = jsonrpc.testJsonRpc.getMessage("xiaoliya");
			var result = jsonrpc.testJsonRpc.getMap();
			var res=result['map']
			for(i in res){
				alert(i+"="+res[i]);
			}
		} catch (e) {
			alert(e);
		}

	});
});