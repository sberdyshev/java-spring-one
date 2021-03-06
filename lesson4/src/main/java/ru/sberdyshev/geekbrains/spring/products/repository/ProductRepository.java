package ru.sberdyshev.geekbrains.spring.products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberdyshev.geekbrains.spring.products.entity.Product;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCostBetween(BigDecimal minCost, BigDecimal maxCost, Pageable pageable);

    Page<Product> findAllByCostLessThanEqual(BigDecimal maxCost, Pageable pageable);

    Page<Product> findAllByCostGreaterThanEqual(BigDecimal minCost, Pageable pageable);
}
