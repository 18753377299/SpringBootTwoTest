package com.example.common.mq.many;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**接收者*/
@Component
@RabbitListener(queues = "one")
public class MessageReceiverOne {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("{Receiver1}================>: " + hello);
    }
}
