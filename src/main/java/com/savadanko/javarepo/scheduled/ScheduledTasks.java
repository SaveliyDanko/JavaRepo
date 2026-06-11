package com.savadanko.javarepo.scheduled;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Бин со scheduled-методами. Spring сам находит @Scheduled-методы в бинах и
 * вызывает их по расписанию на отдельном пуле потоков — руками планировщик
 * создавать не нужно (сравни с темой scheduler / ScheduledExecutorService).
 *
 * Методы @Scheduled не должны принимать аргументы и возвращают void.
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    // счётчики нужны, чтобы тест мог убедиться в срабатывании
    private final AtomicInteger fixedRateCount = new AtomicInteger();
    private final AtomicInteger fixedDelayCount = new AtomicInteger();
    private final AtomicInteger cronCount = new AtomicInteger();

    /** Запуск каждые 500 мс, отсчёт от старта предыдущего запуска. */
    @Scheduled(fixedRate = 500)
    public void atFixedRate() {
        fixedRateCount.incrementAndGet();
        log.info("fixedRate tick @ {}", LocalTime.now());
    }

    /** Пауза 500 мс отсчитывается от окончания предыдущего запуска. */
    @Scheduled(fixedDelay = 500, initialDelay = 200)
    public void withFixedDelay() {
        fixedDelayCount.incrementAndGet();
    }

    /** Cron-выражение: здесь — в начале каждой секунды (поле секунд = «*»). */
    @Scheduled(cron = "* * * * * *")
    public void onCron() {
        cronCount.incrementAndGet();
    }

    public int getFixedRateCount() {
        return fixedRateCount.get();
    }

    public int getFixedDelayCount() {
        return fixedDelayCount.get();
    }

    public int getCronCount() {
        return cronCount.get();
    }
}
