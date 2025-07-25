@echo off
echo ==================================================
echo    XAMPP MySQL Setup for Product Management
echo ==================================================
echo.

echo [INFO] Thiết lập MySQL database cho dự án...
echo.

REM Kiểm tra XAMPP có chạy không
echo [STEP 1] Kiểm tra XAMPP MySQL service...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe" >NUL
if "%ERRORLEVEL%" == "0" (
    echo [OK] MySQL đang chạy!
) else (
    echo [WARNING] MySQL chưa chạy!
    echo [INFO] Vui lòng:
    echo   1. Mở XAMPP Control Panel
    echo   2. Start Apache và MySQL
    echo   3. Chạy lại script này
    echo.
    pause
    exit /b 1
)

echo.
echo [STEP 2] Tạo database...
echo [INFO] Mở phpMyAdmin để tạo database:

REM Tạo file SQL tạm thời
echo CREATE DATABASE IF NOT EXISTS product_management_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; > temp_create_db.sql
echo USE product_management_db; >> temp_create_db.sql
echo SELECT 'Database created successfully!' as status; >> temp_create_db.sql

echo.
echo [OPTION 1] Tự động tạo database (nếu mysql command có sẵn):
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS product_management_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" 2>NUL
if %errorlevel% == 0 (
    echo [OK] Database đã được tạo tự động!
) else (
    echo [INFO] Không thể tạo tự động. Hãy làm thủ công:
    echo.
    echo [OPTION 2] Tạo database thủ công:
    echo   1. Mở trình duyệt: http://localhost/phpmyadmin
    echo   2. Click "New" bên trái
    echo   3. Tên database: product_management_db
    echo   4. Collation: utf8mb4_unicode_ci
    echo   5. Click "Create"
    echo.
    echo [OPTION 3] Import SQL file:
    echo   1. Mở phpMyAdmin
    echo   2. Click "Import"
    echo   3. Chọn file: database\create_database.sql
    echo   4. Click "Go"
)

echo.
echo [STEP 3] Cấu hình kết nối...
echo [INFO] Thông tin kết nối database:
echo   Host: localhost
echo   Port: 3306
echo   Database: product_management_db
echo   Username: root
echo   Password: (để trống)

echo.
echo [STEP 4] Sẵn sàng chạy ứng dụng!
echo [INFO] Sau khi tạo database, chạy:
echo   start.bat

REM Dọn dẹp
del temp_create_db.sql 2>NUL

echo.
pause
