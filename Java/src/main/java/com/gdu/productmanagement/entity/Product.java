package com.gdu.productmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    @Column(name = "product_code", unique = true, nullable = false, length = 50)
    private String productCode;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 200, message = "Tên sản phẩm phải từ 2-200 ký tự")
    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(name = "weight", precision = 8, scale = 2)
    private BigDecimal weight;

    @Column(name = "dimensions", length = 100)
    private String dimensions;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @Column(name = "warranty_period")
    private Integer warrantyPeriod; // in months

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Quan hệ với Store
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // Quan hệ với StudentProduct
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StudentProduct> studentProducts;

    public enum ProductCategory {
        ELECTRONICS("Điện tử"),
        CLOTHING("Quần áo"),
        BOOKS("Sách"),
        HOME_GARDEN("Nhà cửa & Vườn"),
        SPORTS("Thể thao"),
        BEAUTY("Làm đẹp"),
        TOYS("Đồ chơi"),
        FOOD("Thực phẩm"),
        OTHER("Khác");

        private final String displayName;

        ProductCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum ProductStatus {
        ACTIVE("Hoạt động"),
        INACTIVE("Không hoạt động"),
        OUT_OF_STOCK("Hết hàng"),
        DISCONTINUED("Ngừng kinh doanh");

        private final String displayName;

        ProductStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
