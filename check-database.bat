@echo off
chcp 65001 >nul
color 0A
title 🔍 KIỂM TRA DATABASE

echo.
echo   ╔═══════════════════════════════════════════════════════════════════════════════╗
echo   ║                     🔍 KIỂM TRA DATABASE BOOKSTORE 🔍                        ║
echo   ║                        Chẩn đoán tự động các vấn đề                          ║
echo   ╚═══════════════════════════════════════════════════════════════════════════════╝
echo.

REM Kiểm tra MySQL Server
echo [1/4] Kiểm tra MySQL Server...
netstat -an | findstr :3306 >nul
if %errorlevel% neq 0 (
    echo ❌ MySQL Server không chạy!
    echo 💡 Hướng dẫn khắc phục:
    echo    1. Mở XAMPP Control Panel
    echo    2. Bấm Start cho MySQL
    echo    3. Đợi đến khi thấy "Running" màu xanh
    echo.
    pause
    exit /b 1
) else (
    echo ✅ MySQL Server đang chạy trên port 3306
)

REM Kiểm tra MySQL Client
echo.
echo [2/4] Kiểm tra MySQL Client...
where mysql >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  MySQL client không tìm thấy trong PATH
    echo 💡 Tìm XAMPP MySQL...
    
    REM Kiểm tra các đường dẫn XAMPP phổ biến
    set MYSQL_PATH=
    if exist "H:\xampp\mysql\bin\mysql.exe" (
        set MYSQL_PATH=H:\xampp\mysql\bin\mysql.exe
        echo ✅ Tìm thấy XAMPP MySQL: H:\xampp\mysql\bin\mysql.exe
    ) else if exist "C:\xampp\mysql\bin\mysql.exe" (
        set MYSQL_PATH=C:\xampp\mysql\bin\mysql.exe
        echo ✅ Tìm thấy XAMPP MySQL: C:\xampp\mysql\bin\mysql.exe
    ) else if exist "D:\xampp\mysql\bin\mysql.exe" (
        set MYSQL_PATH=D:\xampp\mysql\bin\mysql.exe
        echo ✅ Tìm thấy XAMPP MySQL: D:\xampp\mysql\bin\mysql.exe
    ) else (
        echo ❌ Không tìm thấy XAMPP MySQL
        echo 💡 Vui lòng kiểm tra XAMPP đã được cài đặt
        pause
        exit /b 1
    )
) else (
    echo ✅ MySQL client có sẵn
    set MYSQL_PATH=mysql
)

REM Kiểm tra kết nối MySQL
echo.
echo [3/4] Kiểm tra kết nối MySQL...
echo 💡 Đang thử kết nối với user root (no password)...

REM Tạo file test connection
echo SHOW DATABASES; > temp_test.sql
echo EXIT >> temp_test.sql

REM Test kết nối
"%MYSQL_PATH%" -u root -h localhost -P 3306 < temp_test.sql >temp_output.txt 2>&1

if %errorlevel% equ 0 (
    echo ✅ Kết nối MySQL thành công
    findstr /i "bookstore" temp_output.txt >nul
    if %errorlevel% equ 0 (
        echo ✅ Database 'bookstore' đã tồn tại
        set DB_EXISTS=true
    ) else (
        echo ❌ Database 'bookstore' KHÔNG tồn tại
        set DB_EXISTS=false
    )
) else (
    echo ❌ Không thể kết nối MySQL
    echo 📄 Chi tiết lỗi:
    type temp_output.txt
    echo.
    echo 💡 Có thể nguyên nhân:
    echo    1. MySQL chưa khởi động
    echo    2. Password root đã được thiết lập
    echo    3. Port 3306 bị chặn
    del temp_test.sql temp_output.txt 2>nul
    pause
    exit /b 1
)

REM Kiểm tra database bookstore
echo.
echo [4/4] Kiểm tra database bookstore chi tiết...

if "%DB_EXISTS%"=="true" (
    echo ✅ Database bookstore tồn tại, kiểm tra tables...
    
    REM Kiểm tra tables
    echo USE bookstore; SHOW TABLES; > temp_test.sql
    "%MYSQL_PATH%" -u root -h localhost -P 3306 < temp_test.sql >temp_output.txt 2>&1
    
    findstr /i "books\|users\|categories" temp_output.txt >nul
    if %errorlevel% equ 0 (
        echo ✅ Tables đã được tạo thành công
        echo 📊 Danh sách tables:
        type temp_output.txt | findstr /v /i "database\|tables_in"
    ) else (
        echo ❌ Tables chưa được tạo
        echo 💡 Cần import file SQL: src\main\resources\database\bookstore.sql
    )
) else (
    echo ❌ Cần tạo database bookstore
    echo.
    echo 🛠️  TẠO DATABASE TỰ ĐỘNG
    echo ════════════════════════
    set /p auto_create="Bạn có muốn tự động tạo database? (y/n): "
    if /i "!auto_create!"=="y" (
        echo.
        echo 🔨 Đang tạo database...
        echo CREATE DATABASE IF NOT EXISTS bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; > temp_create.sql
        echo USE bookstore; >> temp_create.sql
        echo SELECT 'Database bookstore created successfully' as Result; >> temp_create.sql
        
        "%MYSQL_PATH%" -u root -h localhost -P 3306 < temp_create.sql
        if %errorlevel% equ 0 (
            echo ✅ Database đã được tạo thành công!
            echo.
            echo 📥 Bây giờ cần import dữ liệu:
            echo    1. Mở phpMyAdmin: http://localhost/phpmyadmin
            echo    2. Chọn database 'bookstore'
            echo    3. Click tab 'Import'
            echo    4. Chọn file: src\main\resources\database\bookstore.sql
            echo    5. Click 'Go'
            del temp_create.sql
        ) else (
            echo ❌ Lỗi tạo database
            del temp_create.sql
        )
    )
)

REM Clean up
del temp_test.sql temp_output.txt 2>nul

echo.
echo ════════════════════════════════════════════════════════════════════════════════════
echo 🎯 TÓM TẮT KIỂM TRA
echo ════════════════════════════════════════════════════════════════════════════════════
if "%DB_EXISTS%"=="true" (
    echo ✅ MySQL Server: Hoạt động
    echo ✅ Database: bookstore tồn tại
    echo 🎉 SẴN SÀNG CHẠY ỨNG DỤNG!
    echo.
    set /p run_app="Bạn có muốn chạy ứng dụng ngay? (y/n): "
    if /i "!run_app!"=="y" (
        echo 🚀 Đang khởi động...
        java -cp "build;lib\mysql-connector-j-8.0.33.jar" BookstoreApp
    )
) else (
    echo ❌ Cần khắc phục các vấn đề trên trước khi chạy ứng dụng
    echo.
    echo 💡 HƯỚNG DẪN NHANH:
    echo    1. Mở XAMPP Control Panel → Start MySQL
    echo    2. Mở http://localhost/phpmyadmin
    echo    3. Tạo database 'bookstore'
    echo    4. Import file: src\main\resources\database\bookstore.sql
    echo    5. Chạy lại: check-database.bat
)

echo.
pause
