<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Home page</title>
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
</head>
<body>
	 ${anonymousMessage} .
	This is home page.<br/>
	<a href="<c:url value="/user" />">Login as User</a><br/>
	<a href="<c:url value="/admin" />">Login as Admin</a><br/>
	<a href="<c:url value="/accountant" />">Login as Accountant</a><br/>
</body>
</html>