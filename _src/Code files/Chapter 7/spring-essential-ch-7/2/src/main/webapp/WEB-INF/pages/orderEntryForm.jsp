<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div align="center">
		<h1>Spring and Struts Integration</h1>
		<h2>Place an order</h2>
		<s:form action="doOrder" method="post">
			<s:textfield label="OrderId" name="order.orderId" />

			<s:submit value="Order" />
		</s:form>				
	</div>	
</body>
</html>