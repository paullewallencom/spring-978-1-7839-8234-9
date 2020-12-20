package com.taskify.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taskify.domain.Task;
import com.taskify.service.TaskService;
import com.taskify.service.UserService;
import com.taskify.web.model.CreateTaskRequest;
import com.taskify.web.model.CreateTaskResponse;

/**
 * Handles requests for task related pages.
 */
@RestController
public class TaskRestController {

	private static final Logger logger = LoggerFactory.getLogger(TaskRestController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	private static final int[] priorities = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	/**
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
	public String listAllUserTasksByStatus(@PathVariable("userid") Long userId, @RequestParam("status") String status,
			Model model) {
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
		model.addAttribute("priorities", priorities);
		model.addAttribute("users", userService.findAllUsers());
		return "task/new";
	}

	@RequestMapping(path = "/tasks/new", method = RequestMethod.POST)
	public String createNewTask(@ModelAttribute("task") @Validated Task task, BindingResult result, Model model) {

		// task.setAssignee(userService.findById(task.getAssignee().getId()));
		// task.setCreatedBy(userService.findById(task.getCreatedBy().getId()));

		// Task existingTask = taskService.findTaskById(task.getId());

		taskService.createTask(task);
		return "redirect:/tasks";
	}

	@RequestMapping(path = "/tasks/{id}", method = RequestMethod.GET)
	public ModelAndView viewTask(@PathVariable("id") Long id) {
		return new ModelAndView("task/view").addObject("task", taskService.findTaskById(id)).addObject("priorities",
				priorities);
	}

	@RequestMapping(path = "/tasks/{id}", method = RequestMethod.PUT)
	public String saveTask(@ModelAttribute("task") @Validated Task task, BindingResult result, Model model) {

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
	public String completeTask(@PathVariable("id") Long id, @ModelAttribute("task") @Validated Task task,
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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(new DateFormatter("dd/MM/yyyy"));
	}
**//*

	@RequestMapping(path = "/api/tasks/new.json", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public CreateTaskResponse createNewTaskJSON(@RequestBody CreateTaskRequest createRequest) {

		Task task = new Task();
		task.setName(createRequest.getTaskName());
		task.setCreatedBy(userService.findById(createRequest.getCreatorId()));
		task.setAssignee(userService.findById(createRequest.getAssigneeId()));
		task.setComments(createRequest.getComments());
		task.setPriority(createRequest.getPriority());

		return new CreateTaskResponse(taskService.createTask(task));
	}

	@RequestMapping(path = "/api/tasks/new.xml", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	public CreateTaskResponse createNewTaskXML(@RequestBody CreateTaskRequest createRequest) {

		Task task = new Task();
		task.setName(createRequest.getTaskName());
		task.setCreatedBy(userService.findById(createRequest.getCreatorId()));
		task.setAssignee(userService.findById(createRequest.getAssigneeId()));
		task.setComments(createRequest.getComments());
		task.setPriority(createRequest.getPriority());

		return new CreateTaskResponse(taskService.createTask(task));
	}*/
}
