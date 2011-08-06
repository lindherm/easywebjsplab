$("document").ready(function() {
	Ext.onReady(function() {
		$(".boxy").boxy();
		$("img").click(function() {
			new Boxy("<p><img src=" + $(this).attr("src") + " width=400 height=500></img></p>", {
				title : "图片",
				closeText : "[关闭]",
				model : true
			});
		});
		$("#upload").click(function() {
			upload.setUseQueryString(true);
			upload.setPostParams({
				name : "xiaoliya",
				age : "28"
			});
			if (upload.getStats().files_queued > 0) {
				upload.startUpload();
			} else {
				Boxy.alert("请选择上传的文件!", null, {
					title : "提示窗口"
				});
			}
		});
	});
});