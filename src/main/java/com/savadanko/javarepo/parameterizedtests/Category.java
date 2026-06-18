package com.savadanko.javarepo.parameterizedtests;

/**
 * Результат классификации целого числа в {@link NumberClassifier#classify(int)}.
 *
 * <p>NEGATIVE/ZERO/ONE — особые краевые случаи; PRIME/COMPOSITE — «обычные»
 * положительные числа > 1. {@link #isStandard()} даёт естественный предикат
 * для демонстрации {@code @EnumSource} с фильтром {@code names}/{@code mode}.
 */
public enum Category {
    NEGATIVE, ZERO, ONE, PRIME, COMPOSITE;

    /** «Стандартная» категория — обычное положительное число > 1 (простое или составное). */
    public boolean isStandard() {
        return this == PRIME || this == COMPOSITE;
    }
}
