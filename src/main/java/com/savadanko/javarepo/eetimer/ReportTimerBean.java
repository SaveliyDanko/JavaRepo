package com.savadanko.javarepo.eetimer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * EJB-бин с таймерами Jakarta EE. Таймеры исполняет КОНТЕЙНЕР (GlassFish,
 * Payara, WildFly) — не Spring и не java.util.concurrent. Здесь показан только
 * API; для реального срабатывания нужен EE-контейнер.
 *
 * @Singleton — один экземпляр на приложение; @Startup — создать при запуске.
 */
@Singleton
@Startup
public class ReportTimerBean {

    // Контейнер внедряет TimerService через @Resource (это ресурс EJB-контейнера,
    // а не CDI-бин — поэтому @Resource, а не @Inject)
    @Resource
    private TimerService timerService;

    private final AtomicInteger automaticRuns = new AtomicInteger();
    private final AtomicInteger programmaticRuns = new AtomicInteger();

    /**
     * Автоматический таймер. Контейнер сам создаёт его по расписанию.
     * Поля @Schedule напоминают cron, но это СВОЙ синтаксис EJB
     * (second/minute/hour/...), не Unix-cron.
     */
    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
    public void generateReport() {
        automaticRuns.incrementAndGet();
        System.out.println("Автоматический таймер @ " + LocalTime.now());
    }

    /**
     * Программный таймер. Создаём при старте бина через TimerService:
     * одноразовый таймер на 60 секунд с произвольной "info"-нагрузкой.
     */
    @PostConstruct
    public void scheduleSingleAction() {
        if (timerService != null) { // в обычном SE-окружении сервис не внедрён
            timerService.createSingleActionTimer(60_000, new TimerConfig("cleanup", false));
        }
    }

    /**
     * Колбэк программного таймера. Контейнер вызывает @Timeout-метод, когда
     * таймер срабатывает, передавая сам Timer (из него доступна info).
     */
    @Timeout
    public void onTimeout(Timer timer) {
        programmaticRuns.incrementAndGet();
        System.out.println("Программный таймер сработал: " + timer.getInfo());
    }

    public int getAutomaticRuns() {
        return automaticRuns.get();
    }

    public int getProgrammaticRuns() {
        return programmaticRuns.get();
    }
}
