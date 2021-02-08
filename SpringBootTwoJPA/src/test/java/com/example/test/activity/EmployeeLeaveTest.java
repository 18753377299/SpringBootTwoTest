package com.example.test.activity;

import com.example.SpringBootTwoJpaApplication;
import com.example.common.activiti.ActivitiUtils;
import com.example.common.activiti.ActivityConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * employeeLeave
   submitUnderWrite
   firstUnderWrite
   secondUnderWrite
 */
@Api(value="员工请假系统")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class EmployeeLeaveTest {
    public static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    @Test
    @ApiOperation(value="流程部署")
    public void deploy(){
        /**文件名字*/
        String  bpmnNameAndKey ="leave";
        ActivitiUtils.deploy(ActivityConst.ProcessInstanceKeyLeave,bpmnNameAndKey);
    }
    @Test
    @ApiOperation(value="启动流程")
    public void startFlow(){
        /**动态指定人员名称*/
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("submitUnderWriteCode","王麻子");
//        map.put("firstUnderWriteCode","王麻子1");
//        map.put("secondUnderWriteCode","王麻子2");
        ActivitiUtils.startFlow(ActivityConst.ProcessInstanceKeyLeave,map);
    }
    @Test
    @ApiOperation(value="任务查询")
    public void querySubmitTask(){
        try {
            TaskService taskService = ActivitiUtils.getTaskService();
            /**排序的时候,是根据字符串类型进行排序的，所以查询出来的不是按整型从小到大排序的*/
//            List<Task> taskList = taskService.createTaskQuery().orderByExecutionId().asc().list();
             Task task = taskService.createTaskQuery().taskAssignee("王麻子").singleResult();
//            if (!CollectionUtils.isEmpty(taskList)) {
//                Task task = taskList.get(taskList.size()-1);
//                System.out.println("王麻子："+task.getId()+",executeId:"+task.getExecutionId());//张三的任务ID：2505
//            }
            System.out.println("王麻子："+task.getId()+",executeId:"+task.getExecutionId());//张三的任务ID：2505
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @ApiOperation(value="完成任务")
    public void competeTask(){
        String  taskId ="17504";
        String userId ="觉远";
        ActivitiUtils.executeTask(taskId,userId);
//        Task task = taskService.createTaskQuery().taskAssignee("王麻子").singleResult();
//        System.out.println("王麻子："+task.getId());//张三的任务ID：2505
    }

    @Test
    @ApiOperation(value="完成任务")
    public void competeTaskByMap(){
        String  taskId ="5005";
        Map<String,Object> map = new HashMap<>();
        map.put("firstUnderWriteCode","张无忌");
        ActivitiUtils.executeTaskByVariable(taskId,map);
    }



    @Test
    @ApiOperation(value="可以通过RepositoryService获取详细的流程定义信息,是表act_re_procdef中的信息")
    public void queryProcdef(){
        try {
            RepositoryService repositoryService = ActivitiUtils.getRepositoryService();
            //创建查询对象
            ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
            //执行查询获取流程定义明细
            List<ProcessDefinition> pds = query.list();
            for (ProcessDefinition pd : pds) {
                System.out.println("ID:"+pd.getId()+
                        ",NAME:"+pd.getName()+",KEY:"+pd.getKey()+
                        ",VERSION:"+pd.getVersion()+
                        ",RESOURCE_NAME:"+pd.getResourceName()+
                        ",DGRM_RESOURCE_NAME:"+pd.getDiagramResourceName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @ApiOperation(value="第五步：查看历史流程实例,根据时间降序排序",notes = "历史流程实例表：act_hi_procinst")
    public void findhistProcessInstance() {
        List<HistoricProcessInstance> list = ActivitiUtils.findhistProcessInstance(ActivityConst.ProcessInstanceKeyLeave);
        if (!CollectionUtils.isEmpty(list)) {
            for (HistoricProcessInstance hisPI : list) {
                /*开始时间和结束时间*/
                System.out.println("业务系统key\t" + hisPI.getBusinessKey());
                System.out.println("部署对象ID\t" + hisPI.getDeploymentId());
                System.out.println("执行时长\t" + hisPI.getDurationInMillis());
                System.out.println("流程定义ID\t" + hisPI.getProcessDefinitionId());
                System.out.println("流程定义的key\t" + hisPI.getProcessDefinitionKey());
                System.out.println("流程定义名称\t" + hisPI.getProcessDefinitionName());
            }
        }
    }
    @Test
    @ApiOperation(value="第六步：查看历史任务, 历史流程任务表：act_hi_taskinst")
    public void findHisTask(){
        String processInstanceId="5001";
        List<HistoricTaskInstance> list = ActivitiUtils.findHisTask(processInstanceId);
        if (!CollectionUtils.isEmpty(list)) {
            for (HistoricTaskInstance hisPI : list) {
                System.out.println("任务执行人\t" + hisPI.getAssignee());
                System.out.println("任务名称\t" + hisPI.getName());
                System.out.println("任务ID\t" + hisPI.getId());
                System.out.println("流程实例ID\t" + hisPI.getProcessInstanceId());
                System.out.println("*****************");
            }
        }
    }
    @Test
    @ApiOperation(value="查询历史流程变量list")
    public void queryHistoricVariableInstance(){
        List<HistoricVariableInstance> hviList = ActivitiUtils.queryHistoricVariableInstance();
        for(HistoricVariableInstance hvi:hviList){
            System.out.println("name: "+hvi.getVariableName()+",value:"+hvi.getValue());
        }
    }
    @Test
    @ApiOperation(value="查询历史活动list")
    public void findHistoricActivityInstance(){
        String processInstanceId="5001";
        List<HistoricActivityInstance> haiList = ActivitiUtils.findHistoricActivityInstance(processInstanceId);
        for(HistoricActivityInstance hai:haiList){
            System.out.println("name: "+hai.getId()+",value:"+hai.getStartTime());
        }
    }




}
