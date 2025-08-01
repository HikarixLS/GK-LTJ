# Hệ thống Quản lý Cửa hàng Sách - Windows Edition

## 📖 Mô tả dự án

Ứng dụng Java Swing chạy hoàn toàn trên **Windows** để quản lý cửa hàng sách với các chức năng:
- 📚 Quản lý sách (CRUD) với giao diện sạch sẽ
- 👥 Quản lý người dùng và phân quyền EMPLOYEE
- 🔍 Tìm kiếm và lọc dữ liệu thông minh
- 🎨 Giao diện GUI Swing đơn giản, không rườm rà
- 🗄️ Kết nối MySQL với XAMPP
- 🚀 Auto-run một lệnh duy nhất

## 🖥️ Môi trường Windows

### Yêu cầu hệ thống:
- **Windows 10/11** (64-bit khuyến nghị)
- **Java JDK 11+** (Oracle hoặc OpenJDK)
- **MySQL Server 8.0+**
- **RAM**: Tối thiểu 4GB
- **Disk**: 1GB trống cho ứng dụng và database

### Các script Windows được cung cấp:
- � **`main.cmd`** - Chạy ứng dụng trực tiếp (auto-build và start)

## 🚀 Công nghệ sử dụng

- **Ngôn ngữ**: Java 11+
- **GUI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **JDBC Driver**: MySQL Connector/J
- **Architecture**: MVC Pattern

## 📁 Cấu trúc dự án

```
src/
├── main/
│   ├── java/
│   │   ├── model/          # Các lớp thực thể
│   │   │   ├── Book.java
│   │   │   ├── User.java
│   │   │   └── Category.java
│   │   ├── dao/            # Data Access Objects
│   │   │   ├── BookDAO.java
│   │   │   ├── UserDAO.java
│   │   │   └── DatabaseConnection.java
│   │   ├── service/        # Business Logic
│   │   │   ├── BookService.java
│   │   │   └── UserService.java
│   │   ├── gui/            # Giao diện người dùng
│   │   │   ├── LoginFrame.java
│   │   │   ├── MainFrame.java
│   │   │   ├── BookManagementPanel.java
│   │   │   ├── BookDialog.java
│   │   │   ├── UserManagementPanel.java
│   │   │   └── UserDialog.java
│   │   └── BookstoreApp.java
│   └── resources/
│       ├── database/
│       │   └── bookstore.sql
│       └── database.properties
└── lib/
    └── mysql-connector-java-8.0.33.jar
```

## ⚙️ Hướng dẫn chạy dự án Windows

### 🎯 CÁCH NHANH NHẤT - Chạy ngay!

**👉 Double-click vào file: `main.cmd`** 

File này sẽ tự động:
- ✅ Kiểm tra Java và dependencies
- ✅ Build dự án 
- ✅ Khởi chạy ứng dụng
- ✅ Không cần menu - chạy trực tiếp!

### 📋 Yêu cầu trước khi chạy:
1. **Java JDK 11+** đã cài đặt
2. **XAMPP MySQL** đang chạy trên port 3306  
3. **Database bookstore** đã được tạo và import

### 🛠️ Setup Database lần đầu:
```cmd
# Tạo database trong XAMPP phpMyAdmin
# 1. Mở http://localhost/phpmyadmin
# 2. Tạo database tên: bookstore
# 3. Import file: src\main\resources\database\bookstore.sql
```

### 📋 Setup thủ công (nếu cần):

1. **Mở PowerShell as Administrator** (Nhấn Win + X → chọn "Windows PowerShell (Admin)")
2. **Cho phép chạy script:**
   ```powershell
   Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
   ```
3. **Chạy script cài đặt tự động:**
   ```powershell
   .\install-windows.ps1
   ```
4. **Setup database:**
   ```cmd
   setup.bat
   ```
5. **Chạy ứng dụng:**
   ```cmd
   run.bat
   ```

### ⚡ Cách 2: Chạy nhanh (nếu đã có Java và MySQL)

```cmd
# Kiểm tra hệ thống
check-system.bat

# Setup và build
setup.bat

# Chạy ứng dụng
run.bat
```

### � Cách 3: Hướng dẫn chi tiết từng bước

