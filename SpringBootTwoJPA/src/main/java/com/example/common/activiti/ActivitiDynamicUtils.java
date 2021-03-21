package com.example.common.activiti;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@Api(value="activiti通过代码绘制流程图")
public class ActivitiDynamicUtils {
    @ApiOperation(value="创建bpmn模型")
    public static BpmnModel createBpmnModel(){
        BpmnModel model = new BpmnModel();
        Process process =new Process();
        model.addProcess(process);
        process.setId(ActivityConst.ProcessInstanceKeyTest);
        return model;
    }

    @ApiOperation(value="创建bpmn元素")
    public static  void  createBpmnElement(){
        List<Process> processList = createBpmnModel().getProcesses();
        Process process = null;
        if(!CollectionUtils.isEmpty(processList)){
            process = processList.get(0);
            process.addFlowElement(createStartEvent());
            process.addFlowElement(createUserTask("task1", "First task", "fred"));
            process.addFlowElement(createUserTask("task2", "Second task", "john"));
            process.addFlowElement(createEndEvent());
            /* 使用箭头将任务连接*/
            process.addFlowElement(createSequenceFlow("start", "task1"));
            process.addFlowElement(createSequenceFlow("task1", "task2"));
            process.addFlowElement(createSequenceFlow("task2", "end"));
        }
//        Process process = createBpmnModel().getProcess(ActivityConst.ProcessInstanceKeyTest);

    }

    @ApiOperation(value="2.生成BPMN自动布局")
    public static void createBpmnAutoLayout(){
        new BpmnAutoLayout(createBpmnModel()).execute();
    }

    @ApiOperation(value="创建bpmn任务",notes="")
    protected static UserTask createUserTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }
    @ApiOperation(value="创建箭头",notes="")
    protected static  SequenceFlow createSequenceFlow(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    @ApiOperation(value="创建开始事件")
    protected static StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }
    @ApiOperation(value="创建结束事件")
    protected static EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }


}
