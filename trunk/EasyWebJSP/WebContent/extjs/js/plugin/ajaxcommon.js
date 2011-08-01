// 参考Ferreri Gbriele Faster Javascript StringBuilder
// http://www.codeproject.com/KB/ajax/JavascriptStringBuilderEx.aspx
var StringBuilder = Array;
Array.prototype.append = Array.prototype.push;
Array.prototype.toString = Array.prototype.join;

document.write('<base target="_self">');
document.write('<style type="text/css">@import url("extjs/resources/css/css/globle.css");@import url("extjs/resources/css/ext-all.css");</style>');

function containJS(jsName) {
	document.write('<script type="text/javascript" src="' + jsName + '"></script>');
}
if (typeof Ext === "undefined") {
	containJS("extjs/adapter/jquery/jquery.js");
	containJS("extjs/adapter/jquery/ext-jquery-adapter.js");
	containJS("extjs/ext-all.js");
	containJS("extjs/ext-lang-zh_CN.js");
}