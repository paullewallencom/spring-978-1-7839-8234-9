package com.taskify.web.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.taskify.domain.File;
import com.taskify.domain.User;
import com.taskify.service.UserService;

/**
 * Handles requests for user related pages.
 */
@Controller
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final String PROFILE_IMAGE_SAVE_LOCATION = "/tmp/taskify/profileImages";

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String listAll(Locale locale, Model model) {

		// model.addAttribute("users", userService.findAllUsers());
		return "user/list";
	}

	@ModelAttribute(value = "users")
	public List<User> getUsersList() {
		return userService.findAllUsers();
	}

	@RequestMapping(path = "/new", method = RequestMethod.GET)
	public String newUserForm(Model model) {

		User user = new User();
		user.setDateOfBirth(new Date());
		model.addAttribute("user", user);
		return "user/new";
	}

	@RequestMapping(path = "/new", method = RequestMethod.POST)
	public String saveNewUser(@ModelAttribute("user") User user, Model model) {

		userService.createNewUser(user);
		return "redirect:/users";
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ModelAndView viewUser(@PathVariable("id") Long id) {
		return new ModelAndView("user/view").addObject("user", userService.findById(id));
	}

	@RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") Long id, Model model) {

		model.addAttribute("user", userService.findById(id));
		return "user/edit";
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user, Model model) {

		userService.updateUser(user);

		model.addAttribute("user", userService.findById(user.getId()));
		return "redirect:/users/" + id;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("id") Long id, Model model) {

		User existingUser = userService.findById(id);

		userService.deleteUser(existingUser);
		return "redirect:/users";
	}

	@RequestMapping(path = "/{userId}/profileForm", method = RequestMethod.GET)
	public ModelAndView uploadProfileImage(@PathVariable("userId") Long userId) throws IOException {

		return new ModelAndView("user/upload").addObject("user", userService.findById(userId));
	}

	@RequestMapping(path = "/{userId}/profileForm", method = RequestMethod.POST)
	public String uploadProfileImage(@PathVariable("userId") Long userId,
			@RequestParam("profileImage") MultipartFile file) throws IOException {

		User user = userService.findById(userId);
		String fileSaveDirectory = PROFILE_IMAGE_SAVE_LOCATION + "/" + user.getId();

		if (!file.isEmpty()) {
			java.io.File fileDir = new java.io.File(fileSaveDirectory);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			FileCopyUtils.copy(file.getBytes(), new java.io.File(fileSaveDirectory + "/" + file.getOriginalFilename()));
			File profileImageFile = this.userService.addProfileImage(userId, file.getOriginalFilename());
		}

		return "redirect:/users/" + userId;
	}

	@RequestMapping(path = "/{userId}/profileImage", method = RequestMethod.GET)
	public void downloadProfileImage(@PathVariable("userId") Long userId, HttpServletResponse response)
			throws IOException {
		User user = userService.findById(userId);
		if (user.getProfileImage() != null) {
			String fileSaveDirectory = PROFILE_IMAGE_SAVE_LOCATION + "/" + user.getId();
			java.io.File physicalFile = new java.io.File(
					fileSaveDirectory + "/" + user.getProfileImage().getFileName());
			if (physicalFile.exists()) {
				String mimeType = URLConnection.guessContentTypeFromName(physicalFile.getName());
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition",
						String.format("inline; filename=\"" + physicalFile.getName() + "\""));
				response.setContentLength((int) physicalFile.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(physicalFile));

				FileCopyUtils.copy(inputStream, response.getOutputStream());
				return;
			}
		}
		String errorMessage = "Sorry. The file you are looking for does not exist";
		System.out.println(errorMessage);
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
		outputStream.close();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(new DateFormatter("dd/MM/yyyy"));
	}
}
