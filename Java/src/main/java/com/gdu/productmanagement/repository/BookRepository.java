package com.gdu.productmanagement.repository;

import com.gdu.productmanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByPublisherContainingIgnoreCase(String publisher);

    List<Book> findByCategory(Book.BookCategory category);

    List<Book> findByFormat(Book.BookFormat format);

    List<Book> findByStatus(Book.BookStatus status);

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
