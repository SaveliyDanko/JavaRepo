package com.savadanko.javarepo.eetimer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.ejb.Timer;
import org.junit.jupiter.api.Test;

/**
 * Полноценно таймеры исполняет EE-контейнер, которого здесь нет. Поэтому
 * проверяем логику бина напрямую: вызываем те же callback-методы, что дёргает
 * контейнер (@Schedule-метод и @Timeout-метод), и убеждаемся, что учёт работает.
 */
class ReportTimerBeanTest {

    @Test
    void automaticCallbackIncrementsCounter() {
        ReportTimerBean bean = new ReportTimerBean();

        bean.generateReport(); // имитируем срабатывание @Schedule
        bean.generateReport();

        assertEquals(2, bean.getAutomaticRuns());
    }

    @Test
    void timeoutCallbackIncrementsCounter() {
        ReportTimerBean bean = new ReportTimerBean();

        // контейнер передаёт в @Timeout объект Timer — подменяем мок
        Timer timer = mock(Timer.class);
        when(timer.getInfo()).thenReturn("cleanup");

        bean.onTimeout(timer);

        assertEquals(1, bean.getProgrammaticRuns());
    }
}
