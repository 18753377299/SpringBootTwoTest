package com.example.test.activity;

import com.example.SpringBootTwoJpaApplication;
import com.example.common.activiti.ActivitiUtils;
import com.example.vo.RiskRequestVo;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/** 流程变量：
 *     运行时流程变量数据表： act_ru_variable
 *     流程变量历史表： act_hi_varinst
 动态指定办理人*/

//排他网关
//并行网关
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class ActivityProcessTest {
    public static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**设置流程变量值*/
    @Test
    public void setVariable(){
//        1、
        String taskId ="20011";
        TaskService taskService = ActivitiUtils.getTaskService();
        taskService.setVariable(taskId,"const",1000);
        taskService.setVariable(taskId,"申请时间",new Date());
        // 可以用于区分不同的任务的信息
        taskService.setVariableLocal(taskId,"申请人","张三");

       /** 3、 变量支持的类型String,boolean,Date,自定义对象bean*/
//        String taskId ="20011";
//        TaskService taskService = ActivitiUtils.getTaskService();
//        RiskRequestVo riskRequestVo =new RiskRequestVo();
//        riskRequestVo.setAge(17);
//        riskRequestVo.setName("zhangsan");
//        riskRequestVo.setInsertTime(new Date());
//        // 可以用于区分不同的任务的信息
//        taskService.setVariableLocal(taskId,"riskRequestVo",riskRequestVo);
    }
    /**获取流程变量值*/
    @Test
    public void getVariable(){
        String taskId ="20011";
        TaskService taskService = ActivitiUtils.getTaskService();
        /**一、变量中存储基本数据类型*/
        Integer constValue = (Integer) taskService.getVariable(taskId,"const");
        Date date =(Date)taskService.getVariable(taskId,"申请时间");
        // 可以用于区分不同的任务的信息
        String applyeName =(String)taskService.getVariableLocal(taskId,"申请人");
        /**二、变量中存储javabean对象*/
        RiskRequestVo riskRequestVo= (RiskRequestVo)taskService.getVariableLocal(taskId,"riskRequestVo");
        System.out.println("name: "+riskRequestVo.getName());
    }



    /**模拟流程变量设置*/
    @Test
    public void getAndSetProcessVariable(){

        /**1、通过runtimeService 来设置流程变量
        * executionId： 执行对象
         * variableName： 变量名
         * values：变量值
        * */
//        runtimeService.setVariable(executionId,variableName,values);
      /*设置本执行对象的变量，改变量的作用域只在当前的execution对象*/
//        runtimeService.setVariableLocal(executionId,variableName,values);
       /* 可以设置多个变量，放在Map<String, ? extends Object>中*/
//        runtimeService.setVariables(executionId,variables);
        /**2、通过runtimeService 来设置流程变量
         * taskId： 执行对象
         * variableName： 变量名
         * values：变量值
         * */
//        taskService.setVariable(taskId,variableName,values);
        /*设置本执行对象的变量，改变量的作用域只在当前的execution对象*/
//        taskService.setVariableLocal(taskId,variableName,values);
        /* 可以设置多个变量，放在Map<String, ? extends Object>中*/
//        taskService.setVariables(taskId,variables);

        /**3、当流程刚开始执行的时候，设置变量参数
         *processDefinitionKey: 流程定义的key
         * variables： 可以设置多个变量，放在Map<String, ? extends Object>中
         *
         * */
//        processEngine.getRuntimeService()
//                .startProcessInstanceByKey(processDefinitionKey,variables);
        /**4、当执行任务的时候，设置流程变量
         *taskId: 任务id
         * variables： 可以设置多个变量，放在Map<String, ? extends Object>中
         * */
//        processEngine.getTaskService().complete(taskId,variables);

        /**5、通过runtimeService取变量值*/
//        runtimeService.getVariable(executionId,variableName);
//        runtimeService.getVariableLocal (executionId,variableName);
//        runtimeService.getVariables(variablesName);// 获取当前对象的所有变量

        /**5、通过taskService取变量值*/
//        taskService.getVariable(taskId,variableName);
//        taskService.getVariableLocal (taskId,variableName);
//        taskService.getVariables(taskId);// 获取当前对象的所有变量
    }
  /**指定任务处理者*/




}
