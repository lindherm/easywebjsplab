document.write('<base target="_self">');
document.write('<style type="text/css">@import url("extjs/resources/css/css/globle.css");@import url("extjs/resources/css/ext-all.css");@import url("boxy/stylesheets/boxy.css");</style>');
function containJS(jsName) {
	document.write('<script type="text/javascript" src="' + jsName + '"></script>');
}
if (typeof Ext === "undefined") {
	containJS("extjs/adapter/jquery/jquery.js");
	containJS("extjs/adapter/jquery/ext-jquery-adapter.js");
	containJS("extjs/ext-all-debug.js");
	containJS("extjs/ext-lang-zh_CN.js");
	containJS("boxy/javascripts/jquery.boxy.js");
	containJS("extjs/js/plugin/jsonrpc.js");
	containJS("js/jsutil/stringBuilder.js");
	containJS("js/jsutil/md5.js");
	containJS("js/jsutil/base64.js");
	containJS("js/jsutil/cookie.js");
}