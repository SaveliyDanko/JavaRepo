package com.savadanko.javarepo.quartz;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Поднимаем контекст: Spring Boot стартует Quartz Scheduler, регистрирует
 * JobDetail + Trigger и начинает запускать ReportJob по расписанию.
 * Ждём несколько срабатываний.
 */
@SpringBootTest
class QuartzJobTest {

    @BeforeEach
    void reset() {
        ReportJob.resetExecutions();
    }

    @Test
    void jobFiresOnSchedule() {
        await().atMost(Duration.ofSeconds(4))
            .untilAsserted(() -> assertThat(ReportJob.getExecutions()).isGreaterThanOrEqualTo(3));
    }
}
