package com.example.common.mq.object;

import com.example.common.mqconst.RabbbitMQConst;
import com.example.common.vo.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**发送对象的队列*/

@Component
public class ObjectSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendObject(User user){
        System.out.println("{ObjectSender}===============>"+user.toString());
        amqpTemplate.convertAndSend(RabbbitMQConst.OBJECT,user);
    }
}
