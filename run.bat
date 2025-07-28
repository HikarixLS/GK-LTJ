@echo off
chcp 65001 >nul
color 0A
title 🚀 RUN BOOKSTORE MANAGEMENT

echo.
echo   ██████╗ ██╗   ██╗███╗   ██╗    ██████╗  ██████╗  ██████╗ ██╗  ██╗███████╗████████╗ ██████╗ ██████╗ ███████╗
echo   ██╔══██╗██║   ██║████╗  ██║    ██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝
echo   ██████╔╝██║   ██║██╔██╗ ██║    ██████╔╝██║   ██║██║   ██║█████╔╝ ███████╗   ██║   ██║   ██║██████╔╝█████╗  
echo   ██╔══██╗██║   ██║██║╚██╗██║    ██╔══██╗██║   ██║██║   ██║██╔═██╗ ╚════██║   ██║   ██║   ██║██╔══██╗██╔══╝  
echo   ██║  ██║╚██████╔╝██║ ╚████║    ██████╔╝╚██████╔╝╚██████╔╝██║  ██╗███████║   ██║   ╚██████╔╝██║  ██║███████╗
echo   ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝    ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝
echo.
echo                           🏪 BOOKSTORE MANAGEMENT SYSTEM 🏪
echo                              Kết nối với MySQL XAMPP
echo.
echo ========================================================================
echo [1/4] Kiểm tra môi trường Java...
REM Kiểm tra Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java không tìm thấy!
    echo 💡 Vui lòng cài đặt Java JDK 11 trở lên
    echo � Tải từ: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)
echo ✅ Java OK

echo [2/4] Kiểm tra MySQL JDBC Driver...
if not exist "lib\mysql-connector-j-8.0.33.jar" (
    echo ❌ MySQL JDBC Driver không tìm thấy!
    echo 💡 Đang tự động tải...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar' -OutFile 'lib\mysql-connector-j-8.0.33.jar'"
    if exist "lib\mysql-connector-j-8.0.33.jar" (
        echo ✅ Đã tải MySQL JDBC Driver thành công
    ) else (
        echo ❌ Không thể tải JDBC Driver
        pause
        exit /b 1
    )
) else (
    echo ✅ MySQL JDBC Driver OK
)

echo [3/4] Kiểm tra MySQL Server (XAMPP)...
netstat -an | findstr :3306 >nul
if %errorlevel% neq 0 (
    echo ❌ MySQL Server không chạy!
    echo.
    echo 💡 Vui lòng:
    echo    1. Mở XAMPP Control Panel
    echo    2. Start Apache và MySQL
    echo    3. Chạy lại script này
    echo.
    echo 🔧 Hoặc chạy: setup-xampp.bat để được hướng dẫn chi tiết
    echo.
    set /p choice="Bạn có muốn mở hướng dẫn setup XAMPP? (y/n): "
    if /i "!choice!"=="y" call setup-xampp.bat
    pause
    exit /b 1
)
echo ✅ MySQL Server đang chạy

echo [4/4] Kiểm tra build...
if not exist "build\BookstoreApp.class" (
    echo ⚠️  Class files chưa được build
    echo 🔨 Đang build...
    javac -cp "lib\mysql-connector-j-8.0.33.jar" -d build BookstoreApp.java
    if %errorlevel% neq 0 (
        echo ❌ Lỗi build!
        pause
        exit /b 1
    )
    echo ✅ Build thành công
) else (
    echo ✅ Build OK
)

echo.
echo ========================================================================
echo 🚀 KHỞI ĐỘNG ỨNG DỤNG
echo ========================================================================
echo.
echo � Đang mở giao diện đăng nhập...
echo 🔗 Kết nối MySQL XAMPP localhost:3306
echo.

REM Chạy ứng dụng
java -cp "build;lib\mysql-connector-j-8.0.33.jar" BookstoreApp

REM Kiểm tra kết quả
if %errorlevel%==0 (
    echo.
    echo ✅ Ứng dụng đã hoạt động thành công!
) else (
    echo.
    echo ❌ Lỗi chạy ứng dụng!
    echo.
    echo 🔍 Các nguyên nhân có thể:
    echo    1. Database 'bookstore' chưa được tạo
    echo    2. XAMPP MySQL đã dừng
    echo    3. Port 3306 bị chặn
    echo    4. Dữ liệu chưa được import
    echo.
    echo � Giải pháp:
    echo    📋 Chạy: setup-xampp.bat để setup database
    echo    🔧 Kiểm tra XAMPP Control Panel
    echo    🌐 Mở phpMyAdmin: http://localhost/phpmyadmin
    echo.
    pause
)

echo.
echo 🎯 Tài khoản demo:
echo    👤 admin/admin123     - Quản trị viên
echo    👤 user/user123       - Người dùng  
echo    👤 manager/manager123 - Nhân viên
echo.
echo 👋 Cảm ơn bạn đã sử dụng Bookstore Management!
pause
