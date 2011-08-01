document.write("<base target='_self'>");
document.write("<style type='text/css'>@import url('extjs/resources/css/css/globle.css');@import url(/extjs/resources/css/ext-all.css);</style>");

require = function(jsName) {
	document.write("<script type='text/javascript' src='" + jsName + "'></script>");
}
if (Ext === "undefined") {
	require("extjs/adapter/jquery/jquery.js");
	require("extjs/adapter/jquery/ext-jquery-adapter.js");
	require("extjs/ext-all.js");
	reuqire("extjs/ext-lang-zh_CN.js");
}