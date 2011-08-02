$("document").ready(function() {
	Ext.onReady(function() {
		var sb=new StringBuilder();
		//sb.appendFormat("Hello {0}! {1}", "World", "Bye");
		sb.appendFormatEx("Hello ?! ?", "World", "Bye");
		alert(sb.toString());
	});
});
