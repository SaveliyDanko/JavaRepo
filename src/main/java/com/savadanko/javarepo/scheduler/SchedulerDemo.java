package com.savadanko.javarepo.scheduler;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * Небольшая демонстрация: запускаем повторяющуюся задачу и одноразовую,
 * даём поработать пару секунд и аккуратно завершаемся.
 */
public class SchedulerDemo {

    public static void main(String[] args) throws InterruptedException {
        // try-with-resources вызовет close() и корректно остановит планировщик
        try (TaskScheduler scheduler = new TaskScheduler(2)) {
            scheduler.runAtFixedRate(
                () -> System.out.println("tick @ " + LocalTime.now()),
                0, 500, TimeUnit.MILLISECONDS);

            scheduler.runOnce(
                () -> System.out.println("одноразовая задача через 1с"),
                1, TimeUnit.SECONDS);

            // даём задачам поработать, иначе close() остановит их сразу
            TimeUnit.SECONDS.sleep(2);
        }
        System.out.println("планировщик остановлен");
    }
}
