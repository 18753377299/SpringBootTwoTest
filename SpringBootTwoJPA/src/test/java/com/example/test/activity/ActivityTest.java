package com.example.test.activity;

import com.example.SpringBootTwoJpaApplication;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.InputStream;
import java.util.List;
/**流程实例只有一个，但是执行的对象可能有多个*/
/**
 * 1、资源库流程规则表
   部署信息表：（ID是由act_ge_property表的next_dbid决定）
    act_re_deployment
    *  流程设计模型部署表： act_re_model
 *  *  流程定义数据表：（该表的key属性是bpmn的ID决定，
 *   该表的name属性是bpmn的name属性决定）
 *    act_re_procdef
 *    通用字节资源表： act_ge_bytearray
 *  2、运行时数据库表
 *     运行时流程执行实例表：act_ru_execution
 *     运行时流程人员表，主要存储任务节点和参与者的相关信息：act_ru_identitylink
 *     运行时任务节点表：act_ru_task
 *     运行时流程变量数据表： act_ru_variable
 *  3、 历史数据表
 *  历史节点表： act_hi_actinst
 *
 *  4、通用属性表
 *   通用属性表，ID生成策略 ，next.dbid 影响的是部署的ID：
 *   act_ge_property
 *   5、 流程实例与任务
 *     流程执行对象信息： act_ru_execution
 *     正在运行的任务表：act_ru_task
 *
 *     历史流程实例表：act_hi_procinst
 *     历史流程任务表：act_hi_taskinst
 *  */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoJpaApplication.class)
public class ActivityTest {

	/**
     * 第二步： 发布流程
     * 流程发布后在  act_ru_task ，act_ru_execution， act_ru_identitylink 表中插入流程数据
     * 接下来就可以通过用户ID去查看该用户的任务了
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
			 */
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
			System.out.println("流程实例的id: "+processInstance.getId()+
                    ",流程定义的ID："+processInstance.getProcessDefinitionId()+
                    ",activitiId: "+processInstance.getActivityId()+
                    ",流程实现的ID"+processInstance.getProcessInstanceId());
		} catch (Exception e) {
			e.printStackTrace();
		}
    } 
    /**可以通过RepositoryService
     * 获取详细的流程定义信息*/
    @Test
    public void queryProcdef(){
    	try {
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			
			RepositoryService repositoryService = processEngine.getRepositoryService();  
			//创建查询对象  
			ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();  
			//添加查询条件
//            query.processDefinitionId(""); // 流程定义的ID（组成： proDefikey(流程定义的key)+version(版本)+自动生成的ID）
//			query.processDefinitionKey("myProcess");//通过key获取 （流程定义的key，，由process的ID属性决定）
//			query.processDefinitionName("My process");//通过name获取  （流程定义的name）
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
//        添加办理人
//        taskService.setAssignee(taskId,userId);
        //根据接受人获取该用户的任务
        List<Task> tasks = taskService.createTaskQuery()  
                                    .taskAssignee("张三")  
                                    .list();  
        for (Task task : tasks) {  
            System.out.println("ID:"+task.getId()+",姓名:"+task.getName()+",接收人:"+task.getAssignee()+",开始时间:"+task.getCreateTime());  
        }
    }
    /**提出请假申请，启动流程
    * 流程完毕，可以再act_hi_actinst表中看到整个请假流程。
     * 整个流程的过程是  1.发布流程 --->2.启动流程--->3.相关人查看任务完成并完成
     * 数据都是存放在数据库中
    * */
    @Test  
    public void startTask2(){  
    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    	
        TaskService taskService = processEngine.getTaskService();  
        //taskId 就是查询任务中的 ID  
        String taskId = "d9427be6e6d8470a8ffd528948acd535";
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
    
    /**
     * 第一步： 部署流程定义
     *  部署信息表：act_re_deployment
     *  流程设计模型部署表： act_re_model
     *  流程定义数据表： act_re_procdef
     * */
    @Test  
    public void deploymentProcessDefinition(){  
    	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()  //用于流程定义和部署相关对象的Service  
                        .createDeployment()   //创建一个部署对象
                        .key("leave")
                        .name("请假流程")//设置部署的名称
                        .category("HR") // 设置部署的类别
                        .addClasspathResource("processes/Test.bpmn20.xml") //从ClassPath资源中加载，一次只能加载一个文件
//                        .addClasspathResource("diagrams/LeaveBill.png")  //从ClassPath资源中加载，一次只能加载一个文件  
                        .deploy();

        System.out.println("流程部署ID\t" + deployment.getId());
        System.out.println("流程keyID\t" + deployment.getKey());
        System.out.println("流程名称ID\t" + deployment.getName());
        System.out.println("流程分类ID\t" + deployment.getCategory());
    }

    	/**流程完毕，可以再act_hi_actinst表中看到整个请假流程
    	整个流程的过程是  1.发布流程 --->2.启动流程--->3.相关人查看任务完成并完成
    	数据都是存放在数据库中*/
    /*查看bpmn资源图片*/
    @Test
    public void viewImage(){
        String deploymentId ="801";
        String imageName =null;
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /**取得某个部署资源的名称 deploymentId*/
        List<String>  resourceNames= processEngine.getRepositoryService().
                getDeploymentResourceNames(deploymentId);
        for(String temp : resourceNames){
            if(temp.indexOf(".png")>0){
                imageName = temp;
            }
        }
//        读取资源
        InputStream resourceAsStream =processEngine.getRepositoryService().getResourceAsStream(deploymentId,imageName);
        File file = new File("D:/"+imageName);
//        FileUtils.copyInputStreamToFile(resourceAsStream,file);
    }
    /**通过部署id删除流程定义*/
    @Test
    public void deleteProcessDefinition(){
        String deploymentId="101";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
       /** 通过部署id删除流程定义*/
        processEngine.getRepositoryService().deleteDeployment(deploymentId);
    }

    /**获取流程实例的状态;
     * 运行时流程执行实例表：act_ru_execution
     * */
    @Test
    public void getProcessInstanceState(){

        String processInstanceId ="801";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
       ProcessInstance pi = processEngine.getRuntimeService().
        createProcessInstanceQuery()
        .processInstanceId(processInstanceId)
        .singleResult() //返回的数据要么是单行，要么是空，其他情况报错
        ;
        /*判断流程实例的状态*/
        if(null!=pi){
            System.out.println("该流程实例"+processInstanceId
                    +"正在运行。。。，当前活动的任务"+pi.getActivityId());
        }else{
            System.out.println("当前的流程实例"+processInstanceId
                    +"已经结束！");
        }
    }





}
