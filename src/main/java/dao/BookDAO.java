package dao;

import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho Book - xử lý các thao tác CRUD với cơ sở dữ liệu
 */
public class BookDAO {
    private Connection connection;
    
    public BookDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    /**
     * Thêm sách mới
     */
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, category, price, quantity, publisher, publish_date, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setBigDecimal(5, book.getPrice());
            stmt.setInt(6, book.getQuantity());
            stmt.setString(7, book.getPublisher());
            stmt.setDate(8, Date.valueOf(book.getPublishDate()));
            stmt.setString(9, book.getDescription());
            
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sách: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Cập nhật thông tin sách
     */
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title=?, author=?, isbn=?, category=?, price=?, quantity=?, publisher=?, publish_date=?, description=? WHERE book_id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setBigDecimal(5, book.getPrice());
            stmt.setInt(6, book.getQuantity());
            stmt.setString(7, book.getPublisher());
            stmt.setDate(8, Date.valueOf(book.getPublishDate()));
            stmt.setString(9, book.getDescription());
            stmt.setInt(10, book.getBookId());
            
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sách: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Xóa sách theo ID
     */
    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sách: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Lấy tất cả sách
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setPrice(rs.getBigDecimal("price"));
                book.setQuantity(rs.getInt("quantity"));
                book.setPublisher(rs.getString("publisher"));
                
                Date publishDate = rs.getDate("publish_date");
                if (publishDate != null) {
                    book.setPublishDate(publishDate.toLocalDate());
                }
                
                book.setDescription(rs.getString("description"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sách: " + e.getMessage());
            e.printStackTrace();
        }
        
        return books;
    }
    
    /**
     * Tìm sách theo ID
     */
    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setPrice(rs.getBigDecimal("price"));
                book.setQuantity(rs.getInt("quantity"));
                book.setPublisher(rs.getString("publisher"));
                
                Date publishDate = rs.getDate("publish_date");
                if (publishDate != null) {
                    book.setPublishDate(publishDate.toLocalDate());
                }
                
                book.setDescription(rs.getString("description"));
                return book;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sách: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Tìm kiếm sách theo từ khóa
     */
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ? ORDER BY title";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchTerm = "%" + keyword + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setPrice(rs.getBigDecimal("price"));
                book.setQuantity(rs.getInt("quantity"));
                book.setPublisher(rs.getString("publisher"));
                
                Date publishDate = rs.getDate("publish_date");
                if (publishDate != null) {
                    book.setPublishDate(publishDate.toLocalDate());
                }
                
                book.setDescription(rs.getString("description"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm sách: " + e.getMessage());
            e.printStackTrace();
        }
        
        return books;
    }
    
    /**
     * Lấy sách theo danh mục
     */
    public List<Book> getBooksByCategory(String category) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE category=? ORDER BY title";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setPrice(rs.getBigDecimal("price"));
                book.setQuantity(rs.getInt("quantity"));
                book.setPublisher(rs.getString("publisher"));
                
                Date publishDate = rs.getDate("publish_date");
                if (publishDate != null) {
                    book.setPublishDate(publishDate.toLocalDate());
                }
                
                book.setDescription(rs.getString("description"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy sách theo danh mục: " + e.getMessage());
            e.printStackTrace();
        }
        
        return books;
    }
}
