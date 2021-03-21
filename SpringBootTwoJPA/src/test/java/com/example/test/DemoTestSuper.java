package com.example.test;


import com.example.SpringBootTwoJpaApplication;
import io.swagger.annotations.Api;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootTwoJpaApplication.class)
@Api(value="用于对测试类进行封装")
public class DemoTestSuper implements InitializingBean {
    public void afterPropertiesSet() throws Exception{
        System.out.println("{}=====>开始测试");
    }
}
