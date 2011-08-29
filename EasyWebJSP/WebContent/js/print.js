$("document").ready(function() {
	BoxUI.onReady(function() {
		// 打印页面head之间设置：<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed> </object>
		function getLodop(oOBJECT, oEMBED) {
			/********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
			 * 本函数根据浏览器类型决定采用哪个对象作为控件实例： IE系列、IE内核系列的浏览器采用oOBJECT， 其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED。
			 *******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
			var strHtml1 = "<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop.exe'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
			var strHtml2 = "<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop.exe'>执行升级</a>,升级后请重新进入。</font>";
			var strHtml3 = "<br><br><font color='#FF00FF'>注意：<br>1：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它;<br>2：如果浏览器表现出停滞不动等异常，建议关闭其“plugin-container”(网上搜关闭方法)功能;</font>";
			var LODOP = oEMBED;
			try {
				if (navigator.appVersion.indexOf("MSIE") >= 0)
					LODOP = oOBJECT;

				if ((LODOP == null) || (typeof(LODOP.VERSION) == "undefined")) {
					if (navigator.userAgent.indexOf('Firefox') >= 0)
						document.documentElement.innerHTML = strHtml3 + document.documentElement.innerHTML;
					if (navigator.appVersion.indexOf("MSIE") >= 0)
						document.write(strHtml1);
					else
						document.documentElement.innerHTML = strHtml1 + document.documentElement.innerHTML;
					return LODOP;
				} else if (LODOP.VERSION < "6.0.3.2") {
					if (navigator.appVersion.indexOf("MSIE") >= 0)
						document.write(strHtml2);
					else
						document.documentElement.innerHTML = strHtml2 + document.documentElement.innerHTML;
					return LODOP;
				}
				// *****如下空白位置适合调用统一功能:*********

				// *******************************************
				return LODOP;
			} catch (err) {
				document.documentElement.innerHTML = "Error:" + strHtml1 + document.documentElement.innerHTML;
				return LODOP;
			}
		}

		// 打印预览
		$("#printpre").click(function() {
			// 初始化lodop
			var LODOP = getLodop(document.getElementById('LODOP'), document.getElementById('LODOP_EM'));
			$(".noprint").hide();
			LODOP.PRINT_INIT("打印预览");
			LODOP.SET_SHOW_MODE("SKIN_TYPE", 1);// 设置打印控件皮肤
			LODOP.ADD_PRINT_HTM("0%", "0%", "100%", "100%", document.documentElement.innerHTML);
			LODOP.PREVIEW();
			$(".noprint").show();
		});
		// 打印
		$("#printTrue").click(function() {
			// 初始化lodop
			var LODOP = getLodop(document.getElementById('LODOP'), document.getElementById('LODOP_EM'));
			$(".noprint").hide();
			LODOP.PRINT_INIT("打印");
			LODOP.SET_SHOW_MODE("SKIN_TYPE", 1);
			LODOP.ADD_PRINT_HTM("0%", "0%", "100%", "100%", document.documentElement.innerHTML);
			LODOP.PRINTA(); // 调用控件选择打印机打印
			$(".noprint").show();
		});
	});
});