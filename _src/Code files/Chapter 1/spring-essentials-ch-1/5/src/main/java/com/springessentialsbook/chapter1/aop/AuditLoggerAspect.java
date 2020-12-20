package com.springessentialsbook.chapter1.aop;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component("auditLoggerAspect")
@Aspect
public class AuditLoggerAspect {
	private static final Logger logger = LoggerFactory
			.getLogger(AuditLoggerAspect.class);

	@Pointcut("execution(* com.springessentialsbook.chapter1.service.TaskService.createTask(..))")
	private void createTaskPointCut() {
	}

	@Pointcut("execution(* com.springessentialsbook.chapter1.service.*.*(..))")
	private void allServiceMethods() {
	}

	@Pointcut("execution(public * *(..))")
	private void anyPublicOperation() {
	}

	@Pointcut("anyPublicOperation() && allServiceMethods()")
	private void allPublicServiceMethods() {
	}

	@Pointcut("within(com.springessentialsbook.chapter1.service..*)")
	private void allServiceClasses() {
	}

	@Pointcut("execution(* set*(..))")
	private void allSetMethods() {
	}

	@Pointcut("target(com.springessentialsbook.chapter1.service.TaskService)")
	private void allTaskServiceImplMethods() {
	}

	@Pointcut("execution(* com.springessentialsbook.chapter1.service.TaskService.*(..))")
	private void allTaskServiceMethods() {
	}

	/*
	 * @Before("createTaskPointCut()") private void logBeforeCreateTaskAdvice()
	 * { logger.info(
	 * "*************** logBeforeCreateTaskAdvice invoked ZZZZ !****************"
	 * ); }
	 * 
	 * @Before("allServiceClasses()") private void
	 * logBeforeAllServiceClassMethods() { logger.info(
	 * "*************** logBeforeAllServiceClassMethods invoked XXXXXXXXXX !****************"
	 * ); }
	 * 
	 * @Around("allServiceClasses()") private void
	 * logAroundAllServiceClassMethods() { logger.info(
	 * "*************** logAroundAllServiceClassMethods invoked YYYYY !****************"
	 * ); }
	 */

	@Before("createTaskPointCut() and args(name, priority, createdByuserId, assigneeUserId)")
	private void logBeforeCreateTaskAdvice(String name, int priority,
			int createdByuserId, int assigneeUserId) {
		logger.info("*************** logBeforeCreateTaskAdvice invoked ZZZZ !**************** ");
		logger.info("name = " + name + "; priority = " + priority
				+ "; createdByuserId = " + createdByuserId);
	}

	@Before("createTaskPointCut()")
	private void logBeforeCreateTaskAdvice(JoinPoint joinpoint) {
		logger.info("*************** logBeforeCreateTaskAdvice invoked ZZZZ !**************** joinpoint = "
				+ joinpoint);
		logger.info("joinpoint.args = " + Arrays.asList(joinpoint.getArgs()));
	}

//	@Around("execution(* com.springessentialsbook.chapter1.service.**.find*(..))")
	private Object profileServieFindAdvice(ProceedingJoinPoint jPoint) throws Throwable {
		Date startTime = new Date();
		Object result = jPoint.proceed(jPoint.getArgs());
		Date endTime = new Date();
		logger.info("Time taken to execute operation: " + jPoint.getSignature() + " is " + (endTime.getTime() - startTime.getTime()) + " ms");
		return result;
	}

	/*
	 * @Before(
	 * "execution (* com.springessentialsbook.chapter1.service.TaskService.*(..))"
	 * ) public void logActionBeforeExecution() { logger.info(
	 * "*************** logActionBeforeExecution invoked ZZZZ !****************"
	 * ); }
	 * 
	 * @After(
	 * "execution (* com.springessentialsbook.chapter1.service.TaskService.*(..))"
	 * ) public void logActionAfterExecution() { logger.info(
	 * "*************** logActionAfterExecution invoked ZZZZ !****************"
	 * ); }
	 * 
	 * @Pointcut("within(com.springessentialsbook.chapter1.service..*)") public
	 * void logActionOnExecution() { logger.info(
	 * "*************** logActionOnExecution invoked ZZZZ !****************"); }
	 * 
	 * @Pointcut("within(* createTask(..))") public void logActionWithin() {
	 * logger
	 * .info("*************** logActionWithin invoked YYYY !****************");
	 * }
	 */
}
