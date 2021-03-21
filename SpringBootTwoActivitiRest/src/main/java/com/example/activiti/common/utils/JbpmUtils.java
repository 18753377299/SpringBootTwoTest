package com.example.activiti.common.utils;

import org.jbpm.api.*;
import org.jbpm.api.task.Task;

import java.util.Iterator;
import java.util.List;


public class JbpmUtils {
    private static ProcessEngine processEngine;
    private static RepositoryService repositoryService;
    private static ExecutionService executionService;

    static{
        //流程定义引擎的初始化
        processEngine = Configuration.getProcessEngine();
        //管理流程定义
        repositoryService = processEngine.getRepositoryService();
        //executionService  用于执行流程定义实例
        executionService = processEngine.getExecutionService();
    }

    /**
     * 获取流程管理的repositoryService
     * @return
     */
    public static RepositoryService getRepositoryService(){
        return repositoryService;
    }

    /**
     * 获取执行流程定义的ExecutionService
     * @return
     */
    public static ExecutionService getExecutionService(){
        return executionService;
    }

    public void deploy(String jpdlFileName){
        JbpmUtils.getRepositoryService()
                .createDeployment()
                .addResourceFromClasspath(jpdlFileName)
                .deploy();
    }
    /**
     * 流程定义的查询
     */
    public void query(){
        ProcessDefinitionQuery query
                = JbpmUtils.getRepositoryService().createProcessDefinitionQuery();
        List<ProcessDefinition> list = query.list();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ProcessDefinition processDefinition = (ProcessDefinition) iterator
                    .next();
            /**
             * id:流程定义的id
             * key：就是流程定义的文件名称  、相同的key下面有不同的id
             * deploymentId：表达的是这次动作发布的时候数据库中存储的那条记录的主键id
             */
            System.out.println("id:"+processDefinition.getId());
            System.out.println("key:"+processDefinition.getKey());
            System.out.println("deploymentId:"+processDefinition.getDeploymentId());
        }
    }

    /**
     * 流程定义的删除
     * @param deploymentId
     */
    public void del(String deploymentId){
        //deleteDeploymentCascade 级联删除：删除流程定义的时候把流程实例也删了..
        JbpmUtils.getRepositoryService().deleteDeploymentCascade(deploymentId);
    }

    /**
     * 创建流程实例
     * @param key	流程定义的key
     * @return	返回创建的流程实例
     */
    public ProcessInstance createInstance(String key){
        //虽然说多个实例的key相同。但是startProcessInstanceByKey通过key来启动的话会默认找到最新版本的流程定义、并创建相应的流程实例
        return JbpmUtils.getExecutionService().startProcessInstanceByKey(key);
    }

    /**
     * 执行流程实例 ：只按线条的顺序执行
     * @param processInstanceId
     * @return
     */
    public ProcessInstance execute(String processInstanceId){
        return JbpmUtils.getExecutionService().signalExecutionById(processInstanceId);

    }

    /**
     * 执行流程实例2：通过transition 指向的路径根据名字执行（to state1  to state2 to end1......）
     * @param processInstanceId
     * @return
     */
    public ProcessInstance execute(String processInstanceId,String transitionName){
        return JbpmUtils.getExecutionService().signalExecutionById(processInstanceId,transitionName);
    }

//    public static void main(String[] args) {
//        Test test = new Test();
//		test.deploy("hello.jpdl.xml");//流程定义的发布
//        test.deploy("simple2.jpdl.xml");//流程定义的发布
//		test.query();//流程定义的查询
//		test.del("10001");//删除流程定义
//        test.createInstance("simple2");//创建流程实例
//		test.execute("simple.70001");//执行流程实例
//		test.execute("simple.70001","to end1");//执行流程实例

//    }

}
