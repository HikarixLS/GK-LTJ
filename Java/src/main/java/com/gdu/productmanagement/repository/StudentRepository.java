package com.gdu.productmanagement.repository;

import com.gdu.productmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentCode(String studentCode);

    Optional<Student> findByEmail(String email);

    List<Student> findByFullNameContainingIgnoreCase(String fullName);

    List<Student> findByClassNameIgnoreCase(String className);

    List<Student> findByMajorContainingIgnoreCase(String major);

    List<Student> findByIsActive(Boolean isActive);

    @Query("SELECT s FROM Student s WHERE s.gpa >= :minGpa")
    List<Student> findByGpaGreaterThanEqual(@Param("minGpa") Double minGpa);

    @Query("SELECT s FROM Student s WHERE " +
            "(:searchTerm IS NULL OR " +
            "LOWER(s.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.studentCode) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Student> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.isActive = true")
    Long countActiveStudents();

    @Query("SELECT s.className, COUNT(s) FROM Student s WHERE s.isActive = true GROUP BY s.className")
    List<Object[]> countStudentsByClass();

    @Query("SELECT s.major, COUNT(s) FROM Student s WHERE s.isActive = true GROUP BY s.major")
    List<Object[]> countStudentsByMajor();

    boolean existsByStudentCode(String studentCode);

    boolean existsByEmail(String email);
}
