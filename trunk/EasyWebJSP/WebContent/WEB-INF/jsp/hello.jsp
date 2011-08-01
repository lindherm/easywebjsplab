<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>你好</title>
</head>
<body>
<table border="1">
	<c:forEach items="${name}" var="helloworld">
		<tr>
			<td>${helloworld.id }</td>
			<td>${helloworld.title }</td>
		</tr>
	</c:forEach>
</table>
${name}
<form action="helloWorldAction_save.box" method="post">
<table>
	<tr>
		<td>id</td>
		<td><input type="text" id="helloWorld.id" name="helloWorld.id" /></td>
	</tr>
	<tr>
		<td>title</td>
		<td><input type="text" id="helloWorld.title" name="helloWorld.title" /></td>
	</tr>
	<tr>
		<td><input type="submit" value="保存"/></td>
	</tr>
</table>
</form>
</body>
</html>