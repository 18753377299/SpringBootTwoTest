package com.example.api;

import com.example.pojo.User;
import com.example.service.CacheService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping(value="riskuser")
public class UserController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping("/getUser")
    public User getUser() {
        User user=cacheService.getUser();
        return user;
    }

    @RequestMapping("/getValue")
    public String getValue() {
        String key = cacheService.getOneValue();
        System.out.println(key);
        String keyTwo = cacheService.getTwoValue();
        System.out.println(keyTwo);
        return key;
    }
    @RequestMapping("/putValue")
    public String putValue() {
        cacheService.putValue();
        String key = cacheService.getOneValue();
        System.out.println(key);
        String keyTwo = cacheService.getTwoValue();
        System.out.println(keyTwo);
        return key;
    }
    @RequestMapping("/evict")
    public String evict() {
        cacheService.evict();
        String key = cacheService.getOneValue();
        System.out.println(key);
        String keyTwo = cacheService.getTwoValue();
        System.out.println(keyTwo);
        return key;
    }

    @ApiOperation(value="添加测试方法获取 sessionid")
    @RequestMapping("/uid")
    public String getUid(HttpSession httpSession){
       UUID uuid= (UUID)httpSession.getAttribute("uid");
       if(null==uuid){
           uuid =UUID.randomUUID();
       }
        httpSession.setAttribute("uid",uuid);
        return httpSession.getId();
    }

}
