package com.Delivery.delivery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.Delivery.delivery.service.PedidosService;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {
    @Autowired
    PedidosService pedidosService;

    @Scheduled(cron = "0 0 0 * * *")
    public void runEveryDay() {
        try {
            pedidosService.deletarPedidoByTimePerDay();
        } catch (Exception e) {
            System.out.println("\n \n \n Houve um erro: " + e + " \n \n \n");
        }
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void runEveryMonth() {
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        throw new UnsupportedOperationException("Unimplemented method 'configureTasks'");
    }

}
