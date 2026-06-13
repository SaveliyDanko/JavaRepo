package com.savadanko.javarepo.jpaspecification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Динамический поиск: ради этого и нужен JpaSpecificationExecutor.
 *
 * Любой из параметров фильтра может быть null («не фильтровать по нему»).
 * Мы собираем Specification только из заданных условий и одним вызовом
 * repository.findAll(spec) получаем нужную выборку — без отдельного метода
 * репозитория под каждую комбинацию фильтров.
 */
@Service
public class ProductSearchService {

    private final ProductRepository repository;

    public ProductSearchService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> search(String category, BigDecimal maxPrice, Boolean onlyInStock) {
        List<Specification<Product>> parts = new ArrayList<>();

        if (category != null) {
            parts.add(ProductSpecifications.hasCategory(category));
        }
        if (maxPrice != null) {
            parts.add(ProductSpecifications.priceAtMost(maxPrice));
        }
        if (Boolean.TRUE.equals(onlyInStock)) {
            parts.add(ProductSpecifications.inStock());
        }

        // allOf соединяет все части через AND; пустой список => без WHERE (все записи)
        return repository.findAll(Specification.allOf(parts));
    }
}
