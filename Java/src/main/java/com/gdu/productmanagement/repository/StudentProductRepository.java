package com.gdu.productmanagement.repository;

import com.gdu.productmanagement.entity.StudentProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudentProductRepository extends JpaRepository<StudentProduct, Long> {

    List<StudentProduct> findByStudentId(Long studentId);

    List<StudentProduct> findByProductId(Long productId);

    List<StudentProduct> findByTransactionType(StudentProduct.TransactionType transactionType);

    List<StudentProduct> findByStatus(StudentProduct.TransactionStatus status);

    @Query("SELECT sp FROM StudentProduct sp WHERE sp.transactionDate BETWEEN :startDate AND :endDate")
    List<StudentProduct> findByTransactionDateBetween(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT sp FROM StudentProduct sp WHERE " +
            "(:studentId IS NULL OR sp.student.id = :studentId) AND " +
            "(:productId IS NULL OR sp.product.id = :productId) AND " +
            "(:transactionType IS NULL OR sp.transactionType = :transactionType) AND " +
            "(:status IS NULL OR sp.status = :status)")
    Page<StudentProduct> findBySearchCriteria(@Param("studentId") Long studentId,
            @Param("productId") Long productId,
            @Param("transactionType") StudentProduct.TransactionType transactionType,
            @Param("status") StudentProduct.TransactionStatus status,
            Pageable pageable);

    @Query("SELECT SUM(sp.totalPrice) FROM StudentProduct sp WHERE sp.status = 'COMPLETED'")
    BigDecimal calculateTotalRevenue();

    @Query("SELECT sp.transactionType, COUNT(sp) FROM StudentProduct sp GROUP BY sp.transactionType")
    List<Object[]> countTransactionsByType();

    @Query("SELECT sp.status, COUNT(sp) FROM StudentProduct sp GROUP BY sp.status")
    List<Object[]> countTransactionsByStatus();

    @Query("SELECT sp.student.id, sp.student.fullName, SUM(sp.totalPrice) " +
            "FROM StudentProduct sp WHERE sp.status = 'COMPLETED' " +
            "GROUP BY sp.student.id, sp.student.fullName " +
            "ORDER BY SUM(sp.totalPrice) DESC")
    List<Object[]> findTopCustomersBySpending(Pageable pageable);

    @Query("SELECT sp.product.id, sp.product.productName, SUM(sp.quantity) " +
            "FROM StudentProduct sp WHERE sp.status = 'COMPLETED' " +
            "GROUP BY sp.product.id, sp.product.productName " +
            "ORDER BY SUM(sp.quantity) DESC")
    List<Object[]> findTopSellingProducts(Pageable pageable);
}
