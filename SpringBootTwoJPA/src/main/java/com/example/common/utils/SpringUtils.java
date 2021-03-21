package com.example.common.utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(value="spring工具类,用于静态获取Spring容器中的Bean")
@Component
public class SpringUtils  implements  ApplicationContextAware {

    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException{
        this.context = applicationContext;
    }
    public static  ApplicationContext getApplicationContext(){
        return context;
    }


    @ApiOperation(value="通过名称进行注入")
    public static  <T>  T getBean(String name){
        if(null == getApplicationContext()){
            System.out.println("ApplicationContext初始化为空");
        }
        return (T)getApplicationContext().getBean(name);
    }
    @ApiOperation(value="通过类进行注入")
    public static <T> T getBean(Class<T> tClass){
        if(null == getApplicationContext()){
            System.out.println("ApplicationContext初始化为空");
        }
        return (T)getApplicationContext().getBean(tClass);
    }


    @ApiOperation(value="检查SpringUtil是否已完成初始化")
    public static boolean isInited(){
        return null!=context;
    }


    @ApiOperation(value="在SpringApplication 上下文环境中获取web属性集对象")
    public static ServletRequestAttributes getRequestAttributes(){
        return (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    }

    @ApiOperation(value="获取HttpServletRequest对象")
    public static HttpServletRequest getServletRequest(){
        return getRequestAttributes().getRequest();
    }

    @ApiOperation(value="获取HttpServletResponse对象")
    public static HttpServletResponse getServletResponse(){
        return getRequestAttributes().getResponse();
    }

    @ApiOperation(value="获取HttpSession对象")
    public static HttpSession getHttpSession(){
        return getRequestAttributes().getRequest().getSession();
    }



}
