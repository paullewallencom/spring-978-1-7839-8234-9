<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<head>
<jsp:include page="/WEB-INF/views/theme.jsp"></jsp:include>
<title>Taskify :: List of tasks</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/navbar.jsp"></jsp:include>
<div class="container">
	<h1>List of tasks</h1>

	<hr />
	<p class="text-right">
		<a class="btn btn-primary" href="tasks/new" role="button">Create New
			Task</a>
	</p>
		<ul class="nav nav-pills">
		<!--
			<li role="presentation" ${status == 'All' ? 'class="active"' : ''}><a href="tasks">All</a></li>
			<li role="presentation" ${status == 'Open' ? 'class="active"' : ''}><a href="tasks?status=Open">Open</a></li>
			<li role="presentation" ${status == 'Closed' ? 'class="active"': ''}><a href="tasks?status=Closed">Closed</a></li>
			-->
			
			<li role="presentation" class="${status == 'All' ? 'active' : ''}"><a href='<spring:url value="/tasks"/>'>All Tasks</a></li>
			<li role="presentation" class="${status == 'Open' ? 'active' : ''}"><a href='<spring:url value="/tasks?status=Open"/>'>Open Tasks</a></li>
			<li role="presentation" class="${status == 'Closed' ? 'active': ''}"><a href='<spring:url value="/tasks?status=Closed"/>'>Closed Tasks</a></li>

		</ul>
	
	<table class="table table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>Task</th>
				<th>Status</th>
				<th>Priority</th>
				<th>Created By</th>
				<th>Assigned To</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty tasks}">

					<c:forEach var="task" items="${tasks}">
						<tr>
							<td><a href='<c:url value="/tasks/${task.id}"/>'>${task.id}</a></td>
							<td>${task.name}</td>
							<td>${task.status}</td>
							<td>${task.priority}</td>
							<td>${task.createdBy.name}</td>
							<td>${task.assignee.name}</td>
						</tr>
					</c:forEach>

			</c:if>

		</tbody>
	</table>
</div>
</body>
</html>
