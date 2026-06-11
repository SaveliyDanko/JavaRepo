package com.savadanko.javarepo.cron;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.scheduling.support.CronExpression;

/**
 * Тонкая обёртка над Spring CronExpression. Показывает суть cron «в чистом
 * виде»: разбор выражения и вычисление моментов срабатывания — без какого-либо
 * планировщика (сравни с темами spring-scheduled и quartz, где cron был внутри
 * планировщика).
 *
 * Spring-формат cron: 6 полей —
 *   секунды минуты часы день-месяца месяц день-недели
 */
public class CronCalculator {

    private final CronExpression expression;

    public CronCalculator(String cron) {
        // parse кидает IllegalArgumentException при некорректном выражении
        this.expression = CronExpression.parse(cron);
    }

    /** Ближайшее срабатывание строго после указанного момента. */
    public LocalDateTime nextAfter(LocalDateTime from) {
        return expression.next(from);
    }

    /** Следующие count моментов срабатывания, начиная после from. */
    public List<LocalDateTime> nextRuns(LocalDateTime from, int count) {
        List<LocalDateTime> runs = new ArrayList<>(count);
        LocalDateTime current = from;
        for (int i = 0; i < count; i++) {
            current = expression.next(current);
            if (current == null) {
                break; // выражение больше никогда не сработает
            }
            runs.add(current);
        }
        return runs;
    }

    /** Корректно ли выражение (без выбрасывания исключения). */
    public static boolean isValid(String cron) {
        return CronExpression.isValidExpression(cron);
    }
}
