package com.example.common.config;

import com.example.common.filter.CumstomFilter;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**配置自定义 Filter：
*有2种方式可以实现过滤器（https://www.jb51.net/article/172128.htm）
    1：通过FilterRegistrationBean实例注册
    2：通过@WebFilter注解生效
    这里选择第一种，因为第二种不能设置过滤器之间的优先级
* */
@Configuration
public class WebCustomConfiguration {

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        //通过FilterRegistrationBean实例设置优先级可以生效，通过@WebFilter无效
        FilterRegistrationBean registration = new FilterRegistrationBean();
        //注册自定义过滤器
        registration.setFilter(new CumstomFilter());
        //过滤所有路径
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        //过滤器名称
        registration.setName("CumstomFilter");
        //优先级，最顶级
        registration.setOrder(1);
        return registration;
    }

}
