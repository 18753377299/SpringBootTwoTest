package com.example.common.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

import com.example.common.activiti.IdGen;


@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

	@Override
	public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
		// TODO Auto-generated method stub
		processEngineConfiguration.setIdGenerator(new IdGen());
	}

}
