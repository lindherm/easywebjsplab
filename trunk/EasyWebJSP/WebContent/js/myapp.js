$(document).ready(function() {
	Ext.onReady(function() {
		$.post("helloWorldAction_getJson.box", null, function(data) {
			$.each(data,function(i,n){
				alert();
				alert(i);
				alert(n);
			});
		});
	});
});