$("document").ready(function() {
	Ext.onReady(function() {
		var sb=new StringBuilder();
		sb.appendFormat("Hello {0}! {1}", "World", "Bye");
		sb.append("||");
		sb.appendFormatEx("Hello ?! ?", "go", "next");
		Ext.Msg.alert("提示框",sb.toString(),null);
	});
});
