package com.bookstore.entity;

public enum RoleName {
    ROLE_USER("Người dùng"),
    ROLE_ADMIN("Quản trị viên");
    
    private final String description;
    
    RoleName(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
