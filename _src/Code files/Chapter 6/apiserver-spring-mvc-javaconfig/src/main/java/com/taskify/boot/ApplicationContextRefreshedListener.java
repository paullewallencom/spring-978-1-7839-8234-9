package com.taskify.boot;

import java.io.File;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import com.taskify.domain.Task;
import com.taskify.domain.User;
import com.taskify.service.TaskService;
import com.taskify.service.UserService;

@Component
public class ApplicationContextRefreshedListener implements ApplicationListener<ContextStartedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextRefreshedListener.class);

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	private static final String PROFILE_IMAGE_SAVE_LOCATION = "/tmp/taskify/profileImages";

	@PostConstruct
	public void loadData() {
		logger.info(">>>>>>>>>>>>>>>>>>> Inside " + this + ".onApplicationEvent--- <<<<<<<<<<");
		
		User shameer = new User(null, "Shameer Kunjumohamed", "sameerean", "password", new Date());
		userService.createNewUser(shameer);
		discoverProfileImageIfExists(shameer);
		
		User tarun = new User(null, "Tarun Bhati", "tbhati", "password", new Date());
		userService.createNewUser(tarun);
		discoverProfileImageIfExists(tarun);
		
		taskService.createTask(new Task("Order Food", 10, "Open", shameer, new Date(), tarun, "This is for breakfast"));
		taskService.createTask(new Task("Commit code changes", 5, "Open", shameer, new Date(), tarun, "Let's test and deploy on Staging Server"));
		taskService.createTask(new Task("Review code changes", 6, "Open", tarun, new Date(), shameer, "Please send your feedback"));
		taskService.createTask(new Task("Release project version", 3, "Open", tarun, new Date(), shameer, "All tested, verified, accepted"));
		taskService.createTask(new Task("Order Snacks", 9, "Closed", tarun, new Date(), shameer, "Order some samosas and juices from Haji Ali"));
	}
	
	@Override
	public void onApplicationEvent(ContextStartedEvent event) {
		logger.info(">>>>>>>>>>>>>>>>>>> Inside " + this + ".onApplicationEvent--- <<<<<<<<<<");
		
		User shameer = new User(null, "Shameer Kunjumohamed", "sameerean", "password", new Date());
		userService.createNewUser(shameer);
		discoverProfileImageIfExists(shameer);
		
		User tarun = new User(null, "Tarun Bhati", "tbhati", "password", new Date());
		userService.createNewUser(tarun);
		discoverProfileImageIfExists(tarun);
		
		taskService.createTask(new Task("Order Food", 10, "Open", shameer, new Date(), tarun, "This is for breakfast"));
		taskService.createTask(new Task("Commit code changes", 5, "Open", shameer, new Date(), tarun, "Let's test and deploy on Staging Server"));
		taskService.createTask(new Task("Review code changes", 6, "Open", tarun, new Date(), shameer, "Please send your feedback"));
		taskService.createTask(new Task("Release project version", 3, "Open", tarun, new Date(), shameer, "All tested, verified, accepted"));
		taskService.createTask(new Task("Order Snacks", 9, "Closed", tarun, new Date(), shameer, "Order some samosas and juices from Haji Ali"));
	}
	
	private void discoverProfileImageIfExists(User user) {
			String fileSaveDirectory = PROFILE_IMAGE_SAVE_LOCATION + "/" + user.getId();
			java.io.File physicalDir = new java.io.File(
					fileSaveDirectory);
			
			if (physicalDir.exists()) {
				File[] files = physicalDir.listFiles();
				if(files != null && files.length > 0) {
					File firstFile = files[0];
					user.setProfileImage(new com.taskify.domain.File(1000 + user.getId() + 1, firstFile.getName()));
				}
			}

	}

}
