-- Script tạo database cho Product Management System
-- Chạy script này trong phpMyAdmin hoặc MySQL Workbench

-- Tạo database
CREATE DATABASE IF NOT EXISTS product_management_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Sử dụng database
USE product_management_db;

-- Hiển thị thông báo
SELECT 'Database product_management_db đã được tạo thành công!' as message;

-- Kiểm tra kết nối
SHOW TABLES;
