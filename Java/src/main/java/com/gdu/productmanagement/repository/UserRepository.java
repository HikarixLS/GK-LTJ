package com.gdu.productmanagement.repository;

import com.gdu.productmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRole(User.UserRole role);

    List<User> findByFullNameContainingIgnoreCase(String fullName);

    List<User> findByIsEnabled(Boolean isEnabled);

    @Query("SELECT u FROM User u WHERE " +
            "(:searchTerm IS NULL OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:isEnabled IS NULL OR u.isEnabled = :isEnabled)")
    Page<User> findBySearchCriteria(@Param("searchTerm") String searchTerm,
            @Param("role") User.UserRole role,
            @Param("isEnabled") Boolean isEnabled,
            Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u WHERE u.isEnabled = true")
    Long countActiveUsers();

    @Query("SELECT u.role, COUNT(u) FROM User u WHERE u.isEnabled = true GROUP BY u.role")
    List<Object[]> countUsersByRole();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
