/*
 * Ferreri Gbriele***Faster Javascript StringBuilder***http://www.codeproject.com/KB/ajax/JavascriptStringBuilderEx.aspx
 */
//var sb = new StringBuilderEx(); 
//sb.append("Hello"); 
//sb.appendFormat("Hello {0}!!! {1}", "World", "Bye"); 
//sb.appendFormatEx("Hello ?!!! ?", "World", "Bye");
// Assign our class to Array class
var StringBuilder = Array;

// Using prototype link function append to push
Array.prototype.append = Array.prototype.push;

// Used to convert arguments in array
Array.prototype._convertToArray = function(arguments) {
	if (!arguments)
		return new Array();
	if (arguments.toArray)
		return arguments.toArray();
	var len = arguments.length
	var results = new Array(len);
	while (len--) {
		results[len] = arguments[len];
	}
	return results;
};

// First solution using regular expression
Array.prototype.appendFormat = function(f_str) {
	var args = this._convertToArray(arguments).slice(1);
	// 执行替换
	this[this.length] = f_str.replace(/\{(\d+)\}/g, function(n, i) {
		return args[i].toString();
	});
};

// Second solution using split and join
Array.prototype.appendFormatEx = function(f_str) {
	if (this._parameters == null)
		this._parameters = new Array();

	var args = this._convertToArray(arguments).slice(1);

	for (var t = 0, len = args.length; t < len; t++) {
		this._parameters[this._parameters.length] = args[t];
	}

	this[this.length] = f_str;
};

// Concatenate the strings using join
// (some lines of code are relay with second solution)
Array.prototype.toString = function() {
	var hasParameters = this._parameters != null;
	hasParameters = hasParameters && this._parameters.length > 0;

	if (hasParameters) {
		var values = this.join("").split('?');
		var tempBuffer = new Array();

		for (var t = 0, len = values.length; t < len; t++) {
			tempBuffer[tempBuffer.length] = values[t];
			tempBuffer[tempBuffer.length] = this._parameters[t];
		}

		return tempBuffer.join("");
	} else {
		return this.join("");
	}
};
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
}