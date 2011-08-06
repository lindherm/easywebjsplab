$("document").ready(function() {
	Ext.onReady(function() {
		$(".boxy").boxy();
		$("img").click(function() {
			new Boxy("<p><img src=" + $(this).attr("src") + " width=400 height=500></img></p><p align='center'><a href=" + $(this).attr("src") + " target='_blank'>查看原图</a></p>", {
				title : $(this).attr("filename"),
				closeText : "[关闭]",
				model : true
			});
		});
		$("#upload").click(function() {
			Boxy.confirm("你确定要上传这些文件吗？", function() {
				upload.setUseQueryString(true);
				upload.setPostParams({
					name : "xiaoliya",
					age : "28"
				});
				if (upload.getStats().files_queued > 0) {
					upload.startUpload();
				} else {
					Boxy.alert("请选择上传的文件!", null, {
						title : "提示信息"
					});
					return false;
				}
			}, {
				title : "确认信息"
			});
		});
		/*function delfile(){
			alert("hello");
		}*/
		/*$("#delfile").each(function(i, n) {
			$(n).click(function() {
				alert("hello");
				$.post("uploadAction_deleteFile.box", {
					id : $(this).attr("fileid")
				}, function(data) {
					alert(data);
				});
			});

		});*/

	});
});