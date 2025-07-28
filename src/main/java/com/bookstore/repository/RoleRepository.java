package com.bookstore.repository;

import com.bookstore.entity.Role;
import com.bookstore.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    // Tìm role theo tên
    Optional<Role> findByName(RoleName name);
}
