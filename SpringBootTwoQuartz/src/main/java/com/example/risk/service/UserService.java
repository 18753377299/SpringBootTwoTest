package com.example.risk.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	public String showUser() {
		System.out.println("==================success");
		
		return "showUser";
	}
}
