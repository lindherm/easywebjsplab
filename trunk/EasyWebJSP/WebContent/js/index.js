$("document").ready(function() {
	Ext.onReady(function() {
		var sb=new StringBuilder();
		sb.append("hello");
		sb.append(34);
		sb.append("hello");
		Ext.MessageBox.alert('提示框', sb.toString(","), null);
	});
});
