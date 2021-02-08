package com.example.risk.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*定时任务1：*/
@Component
public class SchedulerTask {
    private int count=0;

    @Scheduled(cron="*/6 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }

}
