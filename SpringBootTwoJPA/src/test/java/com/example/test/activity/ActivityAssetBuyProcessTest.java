package com.example.test.activity;

import com.example.SpringBootTwoJpaApplication;
import com.example.common.activiti.ActivitiUtils;
import com.example.common.activiti.ActivityConst;
import io.swagger.annotations.Api;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@Api("资产采购审核流")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class ActivityAssetBuyProcessTest {

    @Test
    public void deploy(){
        String  bpmnNameAndKey ="AssertUnderWrite";
        ActivitiUtils.deploy(ActivityConst.ProcessInstanceByKey,bpmnNameAndKey);
    }

    @Test
    public void startFlow(){
        Map<String,Object> map = new HashMap<String,Object>();
        ActivitiUtils.startFlow(ActivityConst.ProcessInstanceByKey,map);
    }

}
