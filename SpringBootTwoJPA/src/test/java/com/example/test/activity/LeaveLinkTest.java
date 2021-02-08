package com.example.test.activity;

import com.example.SpringBootTwoJpaApplication;
import com.example.common.activiti.ActivitiUtils;
import com.example.common.activiti.ActivityConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@Api(value="请假流程连线")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class LeaveLinkTest {

    @Test
    @ApiOperation(value="流程部署")
    public void deploy(){
        /**文件名字*/
        String  bpmnNameAndKey ="leaveLink";
        ActivitiUtils.deploy(ActivityConst.ProcessInstanceKeyLeaveLink,bpmnNameAndKey);
    }

    @Test
    @ApiOperation(value="启动流程")
    public void startFlow(){
        /**动态指定人员名称*/
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("firstUnderWriteCode","张总啊1");
        ActivitiUtils.startFlow(ActivityConst.ProcessInstanceKeyLeaveLink,map);
    }

    @Test
    @ApiOperation(value="完成任务,根据连线设置分支")
    public void competeTask(){
        String  taskId ="62506";
        Map<String,Object> map = new HashMap<>();
//        map.put("message","unimportant");
        /**两个同时存在 begin*/
        map.put("message","important");
        map.put("secondUnderWriteCode","李总啊1");
        /**两个同时存在 end*/
        ActivitiUtils.executeTaskByVariable(taskId,map);
    }




}
