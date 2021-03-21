package com.example.common.activiti.tool;

import com.example.common.activiti.ActivitiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.stereotype.Component;

@Api(value="Repository工具方法")
@Component("activitiRepositoryTool")
public class ActivitiRepositoryTool {
    /**
     * 部署信息表：act_re_deployment
     * 流程设计模型部署表： act_re_model
     * 流程定义数据表： act_re_procdef
     * */
    @ApiOperation(value="进行流程部署")
    public static void deploy(String bpmnNameAndKey,String resourceName) {
        RepositoryService repositoryService = ActivitiUtils.getRepositoryService();
        String keyName = "processes/" + resourceName+".bpmn20.xml";
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource(keyName)
                .key(bpmnNameAndKey)
                .name(bpmnNameAndKey + "name").category("HR").deploy();
    }


}
