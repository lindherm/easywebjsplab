$("document").ready(function() {
	Ext.onReady(function() {
		$(".boxy").boxy();

		$("img").click(function() {
			new Boxy("<p><img src=" + $(this).attr("src") + " width=400 height=500></img></p><p align='center'><a href=" + $(this).attr("src") + " target='_blank'>查看原图</a></p>", {
				title : $(this).attr("filename"),
				model : true
			});
		});

		$("#upload").click(function() {
			if (upload.getStats().files_queued > 0) {
				Boxy.confirm("你确定要上传这些文件吗？", function() {
					upload.setUseQueryString(true);
					upload.setPostParams({
						name : "xiaoliya",
						age : "28"
					});
					upload.startUpload();
				}, {
					title : "确认信息"
				});
			} else {
				Boxy.alert("请选择上传的文件!", null, {
					title : "提示信息"
				});
				return false;
			}

		});
		// 删除文档
		$("div[@id='deletefile']").each(function(i, n) {
			$(n).click(function() {
				var id = $(n).attr("fileid");
				Boxy.confirm("您确定要删除此附件吗?", function() {
					$.post("uploadAction_deleteFile.box?id=" + id, null, function(data) {
						Boxy.alert(data, null, {
							title : "提示信息",
							model : true
						});
					});
				}, {
					title : "确认信息"
				});
			});

		});
	});
});