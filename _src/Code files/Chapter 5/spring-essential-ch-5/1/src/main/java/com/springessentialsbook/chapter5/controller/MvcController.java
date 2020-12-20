package com.springessentialsbook.chapter5.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MvcController {


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

@RequestMapping(value = {"/adResources" }, method = RequestMethod.GET)
	public String adminSpecificPage(ModelMap model) {
		model.addAttribute("anonymousMessage", "Hi every body. ");
		return "adminResources/admin";
	}

	@RequestMapping(value = "/nonAuthorized", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "nonAuthorized";
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