👉 **Xem file: `HUONG-DAN-CHAY.md`** để có hướng dẫn siêu chi tiết!

### 📋 Cách 4: Setup thủ công
```cmd
# Kiểm tra Java
java -version
javac -version

# Tạo database
mysql -u root -p
CREATE DATABASE bookstore;
exit

# Import data
mysql -u root -p bookstore < "src\main\resources\database\bookstore.sql"

# Chạy ứng dụng
main.cmd
```

## 👥 Tài khoản Demo

| Username | Password | Vai trò | Mô tả |
|----------|----------|---------|--------|
| admin | admin123 | Admin | Toàn quyền quản lý |
| user | user123 | User | Chỉ xem thông tin |
| manager | manager123 | Employee | Quản lý sách (thêm/sửa/xóa) |

## 🔧 Tính năng chính

### 1. Đăng nhập & Phân quyền
- Xác thực người dùng
- 3 loại vai trò: Admin, Employee, User
- Phân quyền truy cập các chức năng

### 2. Quản lý Sách
- ✅ Thêm sách mới
- ✅ Cập nhật thông tin sách  
- ✅ Xóa sách
- ✅ Tìm kiếm theo tên, tác giả, ISBN
- ✅ Lọc theo danh mục
- ✅ Hiển thị thống kê
- ✅ EMPLOYEE có đầy đủ quyền quản lý sách

### 3. Quản lý Người dùng (Admin)
- ✅ Thêm người dùng mới
- ✅ Cập nhật thông tin
- ✅ Vô hiệu hóa tài khoản
- ✅ Phân quyền vai trò

### 4. Giao diện GUI
- ✅ Thiết kế sạch sẽ, không icon rườm rà
- ✅ Responsive layout
- ✅ Validation dữ liệu
- ✅ Thông báo lỗi rõ ràng
- ✅ Authentication đơn giản với plain text

## 📊 Database Schema

### Bảng Users
```sql
user_id (PK) | username | password | full_name | email | phone | role | is_active | created_at | last_login
```

### Bảng Books
```sql
book_id (PK) | title | author | isbn | category | price | quantity | publisher | publish_date | description
```

### Bảng Categories
```sql
category_id (PK) | category_name | description | created_at
```

## 🛠️ Phát triển

### Thêm tính năng mới
1. Tạo model class trong package `model`
2. Tạo DAO class trong package `dao`
3. Tạo service class trong package `service`
4. Tạo GUI component trong package `gui`
5. Cập nhật database schema nếu cần

### Testing
- Unit test cho các service class
- Integration test cho DAO layer
- UI test thủ công

## 📝 Changelog

### Version 1.0.0
- ✅ Quản lý sách cơ bản (CRUD)
- ✅ Hệ thống đăng nhập (plain text authentication)
- ✅ Phân quyền người dùng (EMPLOYEE có quyền quản lý sách)
- ✅ Tìm kiếm và lọc dữ liệu
- ✅ Giao diện Swing sạch sẽ (loại bỏ emoji/icon)
- ✅ Auto-run với main.cmd (không cần menu)

### Các tính năng sắp có
- 📋 Quản lý đơn hàng
- 📊 Báo cáo thống kê
- 🎨 Themes và customization
- 💾 Export/Import dữ liệu
- 🔍 Tìm kiếm nâng cao

## 👨‍💻 Tác giả

**Đồ án Lập trình Java**
- Môn học: Lập trình Java
- Năm học: 2025

## 📄 Giấy phép

Dự án này được phát triển cho mục đích học tập.

## 🤝 Đóng góp

Mọi đóng góp và cải thiện đều được hoan nghênh!

1. Fork dự án
2. Tạo feature branch
3. Commit thay đổi
4. Push lên branch
5. Tạo Pull Request

## 📞 Hỗ trợ

Nếu có vấn đề khi chạy ứng dụng:
1. Kiểm tra kết nối MySQL
2. Xác minh thông tin trong database.properties
3. Đảm bảo MySQL JDBC Driver trong classpath
4. Kiểm tra log console để biết chi tiết lỗi

---
*Chúc bạn sử dụng ứng dụng thành công! 📚*
