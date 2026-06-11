package com.savadanko.javarepo.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class TaskSchedulerTest {

    @Test
    void runOnceExecutesAfterDelay() throws InterruptedException {
        try (TaskScheduler scheduler = new TaskScheduler(1)) {
            CountDownLatch latch = new CountDownLatch(1);
            scheduler.runOnce(latch::countDown, 50, TimeUnit.MILLISECONDS);

            // ждём срабатывания; true = задача выполнилась в отведённое время
            assertTrue(latch.await(1, TimeUnit.SECONDS), "задача должна выполниться");
        }
    }

    @Test
    void fixedRateRunsRepeatedly() throws InterruptedException {
        try (TaskScheduler scheduler = new TaskScheduler(1)) {
            // ждём ровно 3 запуска
            CountDownLatch latch = new CountDownLatch(3);
            AtomicInteger counter = new AtomicInteger();

            scheduler.runAtFixedRate(() -> {
                counter.incrementAndGet();
                latch.countDown();
            }, 0, 50, TimeUnit.MILLISECONDS);

            assertTrue(latch.await(2, TimeUnit.SECONDS), "должно набраться 3 запуска");
            assertTrue(counter.get() >= 3, "счётчик должен быть не меньше 3");
        }
    }

    @Test
    void closeStopsFurtherExecutions() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        try (TaskScheduler scheduler = new TaskScheduler(1)) {
            scheduler.runAtFixedRate(counter::incrementAndGet, 0, 50, TimeUnit.MILLISECONDS);
            TimeUnit.MILLISECONDS.sleep(120);
        } // close(): планировщик остановлен

        int afterClose = counter.get();
        TimeUnit.MILLISECONDS.sleep(150);
        // после close() новых запусков быть не должно
        assertEquals(afterClose, counter.get(), "после close() счётчик не растёт");
    }
}
