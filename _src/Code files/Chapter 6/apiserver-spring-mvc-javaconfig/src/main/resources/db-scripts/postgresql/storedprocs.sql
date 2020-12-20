
CREATE OR REPLACE FUNCTION CREATE_TASK(v_name VARCHAR(200), v_STATUS VARCHAR(50), v_priority INTEGER, v_createdUserId INTEGER, v_createdDate DATE, v_assignedUserId INTEGER, v_comment VARCHAR(2000))
	
	RETURNS INTEGER AS $v_newID$
	
	DECLARE
		v_newID INTEGER;
	
	BEGIN
		INSERT INTO TBL_TASK(NAME, STATUS, PRIORITY, CREATED_USER_ID, CREATED_DATE, ASSIGNEE_USER_ID, COMMENTS) 
		VALUES(v_name, v_status, v_priority, v_createdUserId, v_createdDate, v_assignedUserId, v_comment)
		RETURNING ID INTO v_newID;
		RETURN v_newID;
	END;
	$v_newID$ LANGUAGE plpgsql
/
--
--CREATE PROCEDURE CREATE_TASK(OUT v_newID INTEGER, IN v_name VARCHAR(200), IN v_STATUS VARCHAR(50), IN v_priority INTEGER, IN v_createdUserId INTEGER, IN v_createdDate DATE, IN v_assignedUserId INTEGER, IN v_comment VARCHAR(2000))
--	
--	MODIFIES SQL DATA
--	
--	BEGIN ATOMIC
--		INSERT INTO TBL_TASK(NAME, STATUS, PRIORITY, CREATED_USER_ID, CREATED_DATE, ASSIGNEE_USER_ID, COMMENTS) VALUES(v_name, v_status, v_priority, v_createdUserId, v_createdDate, v_assignedUserId, v_comment);
--		SET v_newID = IDENTITY();
--	END;
--	
--/
--
--
--CREATE PROCEDURE GET_TASKS_BY_STATUS(IN v_status VARCHAR(20))
--	
--	READS SQL DATA
--	
--	BEGIN ATOMIC
--	
--	DECLARE RESULT CURSOR WITH RETURN FOR SELECT ID, NAME, STATUS, PRIORITY, CREATED_USER_ID, CREATED_DATE, ASSIGNEE_USER_ID FROM TBL_TASK WHERE STATUS = v_status FOR READ ONLY;
--	
--	OPEN RESULT;
--	
--	END
--/
--
--CREATE PROCEDURE GET_TASKS_BY_STATUS_ASSIGNEE_ID(IN v_status VARCHAR(20), IN v_assignee_id INTEGER)
--	
--	READS SQL DATA
--	
--	BEGIN ATOMIC
--	
--	DECLARE RESULT CURSOR WITH RETURN FOR SELECT ID, NAME, STATUS, PRIORITY, CREATED_USER_ID, CREATED_DATE, ASSIGNEE_USER_ID FROM TBL_TASK WHERE STATUS = v_status AND ASSIGNEE_USER_ID = v_assignee_id FOR READ ONLY;
--	
--	OPEN RESULT;
--	
--	END
--/
--
--CREATE PROCEDURE GET_TASKS_BY_STATUS_ASSIGNEE_USERNAME(IN v_status VARCHAR(20), IN v_assignee_name VARCHAR(20))
--	
--	READS SQL DATA
--	
--	BEGIN ATOMIC
--	
--	DECLARE RESULT CURSOR WITH RETURN FOR SELECT ID, NAME, STATUS, PRIORITY, CREATED_USER_ID, CREATED_DATE, ASSIGNEE_USER_ID FROM TBL_TASK WHERE STATUS = v_status AND ASSIGNEE_USER_ID = (SELECT ID FROM TBL_USER WHERE USER_NAME = v_assignee_name) FOR READ ONLY;
--	
--	OPEN RESULT;
--	
--	END
--	
--/
--
--CREATE PROCEDURE GET_TASKS_BY_ASSIGNEE_ID(v_assignee_id INTEGER)
--	
--	READS SQL DATA
--	
--	BEGIN ATOMIC
--	
--	DECLARE RESULT CURSOR WITH RETURN FOR SELECT ID, NAME, STATUS, PRIORITY, CREATED_USER_ID, CREATED_DATE, ASSIGNEE_USER_ID FROM TBL_TASK WHERE ASSIGNEE_USER_ID = v_assignee_id FOR READ ONLY;
--	
--	OPEN RESULT;
--	
--	END
--	
--/
--
--CREATE PROCEDURE GET_TASKS_BY_ASSIGNEE_NAME(v_assignee_name VARCHAR(20))
--	
--	READS SQL DATA
--	
--	BEGIN ATOMIC
--	
--	DECLARE RESULT CURSOR WITH RETURN FOR SELECT ID, NAME, STATUS, PRIORITY, CREATED_USER_ID, CREATED_DATE, ASSIGNEE_USER_ID FROM TBL_TASK WHERE ASSIGNEE_USER_ID = (SELECT ID FROM TBL_USER WHERE USER_NAME = v_assignee_name) FOR READ ONLY;
--	
--	OPEN RESULT;
--	
--	END
--	
--/
--
--
--
--
--CREATE FUNCTION GET_USER_ID (in_name VARCHAR(200))
--RETURNS INTEGER READS SQL DATA 
--SPECIFIC GET_USER_ID
--BEGIN ATOMIC
--  declare out_id INTEGER;
--  select id into out_id from tbl_user where user_name = in_name;
--  RETURN out_id;
--END
--/
--
--GRANT EXECUTE ON SPECIFIC ROUTINE GET_USER_ID to PUBLIC
--
--/






