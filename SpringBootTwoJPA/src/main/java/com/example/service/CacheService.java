package com.example.service;

import com.example.pojo.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CacheService {

    @Cacheable(value="getUser")
    public User getUser() {
        User user=new User("11", "aa", "aa123456");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

    @Cacheable(value="keyOne")
    public String getOneValue(){
        int num = new Random().nextInt();
        String key =String.valueOf(num);
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return key;
    }
    @Cacheable(value="keyTwo")
    public String getTwoValue(){
        int num = new Random().nextInt();
        String key =String.valueOf(num);
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return key;
    }

    /**设置缓存值：能够设置缓存值成功*/
    @CachePut(value={"keyOne"})
    public String  putValue(){
        return "putValue";
    }

    /**清空缓存值:能够成功，不过这个需要设置成数组形式，例：@CacheEvict(value={"keyOne","keyTwo"}) */
    @CacheEvict(value={"keyOne","keyTwo"})
    public void evict(){
    }

}
