package com.savadanko.javarepo.scheduled;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Поднимаем контекст и ждём, пока scheduled-методы успеют отработать
 * несколько раз. Доставка асинхронная — ждём через Awaitility.
 */
@SpringBootTest
class ScheduledTasksTest {

    @Autowired
    private ScheduledTasks tasks;

    @Test
    void allSchedulesFireRepeatedly() {
        await().atMost(Duration.ofSeconds(4)).untilAsserted(() -> {
            assertThat(tasks.getFixedRateCount()).isGreaterThanOrEqualTo(3);
            assertThat(tasks.getFixedDelayCount()).isGreaterThanOrEqualTo(3);
            assertThat(tasks.getCronCount()).isGreaterThanOrEqualTo(2);
        });
    }
}
