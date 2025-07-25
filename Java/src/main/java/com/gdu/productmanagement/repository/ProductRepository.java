package com.gdu.productmanagement.repository;

import com.gdu.productmanagement.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByCategory(Product.ProductCategory category);

    List<Product> findByStatus(Product.ProductStatus status);

    List<Product> findByStoreId(Long storeId);

    List<Product> findByBrandContainingIgnoreCase(String brand);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= :threshold")
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);

    @Query("SELECT p FROM Product p WHERE " +
            "(:searchTerm IS NULL OR " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.productCode) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:status IS NULL OR p.status = :status)")
    Page<Product> findBySearchCriteria(@Param("searchTerm") String searchTerm,
            @Param("category") Product.ProductCategory category,
            @Param("status") Product.ProductStatus status,
            Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.status = 'ACTIVE'")
    Long countActiveProducts();

    @Query("SELECT p.category, COUNT(p) FROM Product p WHERE p.status = 'ACTIVE' GROUP BY p.category")
    List<Object[]> countProductsByCategory();

    @Query("SELECT SUM(p.stockQuantity * p.price) FROM Product p WHERE p.status = 'ACTIVE'")
    BigDecimal calculateTotalInventoryValue();

    boolean existsByProductCode(String productCode);
}
