<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div align="center">
		<h1>Order confirmation</h1>
		<s:label label="OrderId" name="order.orderId" />, <s:label label="OrderName" name="order.orderName" /> <br/>
		has been successfully placed.
	</div>
</body>
</html>