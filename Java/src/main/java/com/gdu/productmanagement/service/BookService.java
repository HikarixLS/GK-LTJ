package com.gdu.productmanagement.service;

import com.gdu.productmanagement.entity.Book;
import com.gdu.productmanagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service xử lý logic nghiệp vụ cho Book (Sách)
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Lấy tất cả sách với phân trang
     */
    @Transactional(readOnly = true)
    public Page<Book> findAllBooks(Pageable pageable) {
        log.debug("Tìm tất cả sách với phân trang: {}", pageable);
        return bookRepository.findAll(pageable);
    }

    /**
     * Tìm sách theo ID
     */
    @Transactional(readOnly = true)
    public Optional<Book> findBookById(Long id) {
        log.debug("Tìm sách theo ID: {}", id);
        return bookRepository.findById(id);
    }

    /**
     * Tìm kiếm sách theo từ khóa
     */
    @Transactional(readOnly = true)
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        log.debug("Tìm kiếm sách với từ khóa: {}", keyword);
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(
            keyword, keyword, keyword, pageable);
    }

    /**
     * Tìm sách theo thể loại
     */
    @Transactional(readOnly = true)
    public Page<Book> findByCategory(Book.BookCategory category, Pageable pageable) {
        log.debug("Tìm sách theo thể loại: {}", category);
        return bookRepository.findByCategory(category, pageable);
    }

    /**
     * Tìm sách theo trạng thái
     */
    @Transactional(readOnly = true)
    public Page<Book> findByStatus(Book.BookStatus status, Pageable pageable) {
        log.debug("Tìm sách theo trạng thái: {}", status);
        return bookRepository.findByStatus(status, pageable);
    }

    /**
     * Tìm sách theo tác giả
     */
    @Transactional(readOnly = true)
    public Page<Book> findByAuthor(String author, Pageable pageable) {
        log.debug("Tìm sách theo tác giả: {}", author);
        return bookRepository.findByAuthorContainingIgnoreCase(author, pageable);
    }

    /**
     * Tìm sách có sẵn (trạng thái AVAILABLE)
     */
    @Transactional(readOnly = true)
    public Page<Book> findAvailableBooks(Pageable pageable) {
        log.debug("Tìm sách có sẵn");
        return bookRepository.findByStatus(Book.BookStatus.AVAILABLE, pageable);
    }

    /**
     * Lưu sách (thêm mới hoặc cập nhật)
     */
    public Book saveBook(Book book) {
        log.info("Lưu sách: {}", book.getTitle());
        
        // Validate business rules
        validateBook(book);
        
        Book savedBook = bookRepository.save(book);
        log.info("Đã lưu sách với ID: {}", savedBook.getId());
        return savedBook;
    }

    /**
     * Xóa sách theo ID
     */
    public void deleteBook(Long id) {
        log.info("Xóa sách với ID: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sách với ID: " + id);
        }
        
        // Kiểm tra xem sách có đang được mượn không
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent() && book.get().getStatus() == Book.BookStatus.BORROWED) {
            throw new RuntimeException("Không thể xóa sách đang được mượn");
        }
        
        bookRepository.deleteById(id);
        log.info("Đã xóa sách với ID: {}", id);
    }

    /**
     * Kiểm tra sách có tồn tại theo ID
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    /**
     * Kiểm tra ISBN có tồn tại
     */
    @Transactional(readOnly = true)
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    /**
     * Kiểm tra ISBN có tồn tại (loại trừ ID hiện tại)
     */
    @Transactional(readOnly = true)
    public boolean existsByIsbnAndIdNot(String isbn, Long id) {
        return bookRepository.existsByIsbnAndIdNot(isbn, id);
    }

    /**
     * Đếm tổng số sách
     */
    @Transactional(readOnly = true)
    public long countAllBooks() {
        return bookRepository.count();
    }

    /**
     * Đếm số sách có sẵn
     */
    @Transactional(readOnly = true)
    public long countAvailableBooks() {
        return bookRepository.countByStatus(Book.BookStatus.AVAILABLE);
    }

    /**
     * Đếm số sách đang được mượn
     */
    @Transactional(readOnly = true)
    public long countBorrowedBooks() {
        return bookRepository.countByStatus(Book.BookStatus.BORROWED);
    }

    /**
     * Lấy danh sách sách mới nhất
     */
    @Transactional(readOnly = true)
    public List<Book> findLatestBooks(int limit) {
        log.debug("Tìm {} sách mới nhất", limit);
        Pageable pageable = Pageable.ofSize(limit);
        return bookRepository.findTop10ByOrderByCreatedAtDesc(pageable);
    }

    /**
     * Lấy danh sách sách phổ biến
     */
    @Transactional(readOnly = true)
    public List<Book> findPopularBooks(int limit) {
        log.debug("Tìm {} sách phổ biến", limit);
        Pageable pageable = Pageable.ofSize(limit);
        return bookRepository.findTop10ByOrderByViewCountDesc(pageable);
    }

    /**
     * Cập nhật trạng thái sách
     */
    public void updateBookStatus(Long bookId, Book.BookStatus status) {
        log.info("Cập nhật trạng thái sách ID: {} -> {}", bookId, status);
        
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setStatus(status);
            bookRepository.save(book);
            log.info("Đã cập nhật trạng thái sách ID: {}", bookId);
        } else {
            throw new RuntimeException("Không tìm thấy sách với ID: " + bookId);
        }
    }

    /**
     * Tăng lượt xem sách
     */
    public void incrementViewCount(Long bookId) {
        log.debug("Tăng lượt xem cho sách ID: {}", bookId);
        bookRepository.incrementViewCount(bookId);
    }

    /**
     * Validate business rules cho sách
     */
    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Thông tin sách không được null");
        }
        
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sách không được để trống");
        }
        
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN không được để trống");
        }
        
        if (book.getPrice() == null || book.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Giá sách phải lớn hơn 0");
        }
        
        if (book.getQuantity() == null || book.getQuantity() < 0) {
            throw new IllegalArgumentException("Số lượng sách không được âm");
        }
    }

    /**
     * Lấy thống kê sách theo thể loại
     */
    @Transactional(readOnly = true)
    public List<Object[]> getBookStatsByCategory() {
        return bookRepository.countBooksByCategory();
    }

    /**
     * Lấy thống kê sách theo trạng thái
     */
    @Transactional(readOnly = true)
    public List<Object[]> getBookStatsByStatus() {
        return bookRepository.countBooksByStatus();
    }
}
