package com.example.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*自定义过滤器*/
public class CumstomFilter implements Filter {
     /*初始化*/
    public  void init(FilterConfig filterConfig) throws ServletException {

    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException{
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        System.out.println("{CumstomFilter}===>:"+servletRequest.getRequestURI());
        chain.doFilter(request, response);
    }
    /*销毁*/
    public void destroy() {}

}
