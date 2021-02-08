package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@Autowired
	private UserService demoService;
	
	@RequestMapping(value="/getuser")
	public String getUsername() {
		System.out.println("====================getin");
		String aa= demoService.getUserName();
		System.out.println("=================after");
		return aa;
	}
}
