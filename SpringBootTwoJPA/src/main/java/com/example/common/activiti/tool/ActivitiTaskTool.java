package com.example.common.activiti.tool;

import com.example.common.activiti.ActivitiUtils;
import io.swagger.annotations.Api;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Api(value="Activiti任务工具类")
@Component("activitiTaskTool")
public class ActivitiTaskTool {

    public List<Task> queryTask(){
        String userId = "dept1";
        String definedKey = "";
        List<Task> taskList =  ActivitiUtils.getTaskService().createTaskQuery()
                .taskCandidateUser(userId).processDefinitionKey(definedKey).list();
        return taskList;
    }
}
