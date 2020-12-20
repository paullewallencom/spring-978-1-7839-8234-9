<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<jsp:include page="/WEB-INF/views/theme.jsp"></jsp:include>
<title>Taskify!: New Task</title>
<style type="text/css">
@media ( min-width : 1200px) {
	.container {
		max-width: 570px;
	}
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/navbar.jsp"></jsp:include>
	<h1 class="text-center">Create a New Task</h1>
	<hr>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Enter task details here..</h3>
			</div>
			<div class="panel-body">

				<form:form action="new" method="post" commandName="task">
					<spring:bind path="name">
						<div class="form-group${status.error ? ' has-error' : ''}">
							<label for="txtTaskName">Task-name</label>
							<form:input path="name" class="form-control" id="txtTaskName"
								placeholder="Task Name" />
							<form:errors path="name" cssClass="control-label" />
						</div>
					</spring:bind>
					
					<div class="form-group">
						<label for="txtComments">Comments</label>
						<form:textarea path="comments" class="form-control"
							id="txtComments" placeholder="Comments" rows="5" cols="30" />
					</div>
					
					<spring:bind path="createdBy.id">
						<div class="form-group${status.error ? ' has-error' : ''}">
							<label for="selectCreatedBy">Created By</label>
							<form:select path="createdBy" id="selectCreatedBy"
								class="form-control">
								<form:option value="-1" label="----------- Select -----------"></form:option>
								<form:options items="${users}" itemValue="id" itemLabel="name" />
							</form:select>
							<form:errors path="createdBy.id" cssClass="control-label" />
						</div>
					</spring:bind>
					
					<spring:bind path="assignee.id">
						<div class="form-group${status.error ? ' has-error' : ''}">
							<label for="selectAssignedTo" class="control-label">Assigned
								To</label>
							<form:select path="assignee" id="selectAssignedTo"
								class="form-control">
								<form:option value="-1" label="----------- Select -----------"></form:option>
								<form:options items="${users}" itemValue="id" itemLabel="name" />
							</form:select>
							<form:errors path="assignee.id" cssClass="control-label" />
						</div>
					</spring:bind>

					<spring:bind path="priority">
						<div class="form-group${status.error ? ' has-error' : ''}">
							<label for="selectPriority">Priority</label>
							<form:select path="priority" id="selectPriority"
								class="form-control">
								<form:options items="${priorities}" />
							</form:select>
							<form:errors path="priority" cssClass="control-label" />
						</div>
					</spring:bind>
					<!-- 
					<div class="from-group error">
					<label></label>
						<form:errors path="assignee"></form:errors>
					</div>
					-->

					<button type="submit" class="btn btn-success">Save</button>
					<a href='<spring:url value="/tasks/new"/>'
						class="btn btn-warning">Cancel</a>
				</form:form>
			</div>
		</div>

	</div>
</body>
</html>
