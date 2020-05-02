package ru.sberdyshev.geekbrains.spring.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sberdyshev.geekbrains.spring.products.entity.Product;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

//    Optional<Product> findByNameAndCost(String name, Integer cost);
//
//    @Query("from Product p where p.name = :name")
//    List<Product> filterPersonByName(@Param("name") String name);

    // TODO примерный вид запроса с пагинацией
//    List<Product> findAllWithPagination(Pageable pageable);

//    @Query("from Product p where p.cost >= :minCost")
//    List<Product> getProductFilterByMinCost(@Param("minCost") BigDecimal minCost, Pageable pageable);
//
//    @Query("from Product p where p.cost <= :maxCost")
//    List<Product> getProductFilterByMaxCost(@Param("maxCost") BigDecimal maxCost, Pageable pageable);
//
//    @Query("from Product p where p.cost >= :minCost and p.cost <= :maxCost")
//    List<Product> getProductFilterByMinAndMaxCost(@Param("minCost") BigDecimal minCost, @Param("maxCost") BigDecimal maxCost, Pageable pageable);

    List<Product> findAllByCostBetween(BigDecimal minCost, BigDecimal maxCost, Pageable pageable);
    List<Product> findAllByCostLessThanEqual(BigDecimal maxCost, Pageable pageable);
    List<Product> findAllByCostGreaterThanEqual(BigDecimal minCost, Pageable pageable);
}
