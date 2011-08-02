$("document").ready(function() {
	Ext.onReady(function() {
		var sb=new StringBuilder();
		sb.appendFormat("Hello {0}! {1}", "World", "Bye");
		sb.append("||");
		sb.appendFormatEx("Hello ?! ?", "go", "next");
		alert(sb.toString());
		
	});
});
