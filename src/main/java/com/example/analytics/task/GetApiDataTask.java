package com.example.analytics.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetApiDataTask {


    @Scheduled(cron = "0 0 4,8,16,20 * * *")
    public void GetSomeApiDataTask(){
        log.info("Get some data - mock for task in future");
    }
}
