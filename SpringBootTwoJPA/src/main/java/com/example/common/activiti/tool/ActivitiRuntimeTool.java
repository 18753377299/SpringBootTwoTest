package com.example.common.activiti.tool;

import com.example.common.activiti.ActivitiUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import java.util.Map;

@Api(value="Activiti流程工具类")
@Component("activitiRuntimeTool")
public class ActivitiRuntimeTool {
    /**
     * 第二步： 发布流程
     * 流程发布后在  act_ru_task ，act_ru_execution， act_ru_identitylink 表中插入流程数据
     * 接下来就可以通过用户ID去查看该用户的任务了
     */
    @ApiOperation(value="发布流程,指定人员")
    public ProcessInstance startFlow(String  processInstanceByKey, Map<String,Object> map ){
        ProcessInstance processInstance = ActivitiUtils.getRuntimeService().
                    startProcessInstanceByKey(processInstanceByKey,map);
        return processInstance;
    }
}
