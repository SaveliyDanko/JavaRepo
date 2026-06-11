package com.savadanko.javarepo.quartz;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Quartz-джоб — единица работы. Реализует интерфейс Job; метод execute()
 * вызывается планировщиком при срабатывании триггера.
 *
 * Бин Spring (@Component), поэтому в джоб можно внедрять зависимости —
 * за это отвечает SpringBeanJobFactory из spring-boot-starter-quartz.
 *
 * Через JobDataMap (job.getJobDataMap()) в джоб можно прокинуть параметры —
 * здесь читаем "reportName", заданный в JobDetail.
 */
@Component
public class ReportJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(ReportJob.class);

    // статический счётчик: тест проверяет, что джоб срабатывает по триггеру
    private static final AtomicInteger executions = new AtomicInteger();

    @Override
    public void execute(JobExecutionContext context) {
        String reportName = context.getMergedJobDataMap().getString("reportName");
        executions.incrementAndGet();
        log.info("ReportJob '{}' выполнен @ {}", reportName, LocalTime.now());
    }

    public static int getExecutions() {
        return executions.get();
    }

    public static void resetExecutions() {
        executions.set(0);
    }
}
