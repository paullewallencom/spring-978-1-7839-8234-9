<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title> Admin Workspace</title>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</head>
<body>
	Dear <strong>${user}</strong>, Welcome to Admin Workspace.


	<a href="<c:url value="/logout" />">Logout</a> <br/>
	<a href="<c:url value="/migrateUsers" />">Migrate Users</a> <br/> <br/>
	<a href="<c:url value="/userdata/accountant" />">Load accountant user data</a> <br/>
</body>
</html>