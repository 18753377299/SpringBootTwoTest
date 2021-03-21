package com.example.test.activity.group;

import com.example.common.activiti.tool.ActivitiIdentityTool;
import com.example.test.DemoTestSuper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ActivitiGroupTest  extends DemoTestSuper {

    @Autowired
    @Qualifier(value = "activitiIdentityTool")
    ActivitiIdentityTool activitiIdentityTool;

    @Test
    public void addUser(){
        activitiIdentityTool.addUser();
    }

    @Test
    public void queryUser(){
        activitiIdentityTool.queryUser();
    }


}
