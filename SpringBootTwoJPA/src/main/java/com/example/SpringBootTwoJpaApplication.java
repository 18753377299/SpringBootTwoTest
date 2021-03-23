package com.example;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*审计功能*/
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//@ComponentScan(basePackages = {"com.example"})
@EnableJpaAuditing
/*自定义的过滤器去实现。使用FilterType.CUSTOM加上自定义的过滤器  begin*/
//@ComponentScan(basePackages = {"com.example"}
//////	,excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)
//)
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
//@EnableAsync
//@ComponentScan("org.activiti.rest")
@ComponentScan({"com.example.riskfunc","com.example.common"})
public class SpringBootTwoJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTwoJpaApplication.class, args);
    }
}
