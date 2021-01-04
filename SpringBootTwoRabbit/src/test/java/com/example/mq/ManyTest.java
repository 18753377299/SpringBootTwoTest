package com.example.mq;

import com.example.SpringBootTwoRabbitApplication;
import com.example.common.mq.many.MessageOneSender;
import com.example.common.mq.many.MessageOneSenderTwo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoRabbitApplication.class)
public class ManyTest {

    @Autowired
    private MessageOneSender messageOneSender;

    @Autowired
    private MessageOneSenderTwo messageOneSenderTwo;

    /**一个发送者，N 个接收者会出现什么情况呢？
    * 答： 一个发送者，N个接受者,经过测试会均匀的将消息发送到N个接收者中。
    * */
    @Test
    public void oneToMany() throws Exception {
        for (int i=0;i<100;i++){
            messageOneSender.sendOneToMany(i);
        }
    }

    /**
     * 多对多发送, ( N 个发送者和 N 个接收者)
     * 结论：和一对多一样，接收端仍然会均匀接收到消息
     * */
    @Test
    public void manyToMany() throws Exception {
        for (int i=0;i<100;i++){
            messageOneSender.sendOneToMany(i);
            messageOneSenderTwo.sendManyToMany(i);
        }
    }
}
