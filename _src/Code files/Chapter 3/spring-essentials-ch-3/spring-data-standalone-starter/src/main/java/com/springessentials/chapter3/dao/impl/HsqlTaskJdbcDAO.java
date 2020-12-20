package com.springessentials.chapter3.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Repository;

import com.springessentials.chapter3.dao.TaskDAO;
import com.springessentials.chapter3.dao.UserDAO;
import com.springessentials.chapter3.domain.Task;
import com.springessentials.chapter3.domain.User;

@Repository("HsqlTaskJdbcDAO")
public class HsqlTaskJdbcDAO implements TaskDAO {

	private JdbcTemplate jdbcTemplate;
	private RowMapper<Task> taskRowMapper;
	private SimpleJdbcCall createTaskStoredProc;
	private SimpleJdbcCall findTasksByStatusAssigneeIdStoredProc;
	private SimpleJdbcCall tasksCountFunction;
	private SimpleJdbcCall tasksByStatusStoredProc;
	private SimpleJdbcCall openTasksByAssigneeIdStoreProc;
	private UserDAO userDAO;
	

	private List<Task> taskDataSource;

	@Autowired
	public HsqlTaskJdbcDAO(DataSource dataSource, @Qualifier("UserJdbcDAO") UserDAO userDAO) {
		// this.taskDataSource = new ArrayList<Task>();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.jdbcTemplate.setResultsMapCaseInsensitive(true);
		this.createTaskStoredProc = new SimpleJdbcCall(dataSource).withProcedureName("CREATE_TASK")
				.declareParameters(new SqlOutParameter("v_newID", Types.INTEGER),
						new SqlParameter("v_name", Types.VARCHAR),
						new SqlParameter("v_STATUS", Types.VARCHAR),
						new SqlParameter("v_priority", Types.INTEGER),
						new SqlParameter("v_createdUserId", Types.INTEGER),
						new SqlParameter("v_createdDate", Types.DATE),
						new SqlParameter("v_assignedUserId", Types.INTEGER),
						new SqlParameter("v_comment", Types.VARCHAR));
		
		this.tasksCountFunction = new SimpleJdbcCall(jdbcTemplate).withFunctionName("get_user_id");
//		this.tasksCountFunction = new SimpleJdbcCall(jdbcTemplate).withFunctionName("GET_TASK_COUNT");
//				.declareParameters(new SqlParameter("v_status", Types.VARCHAR)).withReturnValue();
		
		this.taskRowMapper = new RowMapper<Task>() {

			@Override
			public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
				Task task = new Task();
				task.setId(rs.getLong("id"));
				Long assigneeId = rs.getLong("assignee_user_id");
				if (assigneeId != null)
					task.setAssignee(userDAO.findById(assigneeId));
				task.setComments(rs.getString("comments"));
				task.setCompletedDate(rs.getDate("completed_date"));
				task.setCreatedBy(userDAO.findById(rs.getLong("created_user_id")));
				task.setCreatedDate(rs.getDate("created_date"));
				task.setName(rs.getString("name"));
				task.setPriority(rs.getInt("priority"));
				task.setStatus(rs.getString("status"));
				return task;
			}
			
		};
		
		this.tasksByStatusStoredProc = new SimpleJdbcCall(dataSource)
				.withProcedureName("GET_TASKS_BY_STATUS")
				.returningResultSet("tasks",this.taskRowMapper);
		
		this.openTasksByAssigneeIdStoreProc = new SimpleJdbcCall(dataSource)
				.withProcedureName("GET_TASKS_BY_STATUS_ASSIGNEE_ID")
				.returningResultSet("tasks",this.taskRowMapper);
		
