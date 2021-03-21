package com.example.test.activity;

import com.example.SpringBootTwoJpaApplication;
import com.example.common.activiti.ActivitiDynamicUtils;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Api("通过代码生成流程图")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class ActivitiDynamicTest {

    @Test
    public void test(){
        ActivitiDynamicUtils.createBpmnElement();
        ActivitiDynamicUtils.createBpmnAutoLayout();
    }
}
