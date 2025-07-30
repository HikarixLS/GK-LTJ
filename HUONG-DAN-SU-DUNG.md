# Bookstore Management System

## Hướng dẫn sử dụng

### Yêu cầu hệ thống:
- Windows 10/11
- Java JDK 11 hoặc cao hơn
- XAMPP (cho MySQL database)

### Cách chạy:
1. Mở PowerShell hoặc Command Prompt
2. Điều hướng đến thư mục project
3. Chạy lệnh: `.\main.cmd`

### Menu chức năng:
1. **Check System** - Kiểm tra Java và XAMPP
2. **Setup Database** - Tự động tạo database MySQL
3. **Build and Run** - Compile và chạy ứng dụng
4. **Exit** - Thoát chương trình

### Lưu ý:
- Đảm bảo XAMPP đã được cài đặt trước khi setup database
- File sẽ tự động compile code vào thư mục `bin/`
- Database sẽ được tạo với tên `bookstore_db`

### Cấu trúc project:
```
src/main/java/          - Source code Java
lib/                    - MySQL connector JAR
bin/                    - Compiled classes (tự động tạo)
main.cmd               - File chạy chính (duy nhất cần thiết)
```

## Troubleshooting:
- Nếu gặp lỗi compilation: Kiểm tra Java version
- Nếu không connect được database: Khởi động lại MySQL trong XAMPP
- Nếu không chạy được main.cmd: Thử chạy với quyền Administrator
