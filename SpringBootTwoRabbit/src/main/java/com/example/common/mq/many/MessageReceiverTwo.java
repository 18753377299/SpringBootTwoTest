package com.example.common.mq.many;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**接收者*/
@Component
@RabbitListener(queues = "one")
public class MessageReceiverTwo {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("{Receiver2}================>: " + hello);
    }
}