		this.userDAO = userDAO;
	}

	@Override
	public void createTask(Task task) {

		SqlParameterSource inParams = new MapSqlParameterSource().addValue("v_name", task.getName())
				.addValue("v_STATUS", task.getStatus()).addValue("v_priority", task.getPriority())
				.addValue("v_createdUserId", task.getCreatedBy().getId())
				.addValue("v_createdDate", task.getCreatedDate())
				.addValue("v_assignedUserId", task.getAssignee() == null ? null : task.getAssignee().getId())
				.addValue("v_comment", task.getComments());

		Map<String, Object> out = createTaskStoredProc.execute(inParams);

		task.setId(Long.valueOf(out.get("v_newID").toString()));
	}

	@Override
	public Task findById(Long taskId) {
		MappingSqlQuery<Task> query = 
		new MappingSqlQuery<Task>() {

			@Override
			protected Task mapRow(ResultSet rs, int rowNum) throws SQLException {
				return taskRowMapper.mapRow(rs,rowNum);
			}
			
		};
		query.setJdbcTemplate(jdbcTemplate);
		query.setSql("select id, name, status, priority, created_user_id, created_date"
				+ ", assignee_user_id, completed_date, comments from tbl_task where id = ?");
		query.declareParameter(new SqlParameter("id", Types.INTEGER));
		
		return query.findObject(taskId);
		
	}

	@Override
	public List<Task> findByAssignee(Long assigneeId) {
		MappingSqlQuery<Task> query = 
		new MappingSqlQuery<Task>() {

			@Override
			protected Task mapRow(ResultSet rs, int rowNum) throws SQLException {
				return taskRowMapper.mapRow(rs,rowNum);
			}
			
		};
		query.setJdbcTemplate(jdbcTemplate);
		query.setSql("select id, name, status, priority, created_user_id, created_date"
				+ ", assignee_user_id, completed_date, comments from tbl_task where assignee_user_id = ?");
		query.declareParameter(new SqlParameter("id", Types.INTEGER));
		
		return query.execute(assigneeId);
	}

	@Override
	public List<Task> findByAssignee(String assigneeUserName) {
		User assignee = userDAO.findByUserName(assigneeUserName);
		return findByAssignee(assignee.getId());
	}

	@Override
	public List<Task> findAllTasks() {
		MappingSqlQuery<Task> query = 
		new MappingSqlQuery<Task>() {

			@Override
			protected Task mapRow(ResultSet rs, int rowNum) throws SQLException {
				return taskRowMapper.mapRow(rs,rowNum);
			}
		};
		query.setJdbcTemplate(jdbcTemplate);
		query.setSql("select id, name, status, priority, created_user_id, created_date"
				+ ", assignee_user_id, completed_date, comments from tbl_task");
//		query.declareParameter(new SqlParameter("id", Types.INTEGER));
		
		return query.execute();
	}

	@Override
	public int findAllTasksCount() {
/*		SqlParameterSource inParams = new MapSqlParameterSource().addValue("v_status", "All");
		return this.tasksCountFunction.executeFunction(Integer.class, inParams);
*/		
		SqlParameterSource inParams = new MapSqlParameterSource().addValue("in_name", "sameerean");
		return this.tasksCountFunction.executeFunction(Integer.class, inParams);
		
	}

	@Override
	public List<Task> findAllOpenTasks() {
		
		return (List<Task>)this.tasksByStatusStoredProc.execute("Open").get("tasks");
	}

	@Override
	public List<Task> findAllCompletedTasks() {
		return (List<Task>)this.tasksByStatusStoredProc.execute("Completed").get("tasks");
	}

	@Override
	public int findAllOpenTasksCount() {
		return this.tasksCountFunction.executeFunction(Integer.class, new MapSqlParameterSource("v_status", "Open"));
	}

	@Override
	public int findAllCompletedTasksCount() {
		return this.tasksCountFunction.executeFunction(Integer.class, new MapSqlParameterSource("v_status", "Completed"));
	}

	@Override
	public List<Task> findOpenTasksByAssignee(Long assigneeId) {
		
		SimpleJdbcCall procCall = new SimpleJdbcCall(jdbcTemplate.getDataSource()).withProcedureName("GET_TASKS_BY_STATUS_ASSIGNEE_ID")
				.returningResultSet("RESULT", taskRowMapper);
		
		SqlParameterSource inParams = new MapSqlParameterSource().addValue("v_status", "Open")
				.addValue("v_assignee_id", assigneeId);

		Map<String, Object> out = procCall.execute(inParams);
		return (List<Task>) out.get("RESULT");
	}

	@Override
	public List<Task> findOpenTasksByAssignee(String assigneeUserName) {
		return taskDataSource.parallelStream()
				.filter(task -> Objects.equals(task.getStatus(), "Open") && Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getUserName(), assigneeUserName))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findCompletedTasksByAssignee(Long assigneeId) {
		return taskDataSource.parallelStream().filter(task -> Objects.equals(task.getStatus(), "Completed")
				&& Objects.nonNull(task.getAssignee()) && Objects.equals(task.getAssignee().getId(), assigneeId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Task> findCompletedTasksByAssignee(String assigneeUserName) {
		return taskDataSource.parallelStream()
				.filter(task -> Objects.equals(task.getStatus(), "Completed") && Objects.nonNull(task.getAssignee())
						&& Objects.equals(task.getAssignee().getUserName(), assigneeUserName))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteTask(Task task) {
		taskDataSource.remove(task);
	}

	@Override
	public void updateTask(Task task) {
		throw new UnsupportedOperationException();
		
	}

}
