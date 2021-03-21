package com.example.common.activiti;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;

@Api("Activity工具类")
public class ActivitiUtils {

    @ApiOperation(value="获取ProcessEngine")
    public static ProcessEngine getProcessEngine(){
        return ProcessEngines.getDefaultProcessEngine();
    }

    @ApiOperation(value="获取RepositoryService类")
    public static RepositoryService getRepositoryService(){
        return getProcessEngine().getRepositoryService();
    }

    @ApiOperation(value="获取RuntimeService类")
    public static RuntimeService getRuntimeService(){
        return getProcessEngine().getRuntimeService();
    }


    @ApiOperation(value="获取TaskService类")
    public static TaskService getTaskService(){
        return getProcessEngine().getTaskService();
    }

    @ApiOperation(value="获取HistoryService类")
    public static HistoryService getHistoryService(){
        return getProcessEngine().getHistoryService();
    }

    @ApiOperation(value="获取IdentityService类")
    public static IdentityService getIdentityService(){
        return getProcessEngine().getIdentityService();
    }
    /**
      * 部署信息表：act_re_deployment
      * 流程设计模型部署表： act_re_model
      * 流程定义数据表： act_re_procdef
      * */
    @ApiOperation(value="进行流程部署")
    public static void deploy(String bpmnNameAndKey,String resourceName) {
        RepositoryService repositoryService = getRepositoryService();
        String keyName = "processes/" + resourceName+".bpmn20.xml";
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource(keyName)
                .key(bpmnNameAndKey)
                .name(bpmnNameAndKey + "name").category("HR").deploy();
        System.out.println("流程部署ID\t" + deploy.getId());
        System.out.println("流程keyID\t" + deploy.getKey());
        System.out.println("流程名称ID\t" + deploy.getName());
        System.out.println("流程分类ID\t" + deploy.getCategory());
    }
    /**
     * 第二步： 发布流程
     * 流程发布后在  act_ru_task ，act_ru_execution， act_ru_identitylink 表中插入流程数据
     * 接下来就可以通过用户ID去查看该用户的任务了
     */
    @ApiOperation(value="发布流程,指定人员")
    public static void startFlow(String  processInstanceByKey, Map<String,Object> map ){
        try {
            ProcessInstance processInstance = getRuntimeService().
                    startProcessInstanceByKey(processInstanceByKey,map);
            System.out.println("流程实例的id: "+processInstance.getId()+
                    ",流程定义的ID："+processInstance.getProcessDefinitionId()+
                    ",activitiId: "+processInstance.getActivityId()+
                    ",流程实现的ID："+processInstance.getProcessInstanceId());
            System.out.println("name： "+processInstance.getName());
            System.out.println("ProcessVariables： "+processInstance.getProcessVariables().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value="执行任务",
            notes="对于执行完的任务，activiti将从act_ru_task表中删除该任务，下一个任务会被插入进来。")
    public static void executeTask(String taskId,String userId){
        TaskService taskService = getTaskService();
        /**添加代理人*/
        taskService.setAssignee(taskId,userId);
        taskService.complete(taskId);
    }
    @ApiOperation(value="执行任务,同时设置Variable值")
    public static void executeTaskByVariable(String taskId, Map<String,Object> map){
        TaskService taskService = getTaskService();
        taskService.complete(taskId,map);
    }

    @ApiOperation(value="通过部署id删除流程定义")
    public static void deleteProcessDefinition(String  deploymentId){
        /** 通过部署id删除流程定义,
         * 普通删除，如果当前规则下有正在执行的流程，则抛异常。
         * */
//        getRepositoryService().deleteDeployment(deploymentId);
            /**级联删除,会删除和当前规则相关的所有信息,
            正在执行的信息,也包括历史信息
             相当于：
             */
        getRepositoryService().deleteDeployment(deploymentId,true);
    }
    @ApiOperation(value="删除key相同的所有不同版本的流程定义",notes="keyId:为ActivityConst.ProcessInstanceKeyLeave")
    public static void deleteProcessDefinitionByKey(String keyId){
        List<ProcessDefinition> list = getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionKey(keyId)
                .list();
        for(ProcessDefinition pd: list){
            getRepositoryService().deleteDeployment(pd.getDeploymentId(),true);
        }
    }

    @ApiOperation(value="查询最新版本的流程定义")
    public static void queryNewProcessDefinition(){
       List<ProcessDefinition> list = getRepositoryService().createProcessDefinitionQuery()
                    .orderByProcessDefinitionVersion().asc().list();
    }
    @ApiOperation(value="查询流程状态",
            notes="取流程实例的状态,运行时流程执行实例表：act_ru_execution")
    public static void queryProcessState(String processInstanceId){
        ProcessInstance pi = getRuntimeService().createProcessInstanceQuery()
               .processInstanceId(processInstanceId).singleResult();
        /*判断流程实例的状态*/
        if(null!=pi){
            System.out.println("该流程实例"+processInstanceId
                    +"正在运行。。。，当前活动的任务"+pi.getActivityId());
        }else{
            System.out.println("当前的流程实例"+processInstanceId
                    +"已经结束！");
        }
    }

    @ApiOperation(value="查询历史流程实例,通过流程实例ID")
    public static HistoricProcessInstance queryHistoryProcessInstance(String hisProcessInstanceId) {
        HistoricProcessInstance hpi = getHistoryService().createHistoricProcessInstanceQuery()
                .processInstanceId(hisProcessInstanceId).singleResult();
        return hpi;
    }

    @ApiOperation(value="查询历史流程变量list,查询act_hi_varinst表")
    public static List<HistoricVariableInstance> queryHistoricVariableInstance() {
        List<HistoricVariableInstance> hviList = getHistoryService()
                .createHistoricVariableInstanceQuery()
//                .processInstanceId()   //使用流程实例ID查询
                .list();
        return hviList;
    }
    @ApiOperation(value="第五步：查看历史流程实例",notes = "历史流程实例表：act_hi_procinst," +
            "参数为ActivityConst.ProcessInstanceKeyLeave")
    public static List<HistoricProcessInstance> findhistProcessInstance(String processDefinitionKey) {
        HistoryService historyService =ActivitiUtils.getHistoryService();
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .orderByProcessInstanceStartTime().desc() // 根据开始时间降序排序
                .list();
        return list;
    }
    @ApiOperation(value="查看历史活动实例",notes = "历史节点表：act_hi_actinst," +
            "参数为act_hi_actinst表中的pro_inst_id字段")
    public static List<HistoricActivityInstance> findHistoricActivityInstance(String processInstanceId) {
        HistoryService historyService =ActivitiUtils.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId) //使用流程实例ID查询
                .orderByHistoricActivityInstanceEndTime().desc() // 根据开始时间降序排序
                .list();
        return list;
    }
    @ApiOperation(value="第六步：查看历史任务, 历史流程任务表：act_hi_taskinst"+
            "参数为act_hi_taskinst表中的pro_inst_id字段")
    public static List<HistoricTaskInstance> findHisTask(String processInstanceId){
        HistoryService historyService =ActivitiUtils.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .orderByHistoricTaskInstanceStartTime().desc() // 排序条件
                .list();
        return list;
    }










}
