package com.example.common.mq.many;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**发送者*/
@Component
public class MessageOneSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendOneToMany(int i){
        String context = "helloOneToMany =1==>"+i+":";
        System.out.println("{Sender}================> : "+ context);
        this.rabbitTemplate.convertAndSend("one", context);
    }




}
