<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title> laod user data page</title>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</head>
<body>
	Dear <strong>${user}</strong>,  here is ${userName} user data page.


	<a href="<c:url value="/logout" />">Logout</a>

</body>
</html>