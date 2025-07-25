package com.gdu.productmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    @Column(name = "unit_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType = TransactionType.PURCHASE;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(name = "notes", length = 500)
    private String notes;

    @CreationTimestamp
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        if (quantity != null && unitPrice != null) {
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }

    public enum TransactionType {
        PURCHASE("Mua"),
        SALE("Bán"),
        BORROW("Mượn"),
        RETURN("Trả"),
        EXCHANGE("Trao đổi");

        private final String displayName;

        TransactionType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum TransactionStatus {
        PENDING("Đang chờ"),
        COMPLETED("Hoàn thành"),
        CANCELLED("Đã hủy"),
        REFUNDED("Đã hoàn tiền");

        private final String displayName;

        TransactionStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
