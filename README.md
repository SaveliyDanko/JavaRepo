# JavaRepo — ветка `jpa-specification`

Тема: **JpaSpecificationExecutor** — динамические типобезопасные запросы в
Spring Data JPA через `Specification` (Criteria API). БД — встроенная H2.

> БД in-memory: внешний сервер не нужен, тесты идут всегда. Базовый каркас
> живёт в `main`.

## Что внутри

```
src/main/java/com/savadanko/javarepo/jpaspecification/
├── JpaSpecificationApplication.java  # точка входа Spring Boot
├── Product.java                      # сущность (name, category, price, inStock)
├── ProductRepository.java            # JpaRepository + JpaSpecificationExecutor
├── ProductSpecifications.java        # переиспользуемые Specification (фильтры)
├── ProductSearchService.java         # динамический поиск из опциональных фильтров
└── DemoRunner.java                   # CommandLineRunner: наполнение H2 + демо-выборки
src/main/resources/application.yml    # настройки H2 + show-sql
src/test/java/.../ProductSpecificationsTest.java   # спеки и их композиция (@DataJpaTest)
src/test/java/.../ProductSearchServiceTest.java    # динамический поиск
```

## Теория

Краткая теоретическая сводка по теме — в [docs/jpa-specification.md](docs/jpa-specification.md).

## Команды

```bash
./gradlew test       # @DataJpaTest на H2: одиночные спеки, AND/OR-композиция, динамический поиск
./gradlew bootRun    # DemoRunner наполняет H2 и печатает выборки по разным фильтрам
```
