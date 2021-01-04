package com.example.common.mq.object;

import com.example.common.mqconst.RabbbitMQConst;
import com.example.common.vo.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbbitMQConst.OBJECT)
public class ObjectReceiver {
    /**接受者*/
    @RabbitHandler
    public void process(User user){
        System.out.println("{ObjectReceiver}================>" + user.toString() );
    }

}
