# 🏪 BOOKSTORE MANAGEMENT SYSTEM - XAMPP VERSION

## 📖 Giới thiệu
Ứng dụng quản lý cửa hàng sách được viết bằng Java Swing với kết nối MySQL XAMPP. Đây là phiên bản chính thức thay thế cho demo version.

## ✨ Tính năng chính
- 🔐 **Hệ thống đăng nhập** với 3 cấp độ quyền
- 📚 **Quản lý sách**: Thêm, sửa, xóa, tìm kiếm
- 👥 **Quản lý người dùng**: Tạo tài khoản, phân quyền
- 🔒 **Bảo mật**: Mã hóa SHA-256, SQL injection protection
- 📊 **Giao diện thân thiện**: Font Segoe UI, màu sắc tối ưu

## 🎯 Phân quyền người dùng
| Quyền | Tài khoản | Mật khẩu | Chức năng |
|-------|-----------|----------|-----------|
| 👤 **USER** | user | user123 | Chỉ xem danh sách sách |
| 👨‍💼 **MANAGER** | manager | manager123 | Quản lý sách + Xem user |
| 👑 **ADMIN** | admin | admin123 | Toàn quyền hệ thống |

## 🚀 Cách chạy nhanh

### Bước 1: Chuẩn bị XAMPP
```bash
# 1. Tải và cài XAMPP
https://www.apachefriends.org/download.html

# 2. Khởi động XAMPP Control Panel
# 3. Start Apache và MySQL
```

### Bước 2: Chạy ứng dụng
```bash
# Chạy script tự động
.\run.bat

# Hoặc chạy script setup XAMPP
.\setup-xampp.bat
```

## 📋 Hướng dẫn chi tiết

### 1️⃣ Cài đặt XAMPP
1. Tải XAMPP từ: https://www.apachefriends.org/download.html
2. Cài đặt với cấu hình mặc định
3. Mở XAMPP Control Panel
4. Start **Apache** và **MySQL**

### 2️⃣ Tạo Database
1. Mở phpMyAdmin: http://localhost/phpmyadmin
2. Tạo database mới tên `bookstore`
3. Import file SQL: `src/main/resources/database/bookstore.sql`

### 3️⃣ Cấu hình Database
File `src/main/resources/database.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh
db.username=root
db.password=
db.driver=com.mysql.cj.jdbc.Driver
```

### 4️⃣ Chạy ứng dụng
```bash
# Tự động download JDBC driver + build + run
.\run.bat

# Hoặc thực hiện thủ công:
javac -cp "lib\mysql-connector-j-8.0.33.jar" -d build BookstoreApp.java
java -cp "build;lib\mysql-connector-j-8.0.33.jar" BookstoreApp
```

## 🛠️ Troubleshooting

### ❌ Lỗi kết nối MySQL
```
Solution:
1. Kiểm tra XAMPP MySQL đã start
2. Kiểm tra port 3306: netstat -an | findstr :3306
3. Thử kết nối: mysql -u root -p
```

### ❌ Database không tồn tại
```
Solution:
1. Mở phpMyAdmin: http://localhost/phpmyadmin
2. Tạo database: CREATE DATABASE bookstore;
3. Import SQL file từ: src/main/resources/database/bookstore.sql
```

### ❌ JDBC Driver thiếu
```
Solution:
1. Script run.bat sẽ tự động tải
2. Hoặc tải thủ công: https://dev.mysql.com/downloads/connector/j/
3. Đặt file jar vào: lib/mysql-connector-j-8.0.33.jar
```

### ❌ Lỗi mã hóa tiếng Việt
```
Solution:
1. Thêm vào URL: &characterEncoding=UTF-8
2. Kiểm tra MySQL charset: SHOW VARIABLES LIKE 'character%';
3. Set UTF-8 cho database: ALTER DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## 📁 Cấu trúc project
```
📦 Bookstore Management
├── 📄 BookstoreApp.java          # Main application (Production)
├── 📄 BookstoreDemo.java         # Demo version (Standalone)
├── 📄 run.bat                    # Auto run script
├── 📄 setup-xampp.bat           # XAMPP setup guide
├── 📄 database.properties       # DB config
├── 📂 lib/
│   └── 📄 mysql-connector-j-8.0.33.jar
├── 📂 src/main/resources/database/
│   └── 📄 bookstore.sql
└── 📂 build/
    └── 📄 BookstoreApp.class
```

## 🔧 Scripts hỗ trợ
- `run.bat` - Chạy ứng dụng tự động (kiểm tra + download + build + run)
- `setup-xampp.bat` - Hướng dẫn setup XAMPP step-by-step
- `build.bat` - Build project manual
- `demo.bat` - Chạy demo version (không cần MySQL)

## 🌐 URLs quan trọng
- **XAMPP Control Panel**: C:\xampp\xampp-control.exe
- **phpMyAdmin**: http://localhost/phpmyadmin
- **MySQL Command**: mysql -u root -p
- **XAMPP Dashboard**: http://localhost/dashboard

## 📞 Liên hệ
- 🧑‍💻 Developer: [Your Name]
- 📧 Email: [Your Email]
- 🔗 GitHub: [Your GitHub]

---
🎯 **Lưu ý**: Đây là phiên bản production kết nối MySQL thực tế, khác với `BookstoreDemo.java` chỉ dùng dữ liệu cứng để demo.
