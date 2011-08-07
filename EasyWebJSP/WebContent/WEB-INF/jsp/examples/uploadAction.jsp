<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>SWFUpload Demos</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="superbox/jquery.superbox.css" type="text/css"/>
<script type="text/javascript" src="extjs/js/plugin/ajaxcommon.js"></script>
<script type="text/javascript" src="extjs/js/plugin/upload/swfupload.js"></script>
<script type="text/javascript" src="extjs/js/plugin/upload/fileprogress.js"></script>
<script type="text/javascript" src="extjs/js/plugin/upload/handlers.js"></script>
<script type="text/javascript" src="js/upload.js"></script>
<script type="text/javascript" src="js/upload/uploadAction.js"></script>
</head>
<body style="background-color: #C0D1E3; padding: 20px;">
<div id="errormsg"></div>
<table class="table" width="600">
	<tr>
		<td width="500"><input type="hidden" name="bizId" id="bizId" value="${bizId}"> <input type="hidden" name="bizType" id="bizType" value="squadronInfoBizFile"> <input type="hidden" name="action" id="action" value="insert"> <input id="openFile" select="files" filetypes="*" filetypesdescription="所有图片文件" type="button" class="box-btn-back" value="浏览..." />
		<div id="fsUploadProgress2" style="width: 500"></div>
		</td>
		<td width="300"><input type="button" id="upload" name="upload" value="上传"></td>
	</tr>
</table>
</body>
<table cellpadding="0" cellspacing="0" border="1" bordercolor="#000000">
	<tr>
		<c:forEach items="${filelist}" var="file" varStatus="status">
			<c:if test="${status.index%5==0}">
	</tr>
	<tr>
		</c:if>
		<td bordercolor="#000000"><a href="downLoadAction.box?id=${file.id}" rel="superbox[gallery][my_gallery]"><img src="downLoadAction.box?id=${file.id}" width="200" height="200" title="点击查看大图" filename="${file.fileName}"></img></a></td>
		<td><a href="javascript:void(0)" id="deletefile" fileid="${file.id}">删除</a></td>
		</c:forEach>
</table>
</html>