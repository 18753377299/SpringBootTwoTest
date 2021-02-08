package com.example.risk.quartz;

import com.example.risk.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class QuartzDemo implements Job{

	@Autowired
	private UserService userService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("==============execute:"+new Date());
		String user = userService.showUser();
		System.out.println("=================="+user);
	}
	
	
	
}
