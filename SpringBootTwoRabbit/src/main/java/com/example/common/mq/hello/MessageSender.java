package com.example.common.mq.hello;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**发送者*/
@Component
public class MessageSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    public void send() {
        String context = "hello " + new Date();
        System.out.println("{Sender}================> : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }



}
