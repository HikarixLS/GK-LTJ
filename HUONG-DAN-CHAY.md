# 🚀 HƯỚNG DẪN CHẠY DỰ ÁN BOOKSTORE MANAGEMENT

## 📋 Tổng quan nhanh

Dự án **Bookstore Management** là ứng dụng Java Swing quản lý cửa hàng sách, chạy hoàn toàn trên **Windows** với MySQL database.

## 🎯 3 CÁCH CHẠY DỰ ÁN

### 🤖 CÁCH 1: TỰ ĐỘNG (Khuyến nghị cho người mới)

```cmd
# Bước 1: Mở PowerShell as Administrator
# Nhấn Win + X → chọn "Windows PowerShell (Admin)"

# Bước 2: Cho phép chạy script
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser

# Bước 3: Chạy auto installer
.\install-windows.ps1

# Bước 4: Sau khi cài đặt xong, setup database
.\setup.bat

# Bước 5: Chạy ứng dụng
.\run.bat
```

### 🔧 CÁCH 2: NHANH (Nếu đã có Java và MySQL)

```cmd
# Bước 1: Kiểm tra hệ thống
check-system.bat

# Bước 2: Setup và build
setup.bat

# Bước 3: Chạy ứng dụng
run.bat
```

### 📚 CÁCH 3: THỦ CÔNG (Từng bước chi tiết)

#### Bước 1: Chuẩn bị môi trường

```cmd
# Kiểm tra Java
java -version
javac -version

# Nếu chưa có Java, tải từ:
# https://www.oracle.com/java/technologies/downloads/
# Chọn: Windows x64 Installer
```

#### Bước 2: Cài đặt MySQL

```cmd
# Tải MySQL từ: https://dev.mysql.com/downloads/installer/
# Chọn: mysql-installer-community-8.x.x.msi
# Cài đặt với cấu hình:
# - Port: 3306
# - Root password: (ghi nhớ mật khẩu này)
```

#### Bước 3: Thiết lập Database

```cmd
# Mở Command Prompt và chạy:
mysql -u root -p

# Trong MySQL console:
CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit

# Import dữ liệu mẫu:
mysql -u root -p bookstore < "src\main\resources\database\bookstore.sql"
```

#### Bước 4: Cấu hình kết nối

```cmd
# Mở file: src\main\resources\database.properties
# Cập nhật thông tin:
db.url=jdbc:mysql://localhost:3306/bookstore?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=root
db.password=YOUR_MYSQL_PASSWORD
```

#### Bước 5: Tải MySQL JDBC Driver

```cmd
# Tải từ: https://dev.mysql.com/downloads/connector/j/
# Chọn: Platform Independent → ZIP Archive
# Giải nén và copy file mysql-connector-java-8.x.x.jar vào thư mục lib\
```

#### Bước 6: Build và chạy

```cmd
# Build dự án
build.bat

# Chạy ứng dụng
run.bat
```

## 🔑 Tài khoản Demo

Sau khi ứng dụng khởi động, sử dụng các tài khoản sau:

| Username | Password | Vai trò | Quyền hạn |
|----------|----------|---------|-----------|
| **admin** | **admin123** | Quản trị viên | Toàn quyền quản lý |
| **user** | **user123** | Người dùng | Chỉ xem thông tin sách |
| **manager** | **manager123** | Nhân viên | Quản lý sách |

## 🎮 Sử dụng ứng dụng

### 1. Đăng nhập
- Mở ứng dụng → Nhập username/password → Click "Đăng nhập"

### 2. Quản lý sách (Admin/Manager)
- Tab "📚 Quản lý Sách"
- **Thêm sách**: Click "➕ Thêm" → Điền thông tin → "Lưu"
- **Sửa sách**: Double-click vào sách hoặc chọn → "✏️ Sửa"
- **Xóa sách**: Chọn sách → "🗑️ Xóa" → Xác nhận
- **Tìm kiếm**: Nhập từ khóa vào ô "Từ khóa"
- **Lọc**: Chọn danh mục từ dropdown

### 3. Quản lý người dùng (Chỉ Admin)
- Tab "👥 Quản lý Người dùng"
- **Thêm user**: Click "➕ Thêm" → Điền form → "Lưu"
- **Sửa user**: Double-click hoặc chọn → "✏️ Sửa"
- **Vô hiệu hóa**: Chọn user → "🗑️ Xóa"

### 4. Tính năng khác
- **Đổi mật khẩu**: Menu "Hệ thống" → "Đổi mật khẩu"
- **Đăng xuất**: Menu "Hệ thống" → "Đăng xuất"

## ❓ Troubleshooting

### Lỗi thường gặp và cách khắc phục:

#### 1. "java: command not found"
```cmd
# Kiểm tra Java đã cài đặt:
where java

# Nếu không có, cài đặt Java JDK
# Thêm Java vào PATH:
setx PATH "%PATH%;C:\Program Files\Java\jdk-17\bin"
```

#### 2. "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
```cmd
# Kiểm tra JDBC Driver:
dir lib\mysql-connector-*.jar

# Nếu không có, tải từ:
# https://dev.mysql.com/downloads/connector/j/
```

#### 3. "Access denied for user 'root'@'localhost'"
```cmd
# Kiểm tra mật khẩu MySQL
# Cập nhật file: src\main\resources\database.properties
# Hoặc reset password MySQL
```

#### 4. "Can't connect to MySQL server"
```cmd
# Kiểm tra MySQL Service:
net start mysql80

# Hoặc qua Services.msc:
services.msc → MySQL80 → Start
```

#### 5. "Database 'bookstore' doesn't exist"
```cmd
# Tạo lại database:
mysql -u root -p
CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
exit

# Import lại dữ liệu:
mysql -u root -p bookstore < "src\main\resources\database\bookstore.sql"
```

## 🔧 Scripts hỗ trợ

| Script | Mô tả | Khi nào dùng |
|--------|--------|-------------|
| **check-system.bat** | Kiểm tra hệ thống | Khi gặp lỗi không rõ nguyên nhân |
| **install-windows.ps1** | Cài đặt tự động | Lần đầu setup môi trường |
| **setup.bat** | Setup database và build | Sau khi cài dependencies |
| **build.bat** | Build project | Khi thay đổi source code |
| **run.bat** | Chạy ứng dụng | Khởi động ứng dụng |

## 📁 Cấu trúc thư mục quan trọng

```
H:\Github\LTJ\GK-LTJ\
├── src\main\java\              # Source code
├── src\main\resources\         # Config và SQL
├── lib\                        # MySQL JDBC Driver
├── build\                      # Build output
├── *.bat                       # Windows scripts
├── install-windows.ps1         # PowerShell installer
└── README.md                   # Tài liệu
```

## 📞 Hỗ trợ thêm

### Nếu vẫn gặp vấn đề:

1. **Chạy system check**: `check-system.bat`
2. **Xem log chi tiết**: Chạy từ Command Prompt để thấy error message
3. **Đọc file README.md** và **SETUP.md**
4. **Kiểm tra**: Java version, MySQL service, file paths

### Môi trường được test:
- ✅ **Windows 10** (64-bit)
- ✅ **Windows 11** (64-bit)
- ✅ **Java JDK 11, 17, 21**
- ✅ **MySQL 8.0**

---

## 🎉 Chúc mừng!

Nếu làm theo hướng dẫn, bạn sẽ có một ứng dụng quản lý cửa hàng sách hoàn chỉnh chạy trên Windows!

**Happy coding! 📚💻**
