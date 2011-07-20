<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>SWFUpload Demos</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="extjs/resources/css/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="extjs/resources/css/css/button.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="extjs/adapter/jquery/jquery.js"></script>
<script type="text/javascript" src="extjs/adapter/jquery/ext-jquery-adapter.js"></script>
<script type="text/javascript" src="extjs/ext-all.js"></script>
<script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="extjs/js/plugin/swfupload.js"></script>
<script type="text/javascript" src="extjs/js/plugin/fileprogress.js"></script>
<script type="text/javascript" src="extjs/js/plugin/swfupload.queue.js"></script>
<script type="text/javascript" src="extjs/js/plugin/handlers.js"></script>
<script type="text/javascript" src="js/flash.js"></script>
</head>
<body style="background-color: #C0D1E3; padding: 2px;">
<input type="hidden" name="squadronInfo.id" id="id" value="${bizId}">
<input type="hidden" name="bizId" id="bizId" value="${bizId}">
<input type="hidden" name="bizType" id="bizType" value="squadronInfoBizFile">
<input type="hidden" name="action" id="action" value="insert">
<input id="openFile" select="files" filetypes="*.jpg;*.gif;*.jpeg" filetypesdescription="所有图片文件" type="button" class="box-btn-back" value="浏览..." />
<div id="fsUploadProgress2" style="height: 30px;">
</body>
</html>