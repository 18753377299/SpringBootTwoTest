package com.example.mq;

import com.example.common.mq.fanout.FanoutSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutTest {
	/*结果如下：
	Sender : hi, fanout msg
...
	fanout Receiver B: hi, fanout msg
	fanout Receiver A  : hi, fanout msg
	fanout Receiver C: hi, fanout msg
	结果说明，绑定到 fanout 交换机上面的队列都收到了消息*/
	@Autowired
	private FanoutSender sender;

	@Test
	public void fanoutSender() throws Exception {
		sender.send();
	}


}