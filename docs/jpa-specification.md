# JpaSpecificationExecutor (Spring Data JPA)

Краткая теоретическая сводка. Код — в `src/main/java/com/savadanko/javarepo/jpaspecification/`.

## Что это и зачем

`JpaSpecificationExecutor<T>` — интерфейс Spring Data JPA, который добавляет
репозиторию методы для **динамических, типобезопасных** запросов через
`Specification<T>`. Репозиторий наследует его рядом с `JpaRepository`:

```java
interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {}
```

Появляются методы: `findAll(spec)`, `findAll(spec, Pageable)`,
`findOne(spec)`, `count(spec)`, `exists(spec)`, `delete(spec)`.

**Проблема, которую решает:** при derived-методах под каждую комбинацию
фильтров приходится плодить `findByCategory`, `findByCategoryAndInStock`,
`findByCategoryAndPriceLessThan`… — комбинаторный взрыв. Specification позволяет
собрать WHERE из кусочков **в рантайме** под те фильтры, что реально заданы.

## Specification и Criteria API

`Specification<T>` — функциональный интерфейс:

```java
Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
```

- **`root`** — «таблица» сущности: `root.get("price")` ссылается на столбец
  (по имени поля, без сырого SQL).
- **`cb`** (CriteriaBuilder) — фабрика условий: `equal`, `like`, `lower`,
  `greaterThanOrEqualTo`, `lessThanOrEqualTo`, `isTrue`, `between`…
- **`query`** — сам запрос (можно влиять на `distinct`, сортировку и т.п.).

Пример одного кусочка:

```java
static Specification<Product> priceAtMost(BigDecimal max) {
    return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), max);
}
```

## Композиция (главная фишка)

Спецификации соединяются логическими операторами:

- **`spec1.and(spec2)`** — `WHERE c1 AND c2`;
- **`spec1.or(spec2)`** — `WHERE c1 OR c2`;
- **`Specification.not(spec)`** — отрицание;
- **`Specification.allOf(list)` / `anyOf(list)`** — AND / OR над коллекцией
  (удобно, когда часть фильтров может отсутствовать; пустой список = без WHERE).

Динамический поиск (см. `ProductSearchService`): собираем список только из
заданных условий и одним `findAll(Specification.allOf(parts))` получаем выборку.

```java
List<Specification<Product>> parts = new ArrayList<>();
if (category != null) parts.add(hasCategory(category));
if (maxPrice != null) parts.add(priceAtMost(maxPrice));
return repository.findAll(Specification.allOf(parts));
```

## Чем отличается от других подходов

- **Derived queries** (`findByCategory`) — просто, но не масштабируется на
  множество опциональных фильтров.
- **`@Query` (JPQL/native)** — гибко, но статичная строка; динамику делают
  через громоздкий `WHERE (:cat IS NULL OR category = :cat)`.
- **Specification** — типобезопасно (нет строк-полей в коде запроса),
  переиспользуемо и собирается в рантайме. Цена — многословность Criteria API.
- **Querydsl** — альтернатива с более приятным DSL, но требует кодогенерации.

## Запуск

Встроенная H2 — внешний сервер не нужен.

```bash
./gradlew test       # @DataJpaTest на H2: спеки, их композиция, динамический поиск
./gradlew bootRun    # DemoRunner наполняет H2 и печатает выборки по разным фильтрам
```

> `spring.jpa.show-sql=true` в `application.yml` — в логах видно, какой именно
> SQL собрала Specification.
