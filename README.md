# Hệ thống Quản lý Cửa hàng Sách - Windows Edition

## 📖 Mô tả dự án

Ứng dụng Java Swing chạy hoàn toàn trên **Windows** để quản lý cửa hàng sách với các chức năng:
- 📚 Quản lý sách (CRUD) với giao diện đẹp
- 👥 Quản lý người dùng và phân quyền 
- 🔍 Tìm kiếm và lọc dữ liệu thông minh
- 🎨 Giao diện GUI Swing chuyên nghiệp
- 🗄️ Kết nối MySQL với validation đầy đủ

## 🖥️ Môi trường Windows

### Yêu cầu hệ thống:
- **Windows 10/11** (64-bit khuyến nghị)
- **Java JDK 11+** (Oracle hoặc OpenJDK)
- **MySQL Server 8.0+**
- **RAM**: Tối thiểu 4GB
- **Disk**: 1GB trống cho ứng dụng và database

### Các script Windows được cung cấp:
- 🔧 **`install-windows.ps1`** - Cài đặt tự động dependencies (PowerShell)
- ✅ **`check-system.bat`** - Kiểm tra hệ thống và dependencies
- ⚙️ **`setup.bat`** - Setup database và build ứng dụng  
- 🏗️ **`build.bat`** - Build dự án và tạo JAR
- 🚀 **`run.bat`** - Chạy ứng dụng

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

**👉 Double-click vào file: `start-here.bat`** 

File này sẽ hướng dẫn bạn từng bước một cách tự động!

### 🤖 Cách 1: Cài đặt tự động (Khuyến nghị cho người mới)

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

# Build
build.bat

# Run
run.bat
```

## 👥 Tài khoản Demo

| Username | Password | Vai trò | Mô tả |
|----------|----------|---------|--------|
| admin | admin123 | Admin | Toàn quyền quản lý |
| user | user123 | User | Chỉ xem thông tin |
| manager | manager123 | Employee | Nhân viên |

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

### 3. Quản lý Người dùng (Admin)
- ✅ Thêm người dùng mới
- ✅ Cập nhật thông tin
- ✅ Vô hiệu hóa tài khoản
- ✅ Phân quyền vai trò

### 4. Giao diện GUI
- ✅ Thiết kế thân thiện, dễ sử dụng
- ✅ Responsive layout
- ✅ Validation dữ liệu
- ✅ Thông báo lỗi rõ ràng

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
- ✅ Hệ thống đăng nhập
- ✅ Phân quyền người dùng
- ✅ Tìm kiếm và lọc dữ liệu
- ✅ Giao diện Swing

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
