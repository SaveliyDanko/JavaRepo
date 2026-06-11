package com.savadanko.javarepo.cron;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Демонстрация: для нескольких cron-выражений печатаем ближайшие срабатывания.
 * Никакого планировщика — только разбор выражения и арифметика дат.
 */
public class CronDemo {

    public static void main(String[] args) {
        // выражение -> человеческое описание
        Map<String, String> samples = Map.of(
            "0 0 * * * *",        "каждый час в начале часа",
            "0 */15 * * * *",     "каждые 15 минут",
            "0 0 9 * * MON-FRI",  "в 09:00 по будням",
            "0 0 0 1 * *",        "в полночь 1-го числа каждого месяца"
        );

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Сейчас: " + now + "\n");

        samples.forEach((cron, description) -> {
            System.out.println(cron + "  — " + description);
            CronCalculator calc = new CronCalculator(cron);
            calc.nextRuns(now, 3).forEach(run -> System.out.println("    → " + run));
            System.out.println();
        });
    }
}
