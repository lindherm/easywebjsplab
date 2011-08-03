$("document").ready(function() {
	Ext.onReady(function() {
		var sb=new StringBuilder();
		sb.appendFormat("Hello {0}! {1}", "World", "Bye");
		Ext.MessageBox.alert("提示框",sb.toString(),function(){});
	});
});
