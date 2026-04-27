package com.api.productengine.repository;

import com.api.productengine.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    // 1. JPQL with Named Parameters
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% AND p.price <= :maxPrice")
    List<Product> searchProducts(@Param("keyword") String keyword, @Param("maxPrice") Double maxPrice);

    // 2. Native Query (Standard SQL)
    @Query(value = "SELECT SUM(price * stock) FROM products", nativeQuery = true)
    Double findTotalStockValue();

    // 3. Modifying Query (Update)
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stock = :newStock WHERE p.id = :id")
    int updateProductStock(@Param("id") Long id, @Param("newStock") Integer newStock);

    // 4. Aggregate JPQL (Average Price)
    @Query("SELECT AVG(p.price) FROM Product p")
    BigDecimal findAveragePrice();

    // 5. JPQL with BETWEEN operator
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max")
    List<Product> findByPriceRange(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // 6. JPQL with literal values
    @Query("SELECT p FROM Product p WHERE p.stock = 0")
    List<Product> findOutOfStockProducts();

    // 7. JPQL with String functions
    @Query("SELECT p FROM Product p WHERE UPPER(p.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Product> findByNameCaseInsensitive(@Param("name") String name);
}