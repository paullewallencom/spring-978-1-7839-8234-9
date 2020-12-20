package com.springessentialsbook.chapter1.service.util;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.springessentialsbook.chapter1.domain.Task;
import com.springessentialsbook.chapter1.service.TaskService;

@Component
public class TaskSnapshotBuilder {
	
	@Autowired
	private TaskService taskService;
	
	public TaskSnapShot buildTaskSnapShot() {
		TaskSnapShot snapshot = new TaskSnapShot();

		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext(taskService);

		Expression exp = parser.parseExpression("findAllTasks().size()");
		snapshot.setTotalTasks(exp.getValue(context).toString());

		exp = parser.parseExpression("findAllTasks()");
		snapshot.setTaskList((List<Task>)exp.getValue(context));

		exp = parser.parseExpression("new java.util.Date()");
		snapshot.setReportTime((Date)exp.getValue(context));

		exp = parser.parseExpression("findAllTasks().?[assignee.userName == 'hamid']");
		snapshot.setHamidTasks((List<Task>)exp.getValue(context));

		exp = parser.parseExpression("findAllTasks().?[status == 'Open']");
		snapshot.setOpenTasks((List<Task>)exp.getValue(context));

		return snapshot;
	}

}