org.springframework.jdbc.UncategorizedSQLException: CallableStatementCallback; uncategorized SQLException for SQL [{? = call springessentials.get_tasks_by_status(?)}]; SQL state [34000]; error code [0]; ERROR: cursor "<unnamed portal 1>" does not exist; nested exception is org.postgresql.util.PSQLException: ERROR: cursor "<unnamed portal 1>" does not exist
	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:84)
	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:81)
	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:81)
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:1094)
	at org.springframework.jdbc.core.JdbcTemplate.call(JdbcTemplate.java:1130)
	at org.springframework.jdbc.core.simple.AbstractJdbcCall.executeCallInternal(AbstractJdbcCall.java:403)
	at org.springframework.jdbc.core.simple.AbstractJdbcCall.doExecute(AbstractJdbcCall.java:363)
	at org.springframework.jdbc.core.simple.SimpleJdbcCall.executeFunction(SimpleJdbcCall.java:164)
	at com.springessentials.chapter3.dao.impl.PostgresTaskJdbcDAO.findAllCompletedTasks(PostgresTaskJdbcDAO.java:207)
	at com.springessentials.chapter3.service.impl.TaskServiceImpl.findAllCompletedTasks(TaskServiceImpl.java:104)
	at com.springessentials.chapter3.test.PostgresqlSpringJdbcTest.findCompletedTasks(PostgresqlSpringJdbcTest.java:180)
	at com.springessentials.chapter3.test.PostgresqlSpringJdbcTest.run(PostgresqlSpringJdbcTest.java:98)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:483)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks.evaluate(RunBeforeTestMethodCallbacks.java:74)
	at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:27)
	at org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks.evaluate(RunAfterTestMethodCallbacks.java:85)
	at org.springframework.test.context.junit4.statements.SpringRepeat.evaluate(SpringRepeat.java:86)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:241)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:87)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:180)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
Caused by: org.postgresql.util.PSQLException: ERROR: cursor "<unnamed portal 1>" does not exist
	at org.postgresql.core.v3.QueryExecutorImpl.receiveErrorResponse(QueryExecutorImpl.java:2198)
	at org.postgresql.core.v3.QueryExecutorImpl.processResults(QueryExecutorImpl.java:1927)
	at org.postgresql.core.v3.QueryExecutorImpl.execute(QueryExecutorImpl.java:255)
	at org.postgresql.jdbc2.AbstractJdbc2Statement.execute(AbstractJdbc2Statement.java:562)
	at org.postgresql.jdbc2.AbstractJdbc2Statement.executeWithFlags(AbstractJdbc2Statement.java:406)
	at org.postgresql.jdbc2.AbstractJdbc2Connection.execSQLQuery(AbstractJdbc2Connection.java:362)
	at org.postgresql.jdbc2.AbstractJdbc2ResultSet.internalGetObject(AbstractJdbc2ResultSet.java:207)
	at org.postgresql.jdbc3.AbstractJdbc3ResultSet.internalGetObject(AbstractJdbc3ResultSet.java:36)
	at org.postgresql.jdbc4.AbstractJdbc4ResultSet.internalGetObject(AbstractJdbc4ResultSet.java:300)
	at org.postgresql.jdbc2.AbstractJdbc2ResultSet.getObject(AbstractJdbc2ResultSet.java:2704)
	at org.postgresql.jdbc2.AbstractJdbc2Statement.executeWithFlags(AbstractJdbc2Statement.java:457)
	at org.postgresql.jdbc2.AbstractJdbc2Statement.execute(AbstractJdbc2Statement.java:413)
	at org.springframework.jdbc.core.JdbcTemplate$6.doInCallableStatement(JdbcTemplate.java:1133)
	at org.springframework.jdbc.core.JdbcTemplate$6.doInCallableStatement(JdbcTemplate.java:1130)
	at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:1078)
	... 38 more

