@echo off
chcp 65001 >nul
color 0A
title 🗄️ CREATE DATABASE

echo.
echo ╔═══════════════════════════════════════════════════════════════════════════════╗
echo ║                          🗄️ TẠO DATABASE BOOKSTORE 🗄️                       ║
echo ║                              Sử dụng MySQL XAMPP                             ║
echo ╚═══════════════════════════════════════════════════════════════════════════════╝
echo.

echo [1/3] Kiểm tra MySQL XAMPP...
if not exist "H:\xampp\mysql\bin\mysql.exe" (
    echo ❌ XAMPP MySQL không tìm thấy tại H:\xampp\mysql\bin\mysql.exe
    echo 💡 Vui lòng kiểm tra đường dẫn XAMPP
    pause
    exit /b 1
)
echo ✅ Tìm thấy MySQL XAMPP

echo.
echo [2/3] Tạo database...
echo CREATE DATABASE IF NOT EXISTS bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; > temp_create_db.sql
echo SHOW DATABASES; >> temp_create_db.sql

"H:\xampp\mysql\bin\mysql.exe" -u root -h localhost -P 3306 < temp_create_db.sql
if %errorlevel% equ 0 (
    echo ✅ Database 'bookstore' đã được tạo thành công!
) else (
    echo ❌ Lỗi tạo database
    del temp_create_db.sql 2>nul
    pause
    exit /b 1
)

echo.
echo [3/3] Import dữ liệu...
if exist "src\main\resources\database\bookstore.sql" (
    echo 📥 Đang import file SQL...
    "H:\xampp\mysql\bin\mysql.exe" -u root -h localhost -P 3306 bookstore < "src\main\resources\database\bookstore.sql"
    if %errorlevel% equ 0 (
        echo ✅ Import dữ liệu thành công!
    ) else (
        echo ❌ Lỗi import dữ liệu
        echo 💡 File SQL có thể bị lỗi hoặc không tồn tại
    )
) else (
    echo ⚠️  File SQL không tìm thấy: src\main\resources\database\bookstore.sql
    echo 💡 Bạn có thể tạo tables thủ công trong phpMyAdmin
)

REM Clean up
del temp_create_db.sql 2>nul

echo.
echo ╔═══════════════════════════════════════════════════════════════════════════════╗
echo ║                              ✅ HOÀN TẤT SETUP ✅                            ║
echo ╚═══════════════════════════════════════════════════════════════════════════════╝
echo.
echo 🎯 Database 'bookstore' đã sẵn sàng!
echo 🚀 Bây giờ bạn có thể chạy: .\run.bat
echo 🌐 Hoặc kiểm tra trong phpMyAdmin: http://localhost/phpmyadmin
echo.
pause
