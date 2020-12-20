package com.springessentialsbook.chapter5.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springessentialsbook.chapter5.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.NoSuchElementException;

@Controller
public class MvcController {

	@Autowired
	BusinessService businessService;


	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		model.addAttribute("anonymousMessage", "Hi every body. ");
		return "login";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "adminWorkspace";
	}

	@RequestMapping(value = "/accountant", method = RequestMethod.GET)
	public String dbaPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accountantWorkspace";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		//model.addAttribute("user", getPrincipal());
		return "login";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(ModelMap model) {
		String user=getPrincipal();
		model.addAttribute("user", getPrincipal());
		return  "userWorkspace";
	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "login?";
	}

    @RequestMapping(value = {"/adResources/*" }, method = RequestMethod.GET)
	public String adminSpecificPage(ModelMap model) {
		model.addAttribute("anonymousMessage", "Hi every body. ");
		return "../adminResources/admin";
	}

	@RequestMapping(value = "/nonAuthorized", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "nonAuthorized";
	}

	@RequestMapping(value = "/migrateUsers", method = RequestMethod.GET)
	public String migrateUsers(ModelMap model) {
		model.addAttribute("user", getPrincipal());
        businessService.migrateUsers();
		return "migrateUsers";
	}



	@PreAuthorize("@businessServiceImpl.isEligibleToSeeUserData(principal, #username)")
	@RequestMapping("/userdata/{username}")
	public String getUserPage(@PathVariable String username,ModelMap model) {
		model.addAttribute("user", getPrincipal());
		model.addAttribute("userName",  username);
		model.addAttribute("userData",businessService.loadUserData(username) );
		return "../loadUserData";
	}


	private String getPrincipal(){
		String userName = null;
        Object principal=null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null){
            principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }


		if (principal !=null && principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}