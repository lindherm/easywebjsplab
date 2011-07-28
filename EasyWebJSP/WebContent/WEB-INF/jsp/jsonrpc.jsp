<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.easyweb.jsonrpc.TestJsonRpc"%>
<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge" />
<%
TestJsonRpc testJsonRpc=new TestJsonRpc();
JSONRPCBridge.registerObject("testJsonRpc", testJsonRpc);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>jsonrpc Demo</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../extjs/resources/css/css/globle.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="../extjs/adapter/jquery/jquery.js"></script>
<script type="text/javascript" src="../extjs/adapter/jquery/ext-jquery-adapter.js"></script>
<script type="text/javascript" src="../extjs/ext-all.js"></script>
<script type="text/javascript" src="../extjs/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="../extjs/js/plugin/jsonrpc.js"></script>
<script type="text/javascript" src="../js/myapp.js"></script>
</head>
<body>
</body>
</html>