package com.example.test;

import com.example.SpringBootTwoRedisApplication;
import com.example.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**对redis进行测试*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SpringBootTwoRedisApplication.class)
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }
    /*Spring Data Redis  操作实体对象*/
    /**
     * 添加 Users 对象
     */
    @Test
    public void setUser(){
        User user = new User();
        user.setInfo("20");
        user.setName("张三丰");
        user.setId("2");
        //重新设置序列化器
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.opsForValue().set("user", user);
    }

    @Test
    public void testObj() throws Exception {
        User user=new User("1", "aa", "aa123456");
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        operations.set("user_two", user);
        operations.set("user_two_f", user,100, TimeUnit.HOURS);
//        Thread.sleep(1000);
        //redisTemplate.delete("com.neo.f");
        boolean exists=redisTemplate.hasKey("user_two");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }
        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
}
