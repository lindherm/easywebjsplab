<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="extjs/js/plugin/ajaxcommon.js"></script>
<script type="text/javascript" src="extjs/js/plugin/jsonrpc.js"></script>
<script type="text/javascript">
$("document").ready(function(){
	Ext.onReady(function(){
		try{
			var jabsorb=new JSONRpcClient("./JSON-RPC");
			var res=jabsorb.helloWorldRPCManager.getListTest();
			var res=res['list'];
			for(var i=0;i<res.length;i++){
				alert(res[i]);
			}
		}catch(e){
			alert(e.name+"|"+e.code+"|"+e.message+"|"+e.javaStack);
		}
	});
});
</script>
<title>你好</title>
</head>
<body>

</body>
</html>