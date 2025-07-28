package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Tìm sách theo ISBN
    Optional<Book> findByIsbn(String isbn);
    
    // Tìm sách theo tên (không phân biệt hoa thường)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Tìm sách theo tác giả (không phân biệt hoa thường)
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Tìm sách theo thể loại
    List<Book> findByCategoryIgnoreCase(String category);
    
    // Tìm sách theo nhà xuất bản
    List<Book> findByPublisherContainingIgnoreCase(String publisher);
    
    // Tìm sách theo năm xuất bản
    List<Book> findByPublicationYear(Integer year);
    
    // Tìm sách có số lượng lớn hơn 0
    List<Book> findByQuantityGreaterThan(Integer quantity);
    
    // Tìm sách theo nhiều tiêu chí
    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
           "(:category IS NULL OR LOWER(b.category) = LOWER(:category)) AND " +
           "(:publisher IS NULL OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :publisher, '%')))")
    List<Book> findBooksWithCriteria(@Param("title") String title,
                                     @Param("author") String author,
                                     @Param("category") String category,
                                     @Param("publisher") String publisher);
    
    // Lấy tất cả thể loại sách
    @Query("SELECT DISTINCT b.category FROM Book b WHERE b.category IS NOT NULL ORDER BY b.category")
    List<String> findAllCategories();
    
    // Lấy tất cả nhà xuất bản
    @Query("SELECT DISTINCT b.publisher FROM Book b WHERE b.publisher IS NOT NULL ORDER BY b.publisher")
    List<String> findAllPublishers();
    
    // Thống kê số lượng sách theo thể loại
    @Query("SELECT b.category, COUNT(b) FROM Book b WHERE b.category IS NOT NULL GROUP BY b.category")
    List<Object[]> countBooksByCategory();
}
