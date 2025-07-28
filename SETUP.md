# Hướng dẫn Cài đặt và Chạy trên Windows

## 🖥️ Yêu cầu Hệ thống Windows

### Phần mềm cần thiết:
- **Windows 10/11** (64-bit khuyến nghị)
- **Java JDK 11 hoặc cao hơn**
- **MySQL Server 8.0+**
- **IDE** (tùy chọn): IntelliJ IDEA, Eclipse, hoặc VS Code

## � Hướng dẫn Cài đặt Chi tiết

### Bước 1: Cài đặt Java JDK
1. Tải Java JDK từ: https://www.oracle.com/java/technologies/downloads/
2. Chọn "Windows x64 Installer"
3. Cài đặt và thiết lập JAVA_HOME:
   ```cmd
   # Mở Command Prompt as Administrator
   setx JAVA_HOME "C:\Program Files\Java\jdk-17"
   setx PATH "%PATH%;%JAVA_HOME%\bin"
   ```
4. Kiểm tra cài đặt:
   ```cmd
   java -version
   javac -version
   ```

### Bước 2: Cài đặt MySQL Server
1. Tải MySQL Installer từ: https://dev.mysql.com/downloads/installer/
2. Chọn "mysql-installer-community-8.x.x.msi"
3. Cài đặt với cấu hình:
   - **Server Type**: Development Computer
   - **Port**: 3306 (mặc định)
   - **Root Password**: Đặt mật khẩu mạnh
   - **Authentication**: Use Strong Password Encryption
4. Cài đặt MySQL Workbench (tùy chọn) để quản lý database

### Bước 3: Tạo Database
1. Mở Command Prompt:
   ```cmd
   # Kết nối MySQL
   mysql -u root -p
   
   # Tạo database
   CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   exit
   ```

2. Import schema:
   ```cmd
   cd /d "H:\Github\LTJ\GK-LTJ"
   mysql -u root -p bookstore < "src\main\resources\database\bookstore.sql"
   ```

### Bước 4: Tải MySQL JDBC Driver
1. Truy cập: https://dev.mysql.com/downloads/connector/j/
2. Chọn "Platform Independent" → ZIP Archive
3. Giải nén và copy file `mysql-connector-java-8.x.x.jar` vào thư mục `lib\`

### Bước 5: Cấu hình Kết nối Database
Chỉnh sửa file `src\main\resources\database.properties`:
```properties
# Cấu hình cho Windows với MySQL
db.url=jdbc:mysql://localhost:3306/bookstore?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=utf8
db.username=root
db.password=YOUR_MYSQL_PASSWORD
```

## 🏗️ Build và Chạy Ứng dụng

### Cách 1: Sử dụng Script (Khuyến nghị)
```cmd
# Mở Command Prompt trong thư mục dự án
cd /d "H:\Github\LTJ\GK-LTJ"

# Build ứng dụng
build.bat

# Chạy ứng dụng
run.bat
```

### Cách 2: Build thủ công
```cmd
# Tạo thư mục build
mkdir build\classes

# Biên dịch
javac -cp "lib\*" -d build\classes -sourcepath src\main\java src\main\java\*.java src\main\java\model\*.java src\main\java\dao\*.java src\main\java\service\*.java src\main\java\gui\*.java

# Copy resources
xcopy "src\main\resources\*" "build\classes\" /E /Y

# Chạy
java -cp "build\classes;lib\*" BookstoreApp
```

### Cách 3: Tạo Executable JAR
```cmd
# Sau khi build thành công
cd build\classes
jar cfm ..\bookstore-management.jar ..\..\MANIFEST.MF *
cd ..\..

# Copy dependencies
xcopy "lib\*" "build\lib\" /Y

# Chạy JAR
java -jar "build\bookstore-management.jar"
```

### 3.2 Tải MySQL JDBC Driver
- Truy cập: https://dev.mysql.com/downloads/connector/j/
- Tải file `mysql-connector-java-8.0.xx.jar`
- Đặt vào thư mục `lib/`

## 📋 Bước 4: Build và Run

### 4.1 Build ứng dụng
```bash
# Windows
build.bat

# Linux/Mac
chmod +x build.sh
./build.sh
```

### 4.2 Chạy ứng dụng
```bash
# Windows
run.bat

# Linux/Mac
chmod +x run.sh
./run.sh
```

### 4.3 Hoặc chạy trực tiếp
```bash
# Compile
javac -cp "lib/*" -d build/classes src/main/java/*.java src/main/java/*/*.java

# Run
java -cp "build/classes:lib/*" BookstoreApp
```

## 🔑 Bước 5: Đăng nhập

Sử dụng một trong các tài khoản sau:

| Username | Password | Vai trò |
|----------|----------|---------|
| admin | admin123 | Admin |
| user | user123 | User |
| manager | manager123 | Employee |

## ❗ Xử lý lỗi thường gặp

### Lỗi kết nối Database
```
Error: "Could not connect to database"
```
**Giải pháp:**
1. Kiểm tra MySQL Server đang chạy
2. Xác minh username/password trong database.properties
3. Kiểm tra port MySQL (mặc định 3306)

### Lỗi ClassNotFoundException
```
Error: "com.mysql.cj.jdbc.Driver not found"
```
**Giải pháp:**
1. Tải MySQL JDBC Driver
2. Đặt vào thư mục lib/
3. Thêm vào classpath khi chạy

### Lỗi Database không tồn tại
```
Error: "Unknown database 'bookstore'"
```
**Giải pháp:**
1. Tạo database: `CREATE DATABASE bookstore;`
2. Import schema từ file bookstore.sql

### Lỗi Permission denied
```
Error: "Access denied for user 'root'"
```
**Giải pháp:**
1. Kiểm tra password MySQL
2. Cấp quyền cho user: `GRANT ALL ON bookstore.* TO 'root'@'localhost';`

## 🎯 Kiểm tra setup thành công

1. **Database**: Truy vấn thành công bảng users, books
2. **Application**: Hiển thị form đăng nhập
3. **Login**: Đăng nhập thành công với tài khoản admin
4. **Features**: Xem được danh sách sách, thêm/sửa/xóa hoạt động

## 📞 Hỗ trợ thêm

Nếu vẫn gặp vấn đề:
1. Kiểm tra log console để biết lỗi cụ thể
2. Xác minh các file JAR trong thư mục lib/
3. Kiểm tra version Java và MySQL tương thích
4. Đảm bảo firewall không chặn kết nối database

---
**Chúc bạn setup thành công! 🚀**
