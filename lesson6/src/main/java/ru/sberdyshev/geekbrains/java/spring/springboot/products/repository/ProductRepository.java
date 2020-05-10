package ru.sberdyshev.geekbrains.java.spring.springboot.products.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberdyshev.geekbrains.java.spring.springboot.products.domain.Product;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCostBetween(BigDecimal minCost, BigDecimal maxCost, Pageable pageable);

    Page<Product> findAllByCostLessThanEqual(BigDecimal maxCost, Pageable pageable);

    Page<Product> findAllByCostGreaterThanEqual(BigDecimal minCost, Pageable pageable);
}
