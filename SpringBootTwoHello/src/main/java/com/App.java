//package com;
//
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.ServletComponentScan;
//import org.springframework.context.annotation.ComponentScan;
//
//@SpringBootApplication
////@MapperScan 用户扫描MyBatis的Mapper接口
//@MapperScan("com.mapper")
////@ComponentScan(basePackages="com")
////SpringBoot 整合 Filter
//@ServletComponentScan(basePackages="com.common")
//public class App {
//	public static void main(String [] args) {
//		SpringApplication.run(App.class, args);
//	}
//
//	/**
//	 * 注册Filter,注册servlet的方法和这个一样
//	 * */
////	@Bean
////	public FilterRegistrationBean getFilterRegistrationBean() {
////		FilterRegistrationBean bean = new FilterRegistrationBean(
////				new FirstFilter());
////		bean.addUrlPatterns("/*");
////		return bean;
////	}
//
//}
//
//
