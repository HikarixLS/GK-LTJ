package com.gdu.productmanagement.repository;

import com.gdu.productmanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Tìm kiếm cơ bản
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    boolean existsByIsbnAndIdNot(String isbn, Long id);

    // Tìm kiếm với phân trang
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    Page<Book> findByPublisherContainingIgnoreCase(String publisher, Pageable pageable);
    Page<Book> findByCategory(Book.BookCategory category, Pageable pageable);
    Page<Book> findByFormat(Book.BookFormat format, Pageable pageable);
    Page<Book> findByStatus(Book.BookStatus status, Pageable pageable);

    // Tìm kiếm không phân trang (để tương thích ngược)
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByPublisherContainingIgnoreCase(String publisher);
    List<Book> findByCategory(Book.BookCategory category);
    List<Book> findByFormat(Book.BookFormat format);
    List<Book> findByStatus(Book.BookStatus status);

    // Tìm kiếm tổng hợp với phân trang
    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(
            @Param("keyword") String keyword1, 
            @Param("keyword") String keyword2, 
            @Param("keyword") String keyword3, 
            Pageable pageable);

    // Đếm số lượng theo trạng thái
    long countByStatus(Book.BookStatus status);

    // Tìm sách mới nhất
    @Query("SELECT b FROM Book b ORDER BY b.createdAt DESC")
    List<Book> findTop10ByOrderByCreatedAtDesc(Pageable pageable);

    // Tìm sách phổ biến (có view count cao nhất)
    @Query("SELECT b FROM Book b ORDER BY b.viewCount DESC")
    List<Book> findTop10ByOrderByViewCountDesc(Pageable pageable);

    // Cập nhật lượt xem
    @Modifying
    @Query("UPDATE Book b SET b.viewCount = b.viewCount + 1 WHERE b.id = :bookId")
    void incrementViewCount(@Param("bookId") Long bookId);

    // Thống kê theo thể loại
    @Query("SELECT b.category, COUNT(b) FROM Book b GROUP BY b.category")
    List<Object[]> countBooksByCategory();

    // Thống kê theo trạng thái
    @Query("SELECT b.status, COUNT(b) FROM Book b GROUP BY b.status")
    List<Object[]> countBooksByStatus();

    List<Book> findByStoreId(Long storeId);

    List<Book> findByLanguage(String language);

    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    List<Book> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT b FROM Book b WHERE b.rating >= :minRating")
    List<Book> findByRatingGreaterThanEqual(@Param("minRating") BigDecimal minRating);

    @Query("SELECT b FROM Book b WHERE b.stockQuantity <= :threshold")
    List<Book> findLowStockBooks(@Param("threshold") Integer threshold);

    @Query("SELECT b FROM Book b WHERE " +
            "(:searchTerm IS NULL OR " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.publisher) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "(:category IS NULL OR b.category = :category) AND " +
            "(:status IS NULL OR b.status = :status)")
    Page<Book> findBySearchCriteria(@Param("searchTerm") String searchTerm,
            @Param("category") Book.BookCategory category,
            @Param("status") Book.BookStatus status,
            Pageable pageable);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.status = 'AVAILABLE'")
    Long countAvailableBooks();

    @Query("SELECT b.category, COUNT(b) FROM Book b WHERE b.status = 'AVAILABLE' GROUP BY b.category")
    List<Object[]> countBooksByCategory();

    @Query("SELECT b.format, COUNT(b) FROM Book b WHERE b.status = 'AVAILABLE' GROUP BY b.format")
    List<Object[]> countBooksByFormat();

    boolean existsByIsbn(String isbn);
}
