package com.savadanko.javarepo.cron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class CronCalculatorTest {

    @Test
    void computesNextRunAfterGivenTime() {
        // "каждый час в начале часа"
        CronCalculator calc = new CronCalculator("0 0 * * * *");
        LocalDateTime from = LocalDateTime.of(2026, 6, 12, 10, 30, 0);

        assertEquals(LocalDateTime.of(2026, 6, 12, 11, 0, 0), calc.nextAfter(from));
    }

    @Test
    void listsSeveralUpcomingRuns() {
        // каждые 15 минут
        CronCalculator calc = new CronCalculator("0 */15 * * * *");
        LocalDateTime from = LocalDateTime.of(2026, 6, 12, 10, 0, 0);

        List<LocalDateTime> runs = calc.nextRuns(from, 3);

        assertEquals(List.of(
            LocalDateTime.of(2026, 6, 12, 10, 15, 0),
            LocalDateTime.of(2026, 6, 12, 10, 30, 0),
            LocalDateTime.of(2026, 6, 12, 10, 45, 0)
        ), runs);
    }

    @Test
    void respectsDayOfWeekField() {
        // в 09:00 по будням — из субботы 13.06.2026 следующий будний день = понедельник 15.06
        CronCalculator calc = new CronCalculator("0 0 9 * * MON-FRI");
        LocalDateTime saturdayEvening = LocalDateTime.of(2026, 6, 13, 18, 0, 0);

        assertEquals(LocalDateTime.of(2026, 6, 15, 9, 0, 0), calc.nextAfter(saturdayEvening));
    }

    @Test
    void validatesExpressions() {
        assertTrue(CronCalculator.isValid("0 0 * * * *"));
        assertFalse(CronCalculator.isValid("not a cron"));
        // некорректное выражение в конструкторе бросает исключение
        assertThrows(IllegalArgumentException.class, () -> new CronCalculator("99 99 99 * * *"));
    }
}
