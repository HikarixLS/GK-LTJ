package service;

import dao.BookDAO;
import model.Book;
import java.util.List;

/**
 * Lớp Service cho Book - xử lý logic nghiệp vụ
 */
public class BookService {
    private BookDAO bookDAO;
    
    public BookService() {
        this.bookDAO = new BookDAO();
    }
    
    /**
     * Thêm sách mới với validation
     */
    public boolean addBook(Book book) {
        if (validateBook(book)) {
            return bookDAO.addBook(book);
        }
        return false;
    }
    
    /**
     * Cập nhật sách với validation
     */
    public boolean updateBook(Book book) {
        if (validateBook(book)) {
            return bookDAO.updateBook(book);
        }
        return false;
    }
    
    /**
     * Xóa sách
     */
    public boolean deleteBook(int bookId) {
        if (bookId <= 0) {
            System.err.println("ID sách không hợp lệ");
            return false;
        }
        return bookDAO.deleteBook(bookId);
    }
    
    /**
     * Lấy tất cả sách
     */
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
    
    /**
     * Tìm sách theo ID
     */
    public Book getBookById(int bookId) {
        if (bookId <= 0) {
            System.err.println("ID sách không hợp lệ");
            return null;
        }
        return bookDAO.getBookById(bookId);
    }
    
    /**
     * Tìm kiếm sách theo từ khóa
     */
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBooks();
        }
        return bookDAO.searchBooks(keyword.trim());
    }
    
    /**
     * Lấy sách theo danh mục
     */
    public List<Book> getBooksByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return getAllBooks();
        }
        return bookDAO.getBooksByCategory(category);
    }
    
    /**
     * Kiểm tra tính hợp lệ của thông tin sách
     */
    private boolean validateBook(Book book) {
        if (book == null) {
            System.err.println("Thông tin sách không được để trống");
            return false;
        }
        
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            System.err.println("Tên sách không được để trống");
            return false;
        }
        
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            System.err.println("Tác giả không được để trống");
            return false;
        }
        
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            System.err.println("ISBN không được để trống");
            return false;
        }
        
        if (book.getPrice() == null || book.getPrice().doubleValue() < 0) {
            System.err.println("Giá sách phải lớn hơn hoặc bằng 0");
            return false;
        }
        
        if (book.getQuantity() < 0) {
            System.err.println("Số lượng sách phải lớn hơn hoặc bằng 0");
            return false;
        }
        
        // Kiểm tra độ dài ISBN (thường là 10 hoặc 13 ký tự)
        String isbn = book.getIsbn().replaceAll("-", "");
        if (isbn.length() != 10 && isbn.length() != 13) {
            System.err.println("ISBN phải có 10 hoặc 13 ký tự");
            return false;
        }
        
        return true;
    }
    
    /**
     * Kiểm tra sách có tồn tại không
     */
    public boolean isBookExists(int bookId) {
        return getBookById(bookId) != null;
    }
    
    /**
     * Cập nhật số lượng sách
     */
    public boolean updateBookQuantity(int bookId, int newQuantity) {
        if (newQuantity < 0) {
            System.err.println("Số lượng không thể âm");
            return false;
        }
        
        Book book = getBookById(bookId);
        if (book != null) {
            book.setQuantity(newQuantity);
            return updateBook(book);
        }
        return false;
    }
    
    /**
     * Lấy tổng số sách trong kho
     */
    public int getTotalBooksInStock() {
        List<Book> books = getAllBooks();
        return books.stream().mapToInt(Book::getQuantity).sum();
    }
    
    /**
     * Lấy danh sách sách sắp hết hàng
     */
    public List<Book> getLowStockBooks(int threshold) {
        List<Book> allBooks = getAllBooks();
        return allBooks.stream()
                .filter(book -> book.getQuantity() <= threshold)
                .toList();
    }
}
