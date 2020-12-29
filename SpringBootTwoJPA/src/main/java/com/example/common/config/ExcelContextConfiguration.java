package com.example.common.config;

import org.easy.excel.ExcelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelContextConfiguration {

	@Bean
	public ExcelContext excelContextFile() {
		return new ExcelContext("classpath:excel/excel-config.xml");
	}	
	
}
