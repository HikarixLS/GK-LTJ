@echo off
chcp 65001 >nul
color 0E
title 🗄️ SETUP DATABASE - XAMPP MySQL

echo.
echo   ╔═══════════════════════════════════════════════════════════════════════════════╗
echo   ║                        🗄️ SETUP DATABASE XAMPP 🗄️                           ║
echo   ║                            Thiết lập Database MySQL                          ║
echo   ╚═══════════════════════════════════════════════════════════════════════════════╝
echo.

echo 📋 HƯỚNG DẪN SETUP DATABASE XAMPP:
echo.
echo ✅ Bước 1: Khởi động XAMPP Control Panel
echo    📂 Mở XAMPP Control Panel (xampp-control.exe)
echo    🟢 Bấm "Start" cho Apache và MySQL
echo    💡 Đợi đến khi thấy chữ "Running" màu xanh
echo.

echo ✅ Bước 2: Mở phpMyAdmin
echo    🌐 Mở trình duyệt và vào: http://localhost/phpmyadmin
echo    👤 Đăng nhập (thường không cần password)
echo.

echo ✅ Bước 3: Tạo Database
echo    ➕ Click "New" bên trái
echo    📝 Tên database: bookstore
echo    🔤 Collation: utf8mb4_unicode_ci
echo    💾 Click "Create"
echo.

echo ✅ Bước 4: Import dữ liệu
echo    📂 Chọn database "bookstore" vừa tạo
echo    📥 Click tab "Import"
echo    📁 Choose file: src\main\resources\database\bookstore.sql
echo    🚀 Click "Go"
echo.

set /p continue="📍 Đã hoàn thành các bước trên? (y/n): "
if /i "%continue%"=="n" goto MANUAL_GUIDE
if /i "%continue%"=="no" goto MANUAL_GUIDE

echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo 🧪 KIỂM TRA KẾT NỐI DATABASE
echo ══════════════════════════════════════════════════════════════════════════════════
echo.

echo 🔍 Đang kiểm tra kết nối MySQL...

REM Kiểm tra MySQL có chạy không
netstat -an | findstr :3306 >nul
if %errorlevel%==0 (
    echo ✅ MySQL Server đang chạy trên port 3306
) else (
    echo ❌ MySQL Server không chạy!
    echo 💡 Vui lòng khởi động MySQL trong XAMPP Control Panel
    pause
    exit /b 1
)

echo.
echo 📊 Thông tin kết nối:
echo    🏠 Host: localhost
echo    🔌 Port: 3306
echo    📚 Database: bookstore
echo    👤 Username: root
echo    🔒 Password: (trống)
echo.

echo ══════════════════════════════════════════════════════════════════════════════════
echo 🚀 CHẠY ỨNG DỤNG
echo ══════════════════════════════════════════════════════════════════════════════════
echo.

set /p run_app="🎯 Bạn có muốn chạy ứng dụng ngay bây giờ? (y/n): "
if /i "%run_app%"=="y" goto RUN_APP
if /i "%run_app%"=="yes" goto RUN_APP

goto END

:RUN_APP
echo.
echo 🚀 Đang khởi động Bookstore Management...
echo 📱 Giao diện đăng nhập sẽ xuất hiện...
echo.

java -cp "build;lib\mysql-connector-j-8.0.33.jar" BookstoreApp

if %errorlevel%==0 (
    echo.
    echo ✅ Ứng dụng đã hoạt động thành công!
) else (
    echo.
    echo ❌ Lỗi chạy ứng dụng!
    echo 💡 Kiểm tra:
    echo    1. Database đã được import đúng
    echo    2. XAMPP MySQL đang chạy
    echo    3. File mysql-connector-j-8.0.33.jar có trong thư mục lib\
)

goto END

:MANUAL_GUIDE
echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo 📖 HƯỚNG DẪN CHI TIẾT
echo ══════════════════════════════════════════════════════════════════════════════════
echo.

echo 🔧 CÁCH 1: Dùng phpMyAdmin (Khuyến nghị)
echo    1. Mở http://localhost/phpmyadmin
echo    2. New → Tên: bookstore → Collation: utf8mb4_unicode_ci
echo    3. Import → Choose file: src\main\resources\database\bookstore.sql
echo    4. Click Go
echo.

echo 🔧 CÁCH 2: Dùng Command Line
echo    1. Mở Command Prompt
echo    2. Chạy: mysql -u root -p
echo    3. Tạo DB: CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
echo    4. Thoát: exit
echo    5. Import: mysql -u root -p bookstore ^< "src\main\resources\database\bookstore.sql"
echo.

echo 🔧 CÁCH 3: Dùng MySQL Workbench
echo    1. Mở MySQL Workbench
echo    2. Connect to localhost:3306 (root, no password)
echo    3. Create Schema: bookstore
echo    4. Open SQL Script: src\main\resources\database\bookstore.sql
echo    5. Execute
echo.

:END
echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo 🎉 HOÀN TẤT SETUP!
echo ══════════════════════════════════════════════════════════════════════════════════
echo.

echo 🔑 Tài khoản mặc định:
echo    👤 admin / admin123     - Quản trị viên (toàn quyền)
echo    👤 user / user123       - Người dùng (chỉ xem)
echo    👤 manager / manager123 - Nhân viên (quản lý sách)
echo.

echo 📞 Nếu gặp vấn đề:
echo    📧 Kiểm tra XAMPP Control Panel
echo    📧 Xem log trong phpMyAdmin
echo    📧 Chạy lại script này: setup-xampp.bat
echo.

echo 🚀 Để chạy ứng dụng: java -cp "build;lib\mysql-connector-j-8.0.33.jar" BookstoreApp
echo.

pause
exit /b 0
