
-- User Table

INSERT INTO TBL_USER(NAME, USER_NAME, PASSWORD, DOB) VALUES('Shameer Kunjumohamed', 'sameerean', 'password123', to_date('08/02/1977', 'DD/MM/YYYY'));
/
INSERT INTO TBL_USER(NAME, USER_NAME, PASSWORD, DOB) VALUES('Hamid Reza Sattari', 'hamid', 'password345', to_date('01/01/1975', 'DD/MM/YYYY'));
/
-- Task Table

--INSERT INTO TBL_TASK (NAME, STATUS, PRIORITY, CREATED_USER_ID, CREATED_DATE, ASSIGNEE_USER_ID, COMPLETED_DATE)
--VALUES ('');
--
--alter table TBL_TASK add constraint FK_TASK_CREATOR foreign key (CREATED_USER_ID) references TBL_USER(ID);
--alter table TBL_TASK add constraint FK_TASK_ASSIGNEE foreign key (ASSIGNEE_USER_ID) references TBL_USER(ID);

