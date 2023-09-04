package com.mig.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2023-08-26 11:50
 */
@Configuration
public class DynamicScheduleConfigurer implements SchedulingConfigurer
{
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
    {

        // 2. 添加任务，修改属性
        taskRegistrar.addTriggerTask(
                // 添加定时任务
                () -> System.out.println("xxx"),
                // 设置执行周期(Trigger)
                triggerContext -> {//0 15 10 ? * MON-FRI
                    String cron = "0/2 30 09,12 * * ? ";
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
}

