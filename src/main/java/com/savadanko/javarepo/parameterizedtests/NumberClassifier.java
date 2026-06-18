package com.savadanko.javarepo.parameterizedtests;

/**
 * SUT (system under test) для темы «@Parameterized Tests».
 *
 * <p>Маленький детерминированный класс без состояния: его предикаты удобно
 * «прогонять» большим набором входных данных параметризованными тестами
 * (stage 02). Контракт на краях зафиксирован явно, чтобы тест-данные были
 * однозначными.
 */
public class NumberClassifier {

    /** Чётное: делится на 2 без остатка (для 0 и отрицательных тоже корректно). */
    public boolean isEven(int n) {
        return n % 2 == 0;
    }

    /** Нечётное — отрицание чётного (например, -3 нечётно). */
    public boolean isOdd(int n) {
        return !isEven(n);
    }

    /**
     * Простое число. Контракт на краях: {@code n < 2} → false (0, 1 и любые
     * отрицательные НЕ простые). Делители проверяем до {@code d*d <= n};
     * сравнение в {@code long}, чтобы не переполнить {@code int} у больших n.
     */
    public boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (long d = 2; d * d <= n; d++) {
            if (n % d == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Классифицирует число в {@link Category}: отрицательные → NEGATIVE,
     * 0 → ZERO, 1 → ONE, иначе PRIME либо COMPOSITE.
     */
    public Category classify(int n) {
        if (n < 0) {
            return Category.NEGATIVE;
        }
        if (n == 0) {
            return Category.ZERO;
        }
        if (n == 1) {
            return Category.ONE;
        }
        return isPrime(n) ? Category.PRIME : Category.COMPOSITE;
    }

    /**
     * Можно ли распарсить строку в целое число. null / пустая / из одних
     * пробелов → false; иначе true, если {@link Integer#parseInt} не бросает
     * исключение. Принимает ссылочный тип String — годится для демонстрации
     * {@code @NullSource}/{@code @EmptySource}/{@code @NullAndEmptySource}.
     */
    public boolean isParsableNumber(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
