package com.savadanko.javarepo.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Тонкая обёртка над ScheduledExecutorService — стандартным планировщиком
 * задач из java.util.concurrent. Показывает три режима запуска и аккуратное
 * завершение пула потоков.
 */
public class TaskScheduler implements AutoCloseable {

    private final ScheduledExecutorService executor;

    public TaskScheduler(int poolSize) {
        // пул из poolSize потоков — задачи могут выполняться параллельно
        this.executor = Executors.newScheduledThreadPool(poolSize);
    }

    /** Выполнить задачу один раз через заданную задержку. */
    public ScheduledFuture<?> runOnce(Runnable task, long delay, TimeUnit unit) {
        return executor.schedule(task, delay, unit);
    }

    /**
     * Запускать с фиксированной частотой: каждые `period` единиц, отсчёт от
     * СТАРТА предыдущего запуска. Если задача дольше периода — запуски не
     * накладываются, а идут подряд (период «нагоняется»).
     */
    public ScheduledFuture<?> runAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return executor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /**
     * Запускать с фиксированной задержкой: пауза `delay` отсчитывается от
     * ОКОНЧАНИЯ предыдущего запуска. Гарантирует паузу между выполнениями
     * независимо от длительности задачи.
     */
    public ScheduledFuture<?> runWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        return executor.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    /**
     * Корректное завершение: перестаём принимать новые задачи и ждём
     * завершения текущих; если не уложились — принудительно глушим.
     */
    @Override
    public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
