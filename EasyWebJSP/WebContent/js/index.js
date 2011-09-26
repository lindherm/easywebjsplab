$("document").ready(function() {
	Ext.onReady(function() {
		/*
		 * var sb=new StringBuilder(); sb.append("fuck,"); sb.appendFormat("Hello {0}! {1}||", "World", "go"); sb.appendFormatEx("mark ,?","dog"); Ext.MessageBox.alert("提示框",sb.toString(),function(){});
		 */
		// Ext.MessageBox.alert("提示框",MD5("name"),null);
		document.write(Base64.encode("hello kettywang"));
		document.write("<br>");
		document.write(Base64.decode(Base64.encode("hello kettywang")));
		var cookie = new CookieHandler();
		cookie.setCookie("name", "xiaoliya", 10)
		document.write("<br>");
		document.write(cookie.getCookie("name"));
		
	});
});
