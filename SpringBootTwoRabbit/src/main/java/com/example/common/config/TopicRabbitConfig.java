package com.example.common.config;

import com.example.common.mqconst.RabbbitMQConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**topic 是 RabbitMQ 中最灵活的一种方式，可以根据 routing_key 自由的绑定不同的队列*/
@Configuration
public class TopicRabbitConfig {

    @Bean
    public Queue queueMessage() {
        return new Queue(RabbbitMQConst.TOPICMESSAGE);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(RabbbitMQConst.TOPICMESSAGES);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(RabbbitMQConst.TOPICEXCHANGE);
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(RabbbitMQConst.TOPICMESSAGE);
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

}
