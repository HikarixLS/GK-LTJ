@echo off
title Bookstore Management - System Check
color 0A

echo.
echo  ██████╗██╗  ██╗███████╗ ██████╗██╗  ██╗
echo ██╔════╝██║  ██║██╔════╝██╔════╝██║ ██╔╝
echo ██║     ███████║█████╗  ██║     █████╔╝ 
echo ██║     ██╔══██║██╔══╝  ██║     ██╔═██╗ 
echo ╚██████╗██║  ██║███████╗╚██████╗██║  ██╗
echo  ╚═════╝╚═╝  ╚═╝╚══════╝ ╚═════╝╚═╝  ╚═╝
echo.
echo    🔍 BOOKSTORE MANAGEMENT - SYSTEM CHECK
echo.
echo ========================================================

set error_count=0

echo [1/8] Kiểm tra hệ điều hành Windows...
ver | findstr "Windows" >nul
if %ERRORLEVEL% EQU 0 (
    echo ✅ Windows detected
    for /f "tokens=2 delims=[]" %%i in ('ver') do echo    Version: %%i
) else (
    echo ❌ Không phải Windows OS
    set /a error_count+=1
)

echo.
echo [2/8] Kiểm tra Java Runtime Environment...
java -version >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo ✅ Java Runtime có sẵn
    for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr "version"') do (
        echo    Version: %%i
    )
) else (
    echo ❌ Java Runtime không tìm thấy
    echo 💡 Cài đặt từ: https://www.oracle.com/java/technologies/downloads/
    set /a error_count+=1
)

echo.
echo [3/8] Kiểm tra Java Compiler...
javac -version >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo ✅ Java Compiler có sẵn
    for /f "tokens=2" %%i in ('javac -version 2^>^&1') do (
        echo    Version: %%i
    )
) else (
    echo ❌ Java Compiler không tìm thấy
    echo 💡 Cài đặt JDK (không chỉ JRE) từ Oracle
    set /a error_count+=1
)

echo.
echo [4/8] Kiểm tra MySQL Service...
sc query mysql80 >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo ✅ MySQL Service được tìm thấy
    sc query mysql80 | findstr "STATE" | findstr "RUNNING" >nul
    if %ERRORLEVEL% EQU 0 (
        echo    Status: RUNNING ✅
    ) else (
        echo    Status: STOPPED ⚠️
        echo    💡 Khởi động: net start mysql80
    )
) else (
    echo ❌ MySQL Service không tìm thấy
    echo 💡 Cài đặt từ: https://dev.mysql.com/downloads/installer/
    set /a error_count+=1
)

echo.
echo [5/8] Kiểm tra MySQL JDBC Driver...
if exist "lib\mysql-connector-*.jar" (
    echo ✅ MySQL JDBC Driver có sẵn
    for %%f in ("lib\mysql-connector-*.jar") do (
        echo    File: %%~nxf
    )
) else (
    echo ❌ MySQL JDBC Driver không tìm thấy
    echo 💡 Tải từ: https://dev.mysql.com/downloads/connector/j/
    echo 💡 Đặt vào thư mục: lib\
    set /a error_count+=1
)

echo.
echo [6/8] Kiểm tra cấu trúc dự án...
if exist "src\main\java\BookstoreApp.java" (
    echo ✅ Main class có sẵn
) else (
    echo ❌ Main class không tìm thấy
    set /a error_count+=1
)

if exist "src\main\resources\database.properties" (
    echo ✅ Database config có sẵn
) else (
    echo ❌ Database config không tìm thấy
    set /a error_count+=1
)

if exist "src\main\resources\database\bookstore.sql" (
    echo ✅ Database schema có sẵn
) else (
    echo ❌ Database schema không tìm thấy
    set /a error_count+=1
)

echo.
echo [7/8] Kiểm tra kết nối MySQL...
mysql --version >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo ✅ MySQL Client có sẵn
    mysql --version 2>&1 | findstr "Distrib"
    
    echo 💡 Test kết nối database:
    echo    mysql -u root -p -e "SHOW DATABASES;"
) else (
    echo ⚠️  MySQL Client không có trong PATH
    echo 💡 Thêm MySQL bin vào Environment Variables
)

echo.
echo [8/8] Kiểm tra port 3306...
netstat -an | findstr ":3306" >nul
if %ERRORLEVEL% EQU 0 (
    echo ✅ Port 3306 đang được sử dụng (có thể là MySQL)
) else (
    echo ⚠️  Port 3306 không có kết nối
    echo 💡 Đảm bảo MySQL Server đang chạy
)

echo.
echo ========================================================
if %error_count% EQU 0 (
    echo ✅ TẤT CẢ KIỂM TRA THÀNH CÔNG! 
    echo 🎉 Hệ thống sẵn sàng chạy Bookstore Management
    echo.
    echo 🚀 Các bước tiếp theo:
    echo    1. Chạy: setup.bat     (Thiết lập database và build)
    echo    2. Chạy: run.bat       (Khởi động ứng dụng)
) else (
    echo ❌ PHÁT HIỆN %error_count% LỖI!
    echo 🔧 Vui lòng khắc phục các lỗi trên trước khi tiếp tục
    echo.
    echo 💡 Trợ giúp:
    echo    📄 Xem: SETUP.md               (Hướng dẫn chi tiết)
    echo    🤖 Chạy: install-windows.ps1   (Cài đặt tự động)
    echo    🔧 Chạy: setup.bat             (Setup database)
)
echo ========================================================

echo.
echo 📊 Thông tin hệ thống:
echo    💻 OS: %OS%
echo    🏠 Computer: %COMPUTERNAME%
echo    👤 User: %USERNAME%
echo    📁 Current Dir: %CD%
echo    🕐 Time: %DATE% %TIME%

echo.
echo 📞 Hỗ trợ:
echo    📧 Nếu gặp vấn đề, hãy gửi thông tin check này
echo    📖 Đọc README.md để biết thêm chi tiết
echo    🐛 Report bugs tại repository GitHub

echo.
pause
