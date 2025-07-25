# 🚀 Product Management System

## 📋 Mô tả dự án
Hệ thống quản lý sản phẩm toàn diện được xây dựng bằng **Java Spring Boot** với giao diện **Thymeleaf** và cơ sở dữ liệu **MySQL**.

### ✨ Tính năng chính:
- 👥 **Quản lý sinh viên** (CRUD, tìm kiếm, phân trang)
- 📦 **Quản lý sản phẩm** (kho hàng, giá cả, danh mục)
- 🏪 **Quản lý cửa hàng** (địa điểm, thông tin liên hệ)
- 📚 **Quản lý thư viện sách** (ISBN, đánh giá, tác giả)
- 🔐 **Xác thực và phân quyền** (Admin, Manager, User, Student)
- 📊 **Dashboard thống kê** (biểu đồ, báo cáo)

## 🛠️ Công nghệ sử dụng
- **Backend**: Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome
- **Database**: MySQL 8.0 (XAMPP)
- **Build Tool**: Maven 3.9+

## ⚡ Quick Start

### 1. Chuẩn bị môi trường:
- ✅ Java 17+ đã cài đặt
- ✅ Maven 3.6+ đã cài đặt  
- ✅ XAMPP với MySQL đã khởi động

### 2. Tạo database:
```bash
# Mở phpMyAdmin: http://localhost/phpmyadmin
# Tạo database: product_management_db
```

### 3. Chạy ứng dụng:
```bash
# Cách 1: Script tự động (khuyến nghị)
start.bat

# Cách 2: Quick start
quick-start.bat

# Cách 3: Command line
mvn spring-boot:run
```

### 4. Truy cập:
- **Ứng dụng**: http://localhost:8080
- **phpMyAdmin**: http://localhost/phpmyadmin

## 🔐 Tài khoản mặc định

| Role | Username | Password | Quyền hạn |
|------|----------|----------|-----------|
| Admin | admin | admin123 | Full access |
| Manager | manager | manager123 | CRUD operations |
| User | user | user123 | Read + basic ops |
| Student | student | student123 | Limited access |

## 📊 Troubleshooting

### Lỗi kết nối database:
1. Kiểm tra XAMPP MySQL đang chạy
2. Kiểm tra database đã tồn tại
3. Kiểm tra username/password

### Lỗi port 8080:
```properties
# Thay đổi port trong application.properties
server.port=8081
```

### Lỗi build:
```bash
mvn clean install -DskipTests
```

## 📝 License
MIT License - Dự án học tập GDU
