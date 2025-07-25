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
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã cửa hàng không được để trống")
    @Column(name = "store_code", unique = true, nullable = false, length = 20)
    private String storeCode;

    @NotBlank(message = "Tên cửa hàng không được để trống")
    @Size(min = 2, max = 200, message = "Tên cửa hàng phải từ 2-200 ký tự")
    @Column(name = "store_name", nullable = false, length = 200)
    private String storeName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(name = "address", nullable = false, length = 300)
    private String address;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải từ 10-11 chữ số")
    @Column(name = "phone", length = 11)
    private String phone;

    @Email(message = "Email không hợp lệ")
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "website", length = 200)
    private String website;

    @Column(name = "manager_name", length = 100)
    private String managerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StoreStatus status = StoreStatus.ACTIVE;

    @Column(name = "opening_hours", length = 100)
    private String openingHours;

    @Column(name = "license_number", length = 50)
    private String licenseNumber;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Quan hệ với Product
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    // Quan hệ với Book
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> books;

    public enum StoreStatus {
        ACTIVE("Hoạt động"),
        INACTIVE("Không hoạt động"),
        SUSPENDED("Tạm ngưng"),
        CLOSED("Đóng cửa");

        private final String displayName;

        StoreStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
