package com.savadanko.javarepo.parameterizedtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Учебный пример темы «@Parameterized Tests» (JUnit 5).
 *
 * <p>Один тест-класс показывает ключевые источники параметров. Каждый метод —
 * «срез» на ОДИН источник; методы идут сверху вниз от простого к сложному,
 * чтобы источники было удобно сравнивать в одном месте. SUT —
 * {@link NumberClassifier} (детерминированный, без состояния и без I/O).
 *
 * <p>В отличие от {@code @Test}, метод с {@code @ParameterizedTest}
 * запускается по разу на каждый набор аргументов из источника; атрибут
 * {@code name} задаёт читаемое имя кейса в отчёте.
 */
class NumberClassifierParameterizedTest {

    private final NumberClassifier classifier = new NumberClassifier();

    // 1. @ValueSource — простейший источник: список литералов одного типа.
    //    На каждый литерал — один запуск с единственным параметром.

    @ParameterizedTest(name = "{0} -> even")
    @ValueSource(ints = {2, 4, 6, 100, -8})
    void evenNumbers(int n) {
        assertTrue(classifier.isEven(n));
    }

    @ParameterizedTest(name = "{0} -> odd")
    @ValueSource(ints = {1, 3, 7, 99, -5})
    void oddNumbers(int n) {
        assertTrue(classifier.isOdd(n));
    }

    // 2. @MethodSource — параметры вычисляет код, когда литералов мало или их
    //    удобнее построить. Имя в аннотации = имя static-метода-фабрики.

    @ParameterizedTest(name = "{0} is prime")
    @MethodSource("primes")
    void primeNumbers(int n) {
        assertTrue(classifier.isPrime(n));
    }

    @ParameterizedTest(name = "{0} is not prime")
    @MethodSource("nonPrimes")
    void notPrime(int n) {
        assertFalse(classifier.isPrime(n));
    }

    static IntStream primes() {
        return IntStream.of(2, 3, 5, 7, 11, 13);
    }

    static IntStream nonPrimes() {
        return IntStream.of(0, 1, 4, 9, -3);
    }

    // 3. @CsvSource — табличные кейсы «вход -> ожидание». JUnit сам
    //    конвертирует строку в нужный тип параметра: "2" -> int,
    //    "PRIME" -> enum Category (встроенная конверсия, без кастомного конвертера).

    @ParameterizedTest(name = "classify({0}) = {1}")
    @CsvSource({"2, PRIME", "4, COMPOSITE", "0, ZERO", "1, ONE", "-3, NEGATIVE"})
    void classifyFromCsv(int input, Category expected) {
        assertEquals(expected, classifier.classify(input));
    }

    // 4. @EnumSource — перебор констант enum. Без фильтра берёт все константы;
    //    через names + mode можно включить (INCLUDE, по умолчанию) или
    //    исключить (EXCLUDE) подмножество.

    @ParameterizedTest(name = "{0}: isStandard() согласован")
    @EnumSource(Category.class)
    void everyCategoryIsConsistent(Category c) {
        assertNotNull(c);
        assertEquals(c == Category.PRIME || c == Category.COMPOSITE, c.isStandard());
    }

    @ParameterizedTest(name = "{0} is standard")
    @EnumSource(value = Category.class, names = {"PRIME", "COMPOSITE"})
    void standardCategories(Category c) {
        assertTrue(c.isStandard());
    }

    @ParameterizedTest(name = "{0} is special (not standard)")
    @EnumSource(value = Category.class, names = {"PRIME", "COMPOSITE"}, mode = EnumSource.Mode.EXCLUDE)
    void specialCategories(Category c) {
        assertFalse(c.isStandard());
    }

    // 5. Null/Empty источники — краевые значения для ССЫЛОЧНЫХ типов (String).
    //    Несколько источников можно комбинировать на одном методе.

    @ParameterizedTest(name = "[{0}] не парсится в число")
    @NullSource
    @EmptySource
    void blankIsNotParsable(String text) {
        assertFalse(classifier.isParsableNumber(text));
    }

    @ParameterizedTest(name = "[{0}] не парсится в число")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "abc"})
    void nullAndEmptyCombined(String text) {
        assertFalse(classifier.isParsableNumber(text));
    }
}
