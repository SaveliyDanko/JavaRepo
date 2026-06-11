package com.savadanko.javarepo.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Связываем джоб с расписанием через два бина:
 *  - JobDetail — описание джоба (класс, имя, параметры в JobDataMap);
 *  - Trigger   — когда и как часто запускать джоб.
 * Spring Boot сам зарегистрирует их в Scheduler при старте.
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail reportJobDetail() {
        return JobBuilder.newJob(ReportJob.class)
            .withIdentity("reportJob")
            // storeDurably — JobDetail может существовать без привязанного триггера
            .storeDurably()
            .usingJobData("reportName", "daily-report")
            .build();
    }

    @Bean
    public Trigger reportJobTrigger(JobDetail reportJobDetail) {
        // SimpleTrigger: повтор с фиксированным интервалом, бесконечно
        return TriggerBuilder.newTrigger()
            .forJob(reportJobDetail)
            .withIdentity("reportTrigger")
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(500)
                .repeatForever())
            .build();
    }
}
