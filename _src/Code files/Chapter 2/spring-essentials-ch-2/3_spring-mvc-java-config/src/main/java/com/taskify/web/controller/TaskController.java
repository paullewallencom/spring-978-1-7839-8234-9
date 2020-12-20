package com.taskify.web.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import com.taskify.domain.Task;
import com.taskify.domain.User;
import com.taskify.service.TaskService;
import com.taskify.service.UserService;
import com.taskify.web.model.CreateTaskRequest;
import com.taskify.web.model.CreateTaskResponse;
import com.taskify.web.validator.TaskValidator;

/**
 * Handles requests for task related pages.
 */
@Controller
public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	private static final int[] priorities = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	@RequestMapping(path = "/tasks", method = RequestMethod.GET)
	public String list(@RequestParam(name = "status", required = false) String status, Model model) {

		logger.info(">>>>>>>>>>>>>>>>>> inside " + this + ".list() >>>>>>>>>>>> Status = " + status);

		model.addAttribute("status", status);
		if (StringUtils.isEmpty(status)) {
			model.addAttribute("tasks", taskService.findAllTasks());
			model.addAttribute("status", "All");
		} else if (status.equals("Open"))
			model.addAttribute("tasks", taskService.findAllOpenTasks());
		else if (status.equals("Closed"))
			model.addAttribute("tasks", taskService.findAllClosedTasks());
		else
			throw new IllegalArgumentException(
					"Illegal value for status supplied: " + status + "; Only 'Open' and 'Closed' are supported.");

		return "task/list";
	}

	@RequestMapping(path = "/users/{userId}/tasks", method = RequestMethod.GET)
	public String listAllUserTasksByStatus(@PathVariable("userId") Long userId,
			@RequestParam(name = "status", required = false) String status, Model model) {
		model.addAttribute("assignee", userService.findById(userId));
		model.addAttribute("status", status);
		if (StringUtils.isEmpty(status)) {
			model.addAttribute("tasks", taskService.findTasksByAssignee(userId));
			model.addAttribute("status", "All");
		} else if (status.equals("Open"))
			model.addAttribute("tasks", taskService.findOpenTasksByAssignee(userId));
		else if (status.equals("Closed"))
			model.addAttribute("tasks", taskService.findClosedTasksByAssignee(userId));
		else
			throw new IllegalArgumentException(
					"Illegal value for status supplied: " + status + "; Only 'Open' and 'Closed' are supported.");
		return "task/list";
	}

	@RequestMapping(path = "/tasks/new", method = RequestMethod.GET)
	public String newTaskForm(Model model) {

		model.addAttribute("task", new Task());
		// model.addAttribute("priorities", priorities);
		// model.addAttribute("users", userService.findAllUsers());
		return "task/new";
	}

	@ModelAttribute("priorities")
	public int[] getTaskPriorities() {
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	}

	@ModelAttribute("users")
	public List<User> getUsersList() {
		return userService.findAllUsers();
	}

	@RequestMapping(path = "/tasks/new", method = RequestMethod.POST)
	public String createNewTask(@ModelAttribute("task") @Valid Task task, BindingResult result, Model model) {

		if(result.hasErrors()) {
			logger.error(">>>>>>> Validation ERROR : " + result.getAllErrors());
			return "task/new";
		} else {
			taskService.createTask(task);
			return "redirect:/tasks";
		}
	}

	@RequestMapping(path = "/tasks/{id}", method = RequestMethod.GET)
	public ModelAndView viewTask(@PathVariable("id") Long id) {
		return new ModelAndView("task/view").addObject("task", taskService.findTaskById(id)).addObject("priorities",
				priorities);
	}

	@RequestMapping(path = "/tasks/{id}", method = RequestMethod.PUT)
	public String saveTask(@ModelAttribute("task") @Valid Task task, BindingResult result, Model model) {

		// task.setAssignee(userService.findById(task.getAssignee().getId()));
		// task.setCreatedBy(userService.findById(task.getCreatedBy().getId()));

		Task existingTask = taskService.findTaskById(task.getId());
		existingTask.setAssignee(task.getAssignee());
		existingTask.setName(task.getName());
		existingTask.setPriority(task.getPriority());
		existingTask.setComments(task.getComments());

		return "redirect:/tasks/" + task.getId();
	}

	@RequestMapping(path = "/tasks/{id}/edit", method = RequestMethod.GET)
	public String editTask(@PathVariable("id") Long id, Model model) {

		model.addAttribute("task", taskService.findTaskById(id));
		model.addAttribute("priorities", priorities);
		model.addAttribute("users", userService.findAllUsers());
		return "task/edit";
	}

	@RequestMapping(path = "/tasks/{id}/complete", method = RequestMethod.PUT)
	public String completeTask(@PathVariable("id") Long id, @ModelAttribute("task") @Valid Task task,
			BindingResult result, Model model) {

		// taskService.completeTask(task);
		Task existingTask = taskService.findTaskById(task.getId());

		existingTask.setComments(task.getComments());
		existingTask.setStatus("Closed");
		existingTask.setCompletedDate(new Date());

		return "redirect:/tasks/" + id;
	}

	@RequestMapping(path = "/tasks/{id}", method = RequestMethod.DELETE)
	public String deleteTask(@PathVariable("id") Long id, Model model) {

		taskService.deleteTask(id);
		return "redirect:/tasks";
	}

	@InitBinder("task")
	public void initBinder(WebDataBinder binder) {
//		binder.addCustomFormatter(new DateFormatter("dd/MM/yyyy"));
		binder.addValidators(new TaskValidator());
	}

	@RequestMapping(path = "/tasks.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Task> listTasksJson(@RequestParam(name = "status", required = false) String status) {

		logger.info(">>>>>>>>>>>>>>>>>> inside " + this + ".listTasksJson() >>>>>>>>>>>> Status = " + status);

		if (StringUtils.isEmpty(status)) {
			return taskService.findAllTasks();
		} else if (status.equals("Open"))
			return taskService.findAllOpenTasks();
		else if (status.equals("Closed"))
			return taskService.findAllClosedTasks();
		else
			throw new IllegalArgumentException(
					"Illegal value for status supplied: " + status + "; Only 'Open' and 'Closed' are supported.");
	}

	@RequestMapping(path = "/tasks/new.json", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public CreateTaskResponse createNewTaskJSON(@RequestBody CreateTaskRequest createRequest) {

		Task task = new Task();
		task.setName(createRequest.getTaskName());
		task.setCreatedBy(userService.findById(createRequest.getCreatorId()));
		task.setAssignee(userService.findById(createRequest.getAssigneeId()));
		task.setComments(createRequest.getComments());
		task.setPriority(createRequest.getPriority());

		return new CreateTaskResponse(taskService.createTask(task));
	}

	@RequestMapping(path = "/tasks/new.xml", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	@ResponseBody
	public CreateTaskResponse createNewTaskXML(@RequestBody CreateTaskRequest createRequest) {

		Task task = new Task();
		task.setName(createRequest.getTaskName());
		task.setCreatedBy(userService.findById(createRequest.getCreatorId()));
		task.setAssignee(userService.findById(createRequest.getAssigneeId()));
		task.setComments(createRequest.getComments());
		task.setPriority(createRequest.getPriority());

		return new CreateTaskResponse(taskService.createTask(task));
	}

	@RequestMapping(path = "/tasks/new-async-callable.xml", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	@ResponseBody
	public Callable<CreateTaskResponse> createNewTaskXMLAsyncCallable(@RequestBody CreateTaskRequest createRequest) {

		return new Callable<CreateTaskResponse>() {
			@Override
			public CreateTaskResponse call() throws Exception {
				Task task = new Task();
				task.setName(createRequest.getTaskName());
				task.setCreatedBy(userService.findById(createRequest.getCreatorId()));
				task.setAssignee(userService.findById(createRequest.getAssigneeId()));
				task.setComments(createRequest.getComments());
				task.setPriority(createRequest.getPriority());

				return new CreateTaskResponse(taskService.createTask(task));
			}
		};
	}

	@RequestMapping(path = "/tasks/new-async-deferred.json", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public DeferredResult<CreateTaskResponse> createNewTaskJSONAsyncDeferredResult(
			@RequestBody CreateTaskRequest createRequest) {

		DeferredResult<CreateTaskResponse> deferredResult = new DeferredResult<>();
		CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				Task task = new Task();
				task.setName(createRequest.getTaskName());
				task.setCreatedBy(userService.findById(createRequest.getCreatorId()));
				task.setAssignee(userService.findById(createRequest.getAssigneeId()));
				task.setComments(createRequest.getComments());
				task.setPriority(createRequest.getPriority());
				Task persistedTask = taskService.createTask(task);
				// Send an email here...
				// Send some push notifications here...
				deferredResult.setResult(new CreateTaskResponse(persistedTask));
			}
		});

		return deferredResult;
	}

	@RequestMapping(path = "/users/{userId}/tasks-async-deferred", method = RequestMethod.GET)
	public DeferredResult<String> listAllUserTasksByStatusAsyncDeferred(@PathVariable("userId") Long userId,
			@RequestParam(name = "status", required = false) String status, Model model) {

		DeferredResult<String> deferredResult = new DeferredResult<>();
		CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				model.addAttribute("assignee", userService.findById(userId));
				model.addAttribute("status", status);
				if (StringUtils.isEmpty(status)) {
					model.addAttribute("tasks", taskService.findTasksByAssignee(userId));
					model.addAttribute("status", "All");
				} else if (status.equals("Open"))
					model.addAttribute("tasks", taskService.findOpenTasksByAssignee(userId));
				else if (status.equals("Closed"))
					model.addAttribute("tasks", taskService.findClosedTasksByAssignee(userId));
				else
					throw new IllegalArgumentException("Illegal value for status supplied: " + status
							+ "; Only 'Open' and 'Closed' are supported.");
				deferredResult.setResult("task/list");
			}
		});

		return deferredResult;
	}

}
