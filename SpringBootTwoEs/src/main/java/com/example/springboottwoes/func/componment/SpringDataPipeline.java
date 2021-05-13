package com.example.springboottwoes.func.componment;

import com.example.springboottwoes.func.po.JobInfo;
import com.example.springboottwoes.func.service.facade.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

@Component
public class SpringDataPipeline implements Pipeline {

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取需要保存到MySQL的数据
        JobInfo jobInfo = resultItems.get("jobInfo");

        //判断获取到的数据不为空
        if(jobInfo!=null) {
            //如果有值则进行保存
            this.jobInfoService.save(jobInfo);
        }
    }
}

