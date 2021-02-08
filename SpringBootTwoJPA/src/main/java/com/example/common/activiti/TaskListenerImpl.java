package com.example.common.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {

    public void notify(DelegateTask delegateTask){
        System.out.println("流程到了指定节点");
        delegateTask.setAssignee("李四");
    }

}
