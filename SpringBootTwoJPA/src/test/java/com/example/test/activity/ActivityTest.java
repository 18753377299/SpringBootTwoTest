package com.example.test.activity;

import com.example.SpringBootTwoJpaApplication;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class ActivityTest {

	/**
     	* 发布流程 
     */  
    @Test  
    public void startFlow(){  
    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        try {
			RuntimeService runtimeService = processEngine.getRuntimeService();  
			/** 
			     * 启动请假单流程  并获取流程实例 
			     * 因为该请假单流程可以会启动多个所以每启动一个请假单流程都会在数据库中插入一条新版本的流程数据 
			     * 通过key启动的流程就是当前key下最新版本的流程 
			 *  
			 */  
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");
			System.out.println("id:"+processInstance.getId()+",activitiId:"+processInstance.getActivityId());
		} catch (Exception e) {
			e.printStackTrace();
		}  
    } 
    /*可以通过RepositoryService获取详细的流程定义信息*/
    @Test  
    public void queryProcdef(){
    	try {
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			
			RepositoryService repositoryService = processEngine.getRepositoryService();  
			//创建查询对象  
			ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();  
			//添加查询条件  
//			query.processDefinitionKey("myProcess");//通过key获取  
//			query.processDefinitionName("My process");//通过name获取  
//			query.orderByProcessDefinitionId();//根据ID排序  
			
			//执行查询获取流程定义明细  
			List<ProcessDefinition> pds = query.list();  
			for (ProcessDefinition pd : pds) {  
			    System.out.println("ID:"+pd.getId()+",NAME:"+pd.getName()+",KEY:"+pd.getKey()+",VERSION:"+pd.getVersion()+",RESOURCE_NAME:"+pd.getResourceName()+",DGRM_RESOURCE_NAME:"+pd.getDiagramResourceName());  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
    }
    
    /** 
     * 查看任务 
     */  
    @Test  
    public void queryTask2(){ 
    	
    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    	
        //获取任务服务对象  
        TaskService taskService = processEngine.getTaskService();  
        //根据接受人获取该用户的任务  
        List<Task> tasks = taskService.createTaskQuery()  
                                    .taskAssignee("张三")  
                                    .list();  
        for (Task task : tasks) {  
            System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());  
        }  
    }
    /*提出请假申请，启动流程*/
    @Test  
    public void startTask2(){  
    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    	
        TaskService taskService = processEngine.getTaskService();  
        //taskId 就是查询任务中的 ID  
        String taskId = "204";  
        //完成请假申请任务  
        taskService.complete(taskId );  
    } 
    
   /* 查看数据库变化 可以看到 表中的数据已经变成了老板审批相关数据
    老板查看任务 ，并审批请假*/
    /** 
     * 查看任务 
     */  
    @Test  
    public void queryTask(){  
    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取任务服务对象  
        TaskService taskService = processEngine.getTaskService();  
        //根据接受人获取该用户的任务  
        List<Task> tasks = taskService.createTaskQuery()  
                                    .taskAssignee("老板")  
                                    .list();  
        for (Task task : tasks) {  
            System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());  
        }  
    }
    
    @Test  
    public void startTask(){  
    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();  
        //taskId 就是查询任务中的 ID  
        String taskId = "302";  
        //完成请假申请任务  
        taskService.complete(taskId );
    }
    
    /**部署流程定义*/
    @Test  
    public void deploymentProcessDefinition(){  
    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()  //用于流程定义和部署相关对象的Service  
                        .createDeployment()   //创建一个部署对象  
//                        .name("leaveBill部门程序")  
                        .addClasspathResource("processes/MyProcess.bpmn") //从ClassPath资源中加载，一次只能加载一个文件  
//                        .addClasspathResource("diagrams/LeaveBill.png")  //从ClassPath资源中加载，一次只能加载一个文件  
                        .deploy(); 
          
        System.out.println("deployment" + deployment.getId());   //1  
        System.out.println("deployment" + deployment.getName());//部门程序  
    }  
    	/*流程完毕，可以再act_hi_actinst表中看到整个请假流程
    	整个流程的过程是  1.发布流程 --->2.启动流程--->3.相关人查看任务完成并完成

    	数据都是存放在数据库中*/
    
    
}
