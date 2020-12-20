
CREATE OR REPLACE FUNCTION GET_TASK_COUNT (v_status VARCHAR(20))
	
	RETURNS INTEGER AS $v_count$
	
	DECLARE 
		v_count INTEGER;
	BEGIN
	
		IF v_status = 'All' THEN
			SELECT COUNT(*) INTO v_count FROM TBL_TASK;
		ELSE
			SELECT COUNT(*) INTO v_count FROM TBL_TASK WHERE STATUS = v_status;
		END IF;
		
		RETURN v_count;
	
	END;
	$v_count$ LANGUAGE plpgsql

/


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


CREATE OR REPLACE FUNCTION UPDATE_TASK(v_id INTEGER, v_name VARCHAR(200), v_STATUS VARCHAR(50), v_priority INTEGER, v_assignedUserId INTEGER, v_comment VARCHAR(2000))
	RETURNS VOID AS $$
	
	DECLARE 
		v_completedDate DATE;
	BEGIN
		
		IF v_STATUS = 'Complete' THEN
			v_completedDate:= CURRENT_TIMESTAMP;
--			SELECT CURRENT_TIMESTAMP into v_completedDate;
		END IF;
		UPDATE TBL_TASK SET NAME = v_name, STATUS = v_STATUS, priority = v_priority, ASSIGNEE_USER_ID = v_assignedUserId, COMPLETED_DATE = v_completedDate, COMMENTS = v_comment WHERE id = v_id;
	END;
	$$ LANGUAGE plpgsql
/

DROP FUNCTION if exists get_tasks_by_status(v_status VARCHAR(20));
/

--CREATE OR REPLACE FUNCTION GET_TASKS_BY_STATUS(v_status VARCHAR(20))
--	
--	RETURNS TABLE(ID INTEGER,
--		NAME VARCHAR(200),
--		STATUS VARCHAR(50),
--		PRIORITY INTEGER,
--		CREATED_USER_ID INT,
--		CREATED_DATE DATE,
--		ASSIGNEE_USER_ID INT,
--		COMPLETED_DATE DATE,
--		COMMENTS VARCHAR(2000)
--	) AS $$
--	
--	DECLARE
--		v_tasks TABLE;
--	BEGIN 
--		v_tasks:= SELECT task.ID, task.NAME, task.STATUS, task.PRIORITY, task.CREATED_USER_ID, task.CREATED_DATE, task.ASSIGNEE_USER_ID, task.COMPLETED_DATE, task.COMMENTS FROM TBL_TASK task WHERE task.STATUS = v_status;
--		return v_tasks;
--	END;
--	$$ LANGUAGE plpgsql VOLATILE;
--	
--/

CREATE OR REPLACE FUNCTION GET_TASKS_BY_STATUS(v_status VARCHAR(20))
	
	RETURNS REFCURSOR AS $$
	
	DECLARE
		ref REFCURSOR;
	BEGIN 
		OPEN ref FOR SELECT task.ID, task.NAME, task.STATUS, task.PRIORITY, task.CREATED_USER_ID, task.CREATED_DATE, task.ASSIGNEE_USER_ID, task.COMPLETED_DATE, task.COMMENTS FROM TBL_TASK task WHERE task.STATUS = v_status;
		return ref;
	END;
	$$ LANGUAGE plpgsql;
	
/
--
--CREATE OR REPLACE FUNCTION GET_TASKS_BY_STATUS_2(v_status VARCHAR(20))
--	
--	RETURNS REFCURSOR AS $$
--	
----	DECLARE
----		ref REFCURSOR;
--	BEGIN 
--		SELECT GET_TASKS_BY_STATUS_INNER('tasks_cur');
--		return FETCH ALL IN 'tasks_cur';
--	END;
--	$$ LANGUAGE plpgsql;
--	
--/

--CREATE FUNCTION get_user_id (in_name VARCHAR(200))
--RETURNS INTEGER READS SQL DATA 
--BEGIN ATOMIC
--  declare out_id INTEGER;
--  select id into out_id from tbl_user where user_name = in_name;
--  RETURN out_id;
--END
-- 

