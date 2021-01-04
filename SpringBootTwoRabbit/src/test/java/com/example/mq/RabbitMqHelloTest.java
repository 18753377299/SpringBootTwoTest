package com.example.mq;

import com.example.SpringBootTwoRabbitApplication;
import com.example.common.mq.hello.MessageSender;
import com.example.common.mq.many.MessageOneSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoRabbitApplication.class)
public class RabbitMqHelloTest {

    /** 注意，发送者和接收者的 queue name 必须一致，不然不能接收*/
    @Autowired
    private MessageSender messageSender;



    @Test
    public void hello() throws Exception {
        messageSender.send();
    }

}
