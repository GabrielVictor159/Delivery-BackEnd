package com.Delivery.delivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    @Scheduled(cron = "0 0 0 * * *")
    public void runEveryDay() {
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void runEveryMonth() {
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'configureTasks'");
    }

}
