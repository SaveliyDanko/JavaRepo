package com.savadanko.javarepo.jpaspecification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Ключевая связка темы: репозиторий наследует ДВА интерфейса.
 *
 *  - JpaRepository           — обычные CRUD + derived/@Query методы;
 *  - JpaSpecificationExecutor — добавляет методы, принимающие Specification:
 *      findAll(spec), findAll(spec, Pageable), findOne(spec), count(spec)...
 *
 * Благодаря этому один и тот же запрос можно собирать ДИНАМИЧЕСКИ из кусочков
 * (Specification), не плодя десятки методов findByXAndYAndZ под каждую комбинацию.
 */
public interface ProductRepository
        extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
