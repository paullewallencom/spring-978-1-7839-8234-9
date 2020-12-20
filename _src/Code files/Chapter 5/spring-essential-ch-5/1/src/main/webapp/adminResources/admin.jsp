<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Admin page</title>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
 response.setHeader("Pragma","no-cache"); //HTTP 1.0
 response.setDateHeader ("Expires", 0);
session.invalidate();%>
</head>
<body>
Admin page
</body>
</html>