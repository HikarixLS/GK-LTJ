package model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Lớp thực thể Book đại diện cho thông tin sách trong cửa hàng
 */
public class Book {
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private BigDecimal price;
    private int quantity;
    private String publisher;
    private LocalDate publishDate;
    private String description;
    
    // Constructor mặc định
    public Book() {}
    
    // Constructor với tham số
    public Book(String title, String author, String isbn, String category, 
                BigDecimal price, int quantity, String publisher, 
                LocalDate publishDate, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.description = description;
    }
    
    // Getters và Setters
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public LocalDate getPublishDate() {
        return publishDate;
    }
    
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", publisher='" + publisher + '\'' +
                ", publishDate=" + publishDate +
                ", description='" + description + '\'' +
                '}';
    }
}
