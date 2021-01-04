package com.example.mq;

import com.example.common.mq.object.ObjectSender;
import com.example.common.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectTest {

	@Autowired
	private ObjectSender objectSender;

	/**对象的支持
	Spring Boot 以及完美的支持对象的发送和接收，不需要格外的配置。*/
	@Test
	public void sendOject() throws Exception {
		User user=new User();
		user.setName("neo");
		user.setPass("123456");
		objectSender.sendObject(user);
	}

}