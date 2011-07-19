<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	StringBuffer uploadUrl = new StringBuffer("http://");
	uploadUrl.append(request.getHeader("Host"));
	uploadUrl.append(request.getContextPath());
	uploadUrl.append("/FileUploadServlet");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>SWFUpload Demos</title>
<link href="extjs/resources/css/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="extjs/resources/css/css/button.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="extjs/adapter/jquery/jquery.js"></script>
<script type="text/javascript" src="extjs/adapter/jquery/ext-jquery-adapter.js"></script>
<script type="text/javascript" src="extjs/ext-all.js"></script>
<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="extjs/js/upload/swfupload.js"></script>
<script type="text/javascript" src="extjs/js/upload/handlers.js"></script>
<script type="text/javascript">
			var swfu;
			window.onload = function () {
				swfu = new SWFUpload({
					upload_url: "<%=uploadUrl.toString()%>",
					post_params: {"name" : "huliang"},
					
					// File Upload Settings
					file_size_limit : "10 MB",	// 1000MB
					file_types : "*.*",
					file_types_description : "所有文件",
					file_upload_limit : "0",
									
					file_queue_error_handler : fileQueueError,
					file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
					file_queued_handler : fileQueued,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccess,
					upload_complete_handler : uploadComplete,
	
					// Button Settings
					button_image_url : "../../extjs/resources/images/SmallSpyGlassWithTransperancy_17x18.png",
					button_placeholder_id : "spanButtonPlaceholder",
					button_width: 180,
					button_height: 18,
					button_text : '<span class="button">选择图片 <span class="buttonSmall">(10 MB Max)</span></span>',
					button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
					button_text_top_padding: 0,
					button_text_left_padding: 18,
					button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
					button_cursor: SWFUpload.CURSOR.HAND,
					
					// Flash Settings
					flash_url : "extjs/js/upload/swfupload.swf",
	
					custom_settings : {
						upload_target : "divFileProgressContainer"
					},
					// Debug Settings
					debug: false  //是否显示调试窗口
				});
			};
			function startUploadFile(){
				swfu.startUpload();
			}
		</script>
</head>
<body style="background-color: #C0D1E3; padding: 2px;">
<div id="content">
<form>
<div style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;"><span id="spanButtonPlaceholder"></span> <input id="btnUpload" type="button" value="上  传" onclick="startUploadFile();" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'" onmousedown="this.className='btn3_mousedown'" onMouseOver="this.className='btn3_mouseover'" onmouseout="this.className='btn3_mouseout'" /> <input id="btnCancel" type="button" value="取消所有上传" onclick="cancelUpload();" disabled="disabled" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'" onmousedown="this.className='btn3_mousedown'" onMouseOver="this.className='btn3_mouseover'" onmouseout="this.className='btn3_mouseout'" /></div>
</form>
<div id="divFileProgressContainer"></div>
<div id="thumbnails">
<table id="infoTable" border="0" width="530" style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px; margin-top: 8px;">
</table>
</div>
</div>
</body>
</html>