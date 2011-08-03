$(document).ready(function() {
	Ext.onReady(function() {
		alert("hello");
		$.post("helloWorldAction_getJson.box", null, function(data) {
			//alert(data);
			alert(eval("(" + data + ")"));
		});
	});
});