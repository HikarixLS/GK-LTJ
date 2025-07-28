@echo off
title Bookstore Management - Build Script
color 0B

echo.
echo ████████╗██████╗ ██╗   ██╗██╗██╗     ██████╗ 
echo ╚══██╔══╝██╔══██╗██║   ██║██║██║     ██╔══██╗
echo    ██║   ██████╔╝██║   ██║██║██║     ██║  ██║
echo    ██║   ██╔══██╗██║   ██║██║██║     ██║  ██║
echo    ██║   ██████╔╝╚██████╔╝██║███████╗██████╔╝
echo    ╚═╝   ╚═════╝  ╚═════╝ ╚═╝╚══════╝╚═════╝ 
echo.
echo    🏗️  BUILDING BOOKSTORE MANAGEMENT FOR WINDOWS
echo.
echo ========================================================

REM Kiểm tra Java
echo [1/6] Kiểm tra môi trường Java...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java không được tìm thấy! Vui lòng cài đặt Java JDK 11+
    pause
    exit /b 1
)
echo ✅ Java environment OK

javac -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java Compiler không có sẵn! Vui lòng cài đặt JDK (không chỉ JRE)
    pause
    exit /b 1
)
echo ✅ Java Compiler OK

REM Kiểm tra MySQL JDBC Driver
echo.
echo [2/6] Kiểm tra dependencies...
if not exist "lib\mysql-connector-*.jar" (
    echo ❌ MySQL JDBC Driver không tìm thấy trong lib\
    echo 💡 Vui lòng tải từ: https://dev.mysql.com/downloads/connector/j/
    pause
    exit /b 1
)
echo ✅ MySQL JDBC Driver found

REM Dọn dẹp build cũ
echo.
echo [3/6] Dọn dẹp build cũ...
if exist "build" (
    rmdir /s /q "build" >nul 2>&1
    echo ✅ Đã xóa build cũ
)

REM Tạo thư mục build
echo.
echo [4/6] Tạo cấu trúc build...
mkdir build >nul 2>&1
mkdir build\classes >nul 2>&1
mkdir build\lib >nul 2>&1
echo ✅ Đã tạo thư mục build

REM Biên dịch Java files
echo.
echo [5/6] Biên dịch source code...
echo ⚙️  Compiling Java classes...

javac -cp "lib\*" -d build\classes -sourcepath src\main\java ^
    src\main\java\*.java ^
    src\main\java\model\*.java ^
    src\main\java\dao\*.java ^
    src\main\java\service\*.java ^
    src\main\java\gui\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed! Kiểm tra lại source code.
    pause
    exit /b 1
)
echo ✅ Compilation successful

REM Copy resources
echo ⚙️  Copying resources...
if not exist "build\classes\database" mkdir build\classes\database
copy "src\main\resources\database\*" "build\classes\database\" >nul 2>&1
copy "src\main\resources\*.properties" "build\classes\" >nul 2>&1

if %ERRORLEVEL% NEQ 0 (
    echo ⚠️  Warning: Một số resources có thể không được copy
) else (
    echo ✅ Resources copied successfully
)

REM Tạo JAR file
echo.
echo [6/6] Tạo executable JAR...
cd build\classes
jar cfm ..\bookstore-management.jar ..\..\MANIFEST.MF * 

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Failed to create JAR file!
    cd ..\..
    pause
    exit /b 1
)
cd ..\..
echo ✅ JAR file created

REM Copy dependencies
echo ⚙️  Copying dependencies...
copy "lib\*" "build\lib\" >nul 2>&1
echo ✅ Dependencies copied

echo.
echo ========================================================
echo ✅ BUILD COMPLETED SUCCESSFULLY!
echo ========================================================
echo.
echo 📦 Build artifacts:
echo    📄 build\bookstore-management.jar  - Main application
echo    📂 build\classes\                  - Compiled classes  
echo    📂 build\lib\                      - Dependencies
echo.
echo 🚀 Cách chạy:
echo    👉 run.bat                         - Quick run
echo    👉 java -cp "build\classes;lib\*" BookstoreApp
echo    👉 java -jar "build\bookstore-management.jar"
echo.

set /p run_choice="Bạn có muốn chạy ứng dụng ngay? (y/n): "
if /i "%run_choice%"=="y" (
    echo.
    echo 🚀 Starting Bookstore Management...
    java -cp "build\bookstore-management.jar;build\lib\*" BookstoreApp
)

echo.
pause
