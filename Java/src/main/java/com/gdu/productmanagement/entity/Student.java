package com.gdu.productmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Column(name = "student_code", unique = true, nullable = false, length = 20)
    private String studentCode;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2-100 ký tự")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Email(message = "Email không hợp lệ")
    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải từ 10-11 chữ số")
    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "address", length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth_date")
    private java.time.LocalDate birthDate;

    @Column(name = "class_name", length = 50)
    private String className;

    @Column(name = "major", length = 100)
    private String major;

    @DecimalMin(value = "0.0", message = "GPA phải từ 0.0")
    @DecimalMax(value = "4.0", message = "GPA không vượt quá 4.0")
    @Column(name = "gpa")
    private Double gpa;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Quan hệ với các entity khác
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StudentProduct> studentProducts;

    public enum Gender {
        MALE("Nam"),
        FEMALE("Nữ"),
        OTHER("Khác");

        private final String displayName;

        Gender(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
