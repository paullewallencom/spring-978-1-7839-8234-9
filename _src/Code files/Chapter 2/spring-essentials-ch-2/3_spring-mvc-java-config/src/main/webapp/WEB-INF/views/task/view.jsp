<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<jsp:include page="/WEB-INF/views/theme.jsp"></jsp:include>
<title>Taskify: View Task</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/navbar.jsp"></jsp:include>
	<h1 class="text-center">Task Details</h1>
	<hr>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Task...</h3>
			</div>
			<div class="panel-body">

				<%-- <form:form action="new" method="post" commandName="user"> --%>
					<div class="form-group">
						<label for="txtUserName">Title</label>
						<p>${task.name}</p>
					</div>
					<div class="form-group">
						<label for="txtUserName">Comments</label>
						<p>${task.comments}</p>
					</div>
					<div class="form-group">
						<label>Created By</label> 
						<p>${task.createdBy.name}</p>
					</div>
					<div class="form-group">
						<label>Created At</label> 
						<p>${task.createdDate}</p>
					</div>
					<div class="form-group">
						<label for="calDob">Assigned To</label> 
						<p>${task.assignee.name}</p>
					</div>
					<div class="form-group">
						<label for="calDob">Priority</label> 
						<p>${task.priority}</p>
					</div>
					<a class="btn btn-success glyphicon glyphicon-pencil" href='<c:url value="/tasks/${task.id}/edit"/>'>Edit</a>
					<a href="../tasks" class="btn btn-primary">Back to Task list</a>
				<%-- </form:form> --%>
			</div>
		</div>

	</div>
</body>
</html>
