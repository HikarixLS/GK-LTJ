package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    // Lấy tất cả sách
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    
    // Lấy sách theo phân trang
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    // Tìm sách theo ID
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    
    // Tìm sách theo ISBN
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    
    // Lưu sách mới hoặc cập nhật
    public Book save(Book book) {
        return bookRepository.save(book);
    }
    
    // Xóa sách theo ID
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
    
    // Kiểm tra sách có tồn tại không
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }
    
    // Tìm sách theo tên
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // Tìm sách theo tác giả
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    // Tìm sách theo thể loại
    public List<Book> findByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }
    
    // Tìm sách theo nhà xuất bản
    public List<Book> findByPublisher(String publisher) {
        return bookRepository.findByPublisherContainingIgnoreCase(publisher);
    }
    
    // Tìm sách theo năm xuất bản
    public List<Book> findByPublicationYear(Integer year) {
        return bookRepository.findByPublicationYear(year);
    }
    
    // Tìm sách có sẵn (số lượng > 0)
    public List<Book> findAvailableBooks() {
        return bookRepository.findByQuantityGreaterThan(0);
    }
    
    // Tìm sách theo nhiều tiêu chí
    public List<Book> searchBooks(String title, String author, String category, String publisher) {
        return bookRepository.findBooksWithCriteria(title, author, category, publisher);
    }
    
    // Lấy tất cả thể loại
    public List<String> getAllCategories() {
        return bookRepository.findAllCategories();
    }
    
    // Lấy tất cả nhà xuất bản
    public List<String> getAllPublishers() {
        return bookRepository.findAllPublishers();
    }
    
    // Thống kê sách theo thể loại
    public List<Object[]> getBookStatsByCategory() {
        return bookRepository.countBooksByCategory();
    }
    
    // Cập nhật số lượng sách
    public Book updateQuantity(Long id, Integer newQuantity) {
        Optional<Book> bookOpt = findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setQuantity(newQuantity);
            return save(book);
        }
        throw new RuntimeException("Không tìm thấy sách với ID: " + id);
    }
    
    // Kiểm tra ISBN đã tồn tại (trừ sách hiện tại)
    public boolean isIsbnExists(String isbn, Long excludeId) {
        Optional<Book> existingBook = findByIsbn(isbn);
        return existingBook.isPresent() && !existingBook.get().getId().equals(excludeId);
    }
    
    // Đếm tổng số sách
    public long countAll() {
        return bookRepository.count();
    }
}
