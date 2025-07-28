@echo off
title Bookstore Management - Windows Setup
color 0A

echo.
echo  ████████╗███████╗████████╗██╗   ██╗██████╗ 
echo  ╚══██╔══╝██╔════╝╚══██╔══╝██║   ██║██╔══██╗
echo     ██║   █████╗     ██║   ██║   ██║██████╔╝
echo     ██║   ██╔══╝     ██║   ██║   ██║██╔═══╝ 
echo     ██║   ███████╗   ██║   ╚██████╔╝██║     
echo     ╚═╝   ╚══════╝   ╚═╝    ╚═════╝ ╚═╝     
echo.
echo    🏪 BOOKSTORE MANAGEMENT SYSTEM - WINDOWS SETUP 🏪
echo.
echo ========================================================

REM Kiểm tra Java
echo [1/5] Kiểm tra Java...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java không được tìm thấy!
    echo 💡 Vui lòng cài đặt Java JDK 11+ từ: https://www.oracle.com/java/technologies/downloads/
    echo 💡 Và thiết lập JAVA_HOME trong Environment Variables
    pause
    exit /b 1
) else (
    echo ✅ Java đã được cài đặt
    java -version 2>&1 | findstr "version"
)

echo.
echo [2/5] Kiểm tra cấu trúc thư mục...

REM Tạo thư mục lib nếu chưa có
if not exist "lib" (
    mkdir lib
    echo ✅ Đã tạo thư mục lib
) else (
    echo ✅ Thư mục lib đã tồn tại
)

REM Kiểm tra MySQL JDBC Driver
if not exist "lib\mysql-connector-*.jar" (
    echo ❌ Không tìm thấy MySQL JDBC Driver trong thư mục lib!
    echo.
    echo 💡 Hướng dẫn tải MySQL JDBC Driver:
    echo    1. Truy cập: https://dev.mysql.com/downloads/connector/j/
    echo    2. Chọn "Platform Independent" và tải ZIP
    echo    3. Giải nén và copy file .jar vào thư mục lib\
    echo.
    set /p choice="Bạn đã tải và đặt MySQL JDBC Driver vào lib\? (y/n): "
    if /i "%choice%"=="n" (
        echo 🔄 Vui lòng tải driver và chạy lại setup.bat
        pause
        exit /b 1
    )
    if /i "%choice%"=="y" (
        if not exist "lib\mysql-connector-*.jar" (
            echo ❌ Vẫn không tìm thấy file JAR trong lib\
            pause
            exit /b 1
        )
    )
)
echo ✅ MySQL JDBC Driver có sẵn

echo.
echo [3/5] Kiểm tra MySQL Server...

REM Kiểm tra MySQL service
sc query mysql80 >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo ✅ MySQL Service được tìm thấy
    
    REM Kiểm tra trạng thái MySQL
    sc query mysql80 | findstr "STATE" | findstr "RUNNING" >nul
    if %ERRORLEVEL% NEQ 0 (
        echo ⚠️  MySQL Service không chạy. Đang khởi động...
        net start mysql80 >nul 2>&1
        if %ERRORLEVEL% EQU 0 (
            echo ✅ MySQL đã được khởi động
        ) else (
            echo ❌ Không thể khởi động MySQL. Vui lòng khởi động thủ công.
        )
    ) else (
        echo ✅ MySQL đang chạy
    )
) else (
    echo ❌ MySQL Service không được tìm thấy!
    echo 💡 Vui lòng cài đặt MySQL Server từ: https://dev.mysql.com/downloads/installer/
    pause
    exit /b 1
)

echo.
echo [4/5] Cấu hình Database...

REM Kiểm tra file cấu hình database
if not exist "src\main\resources\database.properties" (
    echo ❌ Không tìm thấy file database.properties
    pause
    exit /b 1
)

echo ✅ File cấu hình database có sẵn

echo.
echo 💡 Lưu ý quan trọng về Database:
echo    1. Đảm bảo đã tạo database 'bookstore'
echo    2. Đã import schema từ src\main\resources\database\bookstore.sql
echo    3. Đã cập nhật mật khẩu MySQL trong database.properties
echo.

set /p db_setup="Bạn đã thiết lập database bookstore chưa? (y/n): "
if /i "%db_setup%"=="n" (
    echo.
    echo 📋 Hướng dẫn thiết lập database:
    echo.
    echo    1. Mở Command Prompt và chạy:
    echo       mysql -u root -p
    echo.
    echo    2. Tạo database:
    echo       CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    echo       exit
    echo.
    echo    3. Import schema:
    echo       mysql -u root -p bookstore ^< "src\main\resources\database\bookstore.sql"
    echo.
    echo    4. Cập nhật mật khẩu trong: src\main\resources\database.properties
    echo.
    pause
    echo 🔄 Vui lòng hoàn thành setup database và chạy lại setup.bat
    exit /b 1
)

echo.
echo [5/5] Build ứng dụng...

REM Tạo thư mục build
if not exist "build" mkdir build
if not exist "build\classes" mkdir build\classes

echo ⚙️  Đang biên dịch source code...
javac -cp "lib\*" -d build\classes -sourcepath src\main\java src\main\java\*.java src\main\java\model\*.java src\main\java\dao\*.java src\main\java\service\*.java src\main\java\gui\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Lỗi biên dịch! Vui lòng kiểm tra source code.
    pause
    exit /b 1
)

echo ✅ Biên dịch thành công

echo ⚙️  Đang copy resources...
if not exist "build\classes\database" mkdir build\classes\database
copy "src\main\resources\database\*" "build\classes\database\" >nul
copy "src\main\resources\*.properties" "build\classes\" >nul

echo ✅ Copy resources thành công

echo.
echo ========================================================
echo ✅ SETUP HOÀN TẤT THÀNH CÔNG!
echo ========================================================
echo.
echo 🚀 Cách chạy ứng dụng:
echo    👉 run.bat                    (Chạy từ class files)
echo    👉 build.bat                  (Build và tạo JAR)
echo.
echo 🔑 Tài khoản demo:
echo    👤 Admin:   admin/admin123    (Toàn quyền)
echo    👤 User:    user/user123      (Chỉ xem)
echo    👤 Manager: manager/manager123 (Nhân viên)
echo.
echo 📁 Thư mục quan trọng:
echo    📂 src\              - Source code
echo    📂 lib\              - MySQL JDBC Driver  
echo    📂 build\            - Build output
echo    📄 database.properties - Cấu hình DB
echo.

set /p run_now="Bạn có muốn chạy ứng dụng ngay bây giờ? (y/n): "
if /i "%run_now%"=="y" (
    echo.
    echo 🚀 Đang khởi động Bookstore Management...
    java -cp "build\classes;lib\*" BookstoreApp
)

echo.
echo 👋 Cảm ơn bạn đã sử dụng Bookstore Management System!
pause
