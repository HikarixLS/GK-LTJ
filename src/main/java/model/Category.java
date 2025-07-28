package model;

/**
 * Lớp thực thể Category đại diện cho danh mục sách
 */
public class Category {
    private int categoryId;
    private String categoryName;
    private String description;
    
    // Constructor mặc định
    public Category() {}
    
    // Constructor với tham số
    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }
    
    // Getters và Setters
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return categoryName; // Để hiển thị trong ComboBox
    }
}
