package com.springessentialsbook.chapter5.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResourceMvcController {




	@RequestMapping(value = "/protected", method = RequestMethod.GET)
	@ResponseBody
	public String getProtectedResources(ModelMap model) {

		return  "this is from getProtectedResources";
	}

	@RequestMapping(value = "/public", method = RequestMethod.GET)
	@ResponseBody
	public String getPublicResources(ModelMap model) {
		return  "this is from getPublicResources";
	}


}