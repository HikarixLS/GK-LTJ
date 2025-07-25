package com.gdu.productmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ISBN không được để trống")
    @Column(name = "isbn", unique = true, nullable = false, length = 20)
    private String isbn;

    @NotBlank(message = "Tên sách không được để trống")
    @Size(min = 2, max = 300, message = "Tên sách phải từ 2-300 ký tự")
    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @NotBlank(message = "Tác giả không được để trống")
    @Column(name = "author", nullable = false, length = 200)
    private String author;

    @Column(name = "publisher", length = 200)
    private String publisher;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Giá sách không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá sách phải lớn hơn 0")
    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Min(value = 0, message = "Số lượng không được âm")
    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "language", length = 50)
    private String language = "Vietnamese";

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private BookCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "format")
    private BookFormat format = BookFormat.PAPERBACK;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookStatus status = BookStatus.AVAILABLE;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @DecimalMin(value = "0.0", message = "Rating phải từ 0.0")
    @DecimalMax(value = "5.0", message = "Rating không vượt quá 5.0")
    @Column(name = "rating", precision = 2, scale = 1)
    private BigDecimal rating;

    @Min(value = 0, message = "Số lượt đánh giá không được âm")
    @Column(name = "review_count")
    private Integer reviewCount = 0;

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

    public enum BookCategory {
        FICTION("Tiểu thuyết"),
        NON_FICTION("Phi tiểu thuyết"),
        SCIENCE("Khoa học"),
        TECHNOLOGY("Công nghệ"),
        EDUCATION("Giáo dục"),
        BUSINESS("Kinh doanh"),
        HEALTH("Sức khỏe"),
        HISTORY("Lịch sử"),
        BIOGRAPHY("Tiểu sử"),
        CHILDREN("Trẻ em"),
        SELF_HELP("Tự lực"),
        COOKING("Nấu ăn"),
        TRAVEL("Du lịch"),
        RELIGION("Tôn giáo"),
        ART("Nghệ thuật"),
        OTHER("Khác");

        private final String displayName;

        BookCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum BookFormat {
        HARDCOVER("Bìa cứng"),
        PAPERBACK("Bìa mềm"),
        EBOOK("Sách điện tử"),
        AUDIOBOOK("Sách nói");

        private final String displayName;

        BookFormat(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum BookStatus {
        AVAILABLE("Có sẵn"),
        OUT_OF_STOCK("Hết hàng"),
        DISCONTINUED("Ngừng xuất bản"),
        COMING_SOON("Sắp ra mắt");

        private final String displayName;

        BookStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
