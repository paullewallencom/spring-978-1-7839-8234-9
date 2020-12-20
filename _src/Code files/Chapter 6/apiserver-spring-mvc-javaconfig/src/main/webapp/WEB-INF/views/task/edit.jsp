<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<jsp:include page="/WEB-INF/views/theme.jsp"></jsp:include>

<style type="text/css">
@media ( min-width : 1200px) {
	.container {
		max-width: 570px;
	}
}
</style>
<title>Spring MVC JPA Starter: Edit Task</title>
<script type="text/javascript">
	function deleteTask(_id) {
		if (confirm("Delete... PID = " + _id + " ??? ")) {
			with (document.forms[0]) {
				_method.value = "DELETE";
				submit();
			}
		}
	}
	function completeTask(_id) {
		if (confirm("Complete... Task = " + _id + " ??? ")) {
			with (document.forms[0]) {
				action="../" + _id + "/complete";
				submit();
			}
		}
	}
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/navbar.jsp"></jsp:include>
	<h1 class="text-center">Edit Task</h1>
	<hr>
	<div class="container">
		<div class="panel panel-default span4">
			<div class="panel-heading">
				<h3 class="panel-title">Enter task details here..</h3>
			</div>
			<div class="panel-body">

				<form:form action="../${task.id}" method="PUT" commandName="task">
					<div class="form-group">
						<label for="txtTaskName">Task-name</label>
						<form:input path="name" class="form-control" id="txtTaskName"
							placeholder="Task Name" />
					</div>
					<div class="form-group">
						<label for="txtComments">Comments</label>
						<form:textarea path="comments" class="form-control"
							id="txtComments" placeholder="Comments" rows="5" cols="30" />
					</div>
					<div class="form-group">
						<label for="selectCreatedBy">Created By</label>
						<form:select path="createdBy" id="selectCreatedBy"
							class="form-control">
							<form:option value="-1" label="----------- Select -----------"></form:option>
							<form:options items="${users}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>
					<div class="form-group">
						<label for="selectAssignedTo">Assigned To</label>
						<form:select path="assignee" id="selectAssignedTo"
							class="form-control">
							<form:option value="-1" label="----------- Select -----------"></form:option>
							<form:options items="${users}" itemValue="id" itemLabel="name" />
						</form:select>
					</div>
					<div class="form-group">
						<label for="selectAssignedTo">Priority</label>
						<form:select path="priority" id="selectAssignedTo"
							class="form-control">
							<form:options items="${priorities}" />
						</form:select>
					</div>

					<button type="submit" class="btn btn-success">Save</button>
					<a href="javascript:completeTask('${task.id}');"
						class="btn btn-primary">Complete</a>
					<a href='<c:url value="/tasks/${task.id}"/>'
						class="btn btn-primary">Cancel</a>
					<a href="javascript:deleteTask('${task.id}');"
						class="btn btn-danger">Delete</a>
				</form:form>
			</div>
		</div>

	</div>
</body>
</html>
