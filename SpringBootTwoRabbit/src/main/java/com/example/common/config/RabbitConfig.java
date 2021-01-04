package com.example.common.config;


import com.example.common.mqconst.RabbbitMQConst;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**队列配置*/
@Configuration
public class RabbitConfig {

    @Bean
    public Queue Queue() {
        return new Queue(RabbbitMQConst.HELLO);
    }

    /*进行一对多队列配置*/
    @Bean
    public Queue oneToManyQueue(){
        return new Queue(RabbbitMQConst.ONETOMANYQUEUE,true);
    }

    /*对象的支持,对象队列*/
    @Bean
    public Queue objectQueue(){
        return new Queue(RabbbitMQConst.OBJECT,true);
    }

}
