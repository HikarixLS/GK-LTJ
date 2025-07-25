package com.gdu.productmanagement.repository;

import com.gdu.productmanagement.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreCode(String storeCode);

    List<Store> findByStoreNameContainingIgnoreCase(String storeName);

    List<Store> findByStatus(Store.StoreStatus status);

    List<Store> findByAddressContainingIgnoreCase(String address);

    List<Store> findByManagerNameContainingIgnoreCase(String managerName);

    @Query("SELECT s FROM Store s WHERE " +
            "(:searchTerm IS NULL OR " +
            "LOWER(s.storeName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.storeCode) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.managerName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "(:status IS NULL OR s.status = :status)")
    Page<Store> findBySearchCriteria(@Param("searchTerm") String searchTerm,
            @Param("status") Store.StoreStatus status,
            Pageable pageable);

    @Query("SELECT COUNT(s) FROM Store s WHERE s.status = 'ACTIVE'")
    Long countActiveStores();

    @Query("SELECT s.status, COUNT(s) FROM Store s GROUP BY s.status")
    List<Object[]> countStoresByStatus();

    boolean existsByStoreCode(String storeCode);

    boolean existsByEmail(String email);
}
