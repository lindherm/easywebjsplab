<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="extjs/js/plugin/ajaxcommon.js"></script>
<script type="text/javascript" src="js/myapp.js"></script>
<title>你好</title>
</head>
<body>
<table border="1">
	<tr>
		<td>id</td>
		<td>menuName</td>
		<td>parentid</td>
	</tr>
	<c:forEach items="${name}" var="helloworld">
		<tr>
			<td>${helloworld.id }</td>
			<td>${helloworld.menuName }</td>
			<td>${helloworld.parentid }</td>
		</tr>
	</c:forEach>
</table>
<form action="helloWorldAction_save.box" method="post">
<table>
	<tr>
		<td>id</td>
		<td><input type="text" id="helloWorld.id" name="helloWorld.id" /></td>
	</tr>
	<tr>
		<td>menuName</td>
		<td><input type="text" id="helloWorld.menuName" name="helloWorld.menuName" /></td>
	</tr>
	<tr>
		<td>parentid</td>
		<td><input type="text" id="helloWorld.parentid" name="helloWorld.parentid" /></td>
	</tr>
	<tr>
		<td><input type="submit" value="保存" /></td>
	</tr>
</table>
</form>
<div id="tree" style="width: 800; height: 400"></div>
</body>
</html>