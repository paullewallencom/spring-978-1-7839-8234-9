
-- User Table

CREATE TABLE TBL_USER (
	ID INTEGER IDENTITY,
	NAME VARCHAR(200) NOT NULL,
	USER_NAME VARCHAR(25) NOT NULL,
	PASSWORD VARCHAR(20) NOT NULL,
	DOB DATE,
	PROFILE_IMAGE_ID INTEGER,
	PROFILE_IMAGE_NAME VARCHAR(200)
);
/
-- Task Table
--
--CREATE TABLE TBL_TASK (
--	ID INTEGER IDENTITY,
--	NAME VARCHAR(200) NOT NULL,
--	STATUS VARCHAR(50) NOT NULL,
--	PRIORITY INTEGER DEFAULT 0 NOT NULL ,
--	CREATED_USER_ID INT NOT NULL,
--	CREATED_DATE DATE DEFAULT current_date,
--	ASSIGNEE_USER_ID INT,
--	COMPLETED_DATE DATE,
--	PROFILE_FILE_ID INTEGER,
--	PROFILE_FILE_NAME VARCHAR(200)
--);

CREATE TABLE TBL_TASK (
	ID INTEGER IDENTITY,
	NAME VARCHAR(200) NOT NULL,
	STATUS VARCHAR(50) NOT NULL,
	PRIORITY INTEGER DEFAULT 0 NOT NULL ,
	CREATED_USER_ID INT NOT NULL,
	CREATED_DATE DATE DEFAULT current_date,
	ASSIGNEE_USER_ID INT,
	COMPLETED_DATE DATE,
	COMMENTS VARCHAR(2000)
);
/
alter table TBL_TASK add constraint FK_TASK_CREATOR foreign key (CREATED_USER_ID) references TBL_USER(ID);
/
alter table TBL_TASK add constraint FK_TASK_ASSIGNEE foreign key (ASSIGNEE_USER_ID) references TBL_USER(ID);


-- File Tables
/
CREATE TABLE TBL_TASK_FILE (
	ID INTEGER IDENTITY,
	NAME VARCHAR(200) NOT NULL,
	TASK_ID INT
);
/
alter table TBL_TASK_FILE add constraint FK_FILE_TASK foreign key (TASK_ID) references TBL_TASK(ID);
/