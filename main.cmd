@echo off
title Bookstore Management - Windows Only
color 0B
chcp 65001 >nul 2>&1

echo.
echo ████████╗██████╗ ██╗   ██╗██╗██╗     ██████╗ 
echo ╚══██╔══╝██╔══██╗██║   ██║██║██║     ██╔══██╗
echo    ██║   ██████╔╝██║   ██║██║██║     ██║  ██║
echo    ██║   ██╔══██╗██║   ██║██║██║     ██║  ██║
echo    ██║   ██████╔╝╚██████╔╝██║███████╗██████╔╝
echo    ╚═╝   ╚═════╝  ╚═════╝ ╚═╝╚══════╝╚═════╝ 
echo.
echo    🏗️  BOOKSTORE MANAGEMENT - WINDOWS ONLY
echo.
echo ========================================================

:MENU
echo.
echo ===== MAIN MENU =====
echo 1. Check System
echo 2. Setup Database  
echo 3. Build and Run
echo 4. Quick Run (if already built)
echo 5. Exit
echo.
set /p "choice=Choose option (1-5): "

if "%choice%"=="1" goto CHECK
if "%choice%"=="2" goto SETUP
if "%choice%"=="3" goto BUILD
if "%choice%"=="4" goto QUICKRUN
if "%choice%"=="5" goto END
echo ❌ Invalid choice!
pause
goto MENU

:CHECK
echo.
echo [1/4] Checking Java...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java not found! Please install Java JDK 11+
    pause
    goto MENU
)
javac -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java Compiler not found! Please install JDK
    pause
    goto MENU
)
echo ✅ Java OK
java -version

echo.
echo [2/4] Checking dependencies...
if not exist "lib\mysql-connector-*.jar" (
    echo ❌ MySQL JDBC Driver not found in lib\
    pause
    goto MENU
)
echo ✅ MySQL JDBC Driver OK

echo.
echo [3/4] Checking project files...
if exist "src\main\java\BookstoreApp.java" (
    echo ✅ Main Java file found
) else (
    echo ❌ Main Java file missing
)

echo.
echo [4/4] Checking XAMPP MySQL Server...
netstat -an | findstr :3306 >nul
if %errorlevel% neq 0 (
    echo ⚠️  MySQL Server not running (will be needed to run app)
    echo 💡 Remember to start XAMPP MySQL before running the app
) else (
    echo ✅ MySQL Server is running on port 3306
)
echo.
pause
goto MENU

:SETUP
echo.
echo 🗄️  Setting up database...
echo.
echo Checking for XAMPP...
set XAMPP_PATH=
if exist "C:\xampp\mysql\bin\mysql.exe" set XAMPP_PATH=C:\xampp
if exist "C:\Program Files\XAMPP\mysql\bin\mysql.exe" set XAMPP_PATH=C:\Program Files\XAMPP
if exist "d:\soft\xampp\xp\mysql\bin\mysql.exe" set XAMPP_PATH=d:\soft\xampp\xp
if exist "D:\xampp\mysql\bin\mysql.exe" set XAMPP_PATH=D:\xampp
if exist "E:\xampp\mysql\bin\mysql.exe" set XAMPP_PATH=E:\xampp

if "%XAMPP_PATH%"=="" (
    echo ❌ XAMPP not found!
    echo Please install XAMPP and try again.
    echo.
    echo 💡 Alternative: Use phpMyAdmin manually:
    echo    1. Open http://localhost/phpmyadmin
    echo    2. Create database: bookstore
    echo    3. Import: src\main\resources\database\bookstore.sql
    pause
    goto MENU
)

echo ✅ XAMPP found at: %XAMPP_PATH%
echo.
echo Starting XAMPP Control Panel...
start "" "%XAMPP_PATH%\xampp-control.exe"
echo.
echo Please start MySQL service in XAMPP Control Panel
echo Then press any key to continue...
pause

echo.
echo Creating database...
"%XAMPP_PATH%\mysql\bin\mysql.exe" -u root -e "CREATE DATABASE IF NOT EXISTS bookstore;"
if errorlevel 1 (
    echo ❌ Failed to create database!
    echo Please make sure MySQL is running.
) else (
    echo ✅ Database 'bookstore' created successfully!
    if exist "src\main\resources\database\bookstore.sql" (
        echo Importing database schema...
        "%XAMPP_PATH%\mysql\bin\mysql.exe" -u root bookstore < "src\main\resources\database\bookstore.sql"
        if errorlevel 1 (
            echo ⚠️  Failed to import schema
        ) else (
            echo ✅ Database schema imported!
            echo.
            echo 🎯 Default accounts created:
            echo    admin/admin123 - Administrator
            echo    user/user123 - User  
            echo    manager/manager123 - Manager
        )
    )
)
echo.
echo 🔗 phpMyAdmin: http://localhost/phpmyadmin
pause
goto MENU

:BUILD
echo.
echo 🔥 Building and running project...

REM Check Java first
echo [1/4] Checking Java...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java not found! Please install Java JDK 11+
    pause
    goto MENU
)
javac -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java Compiler not found! Please install JDK
    pause
    goto MENU
)
echo ✅ Java OK

REM Check dependencies
echo [2/4] Checking dependencies...
if not exist "lib\mysql-connector-*.jar" (
    echo ❌ MySQL JDBC Driver not found in lib\
    pause
    goto MENU
)
echo ✅ MySQL JDBC Driver OK

REM Check XAMPP MySQL
echo [3/4] Checking XAMPP MySQL Server...
netstat -an | findstr :3306 >nul
if %errorlevel% neq 0 (
    echo ⚠️  MySQL Server not running!
    echo 💡 Please start XAMPP MySQL first
    set /p choice="Continue anyway? (y/n): "
    if /i not "%choice%"=="y" goto MENU
)

REM Clean and build
echo [4/4] Building...
if exist "bin" rmdir /s /q "bin" >nul 2>&1
if exist "build" rmdir /s /q "build" >nul 2>&1

mkdir bin >nul 2>&1
mkdir build >nul 2>&1
mkdir build\lib >nul 2>&1

echo Compiling Java sources to bin\...
javac -encoding UTF-8 -cp "lib\*" -d bin -sourcepath src\main\java src\main\java\*.java src\main\java\model\*.java src\main\java\dao\*.java src\main\java\service\*.java src\main\java\gui\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed!
    pause
    goto MENU
)

REM Copy resources
if exist "src\main\resources\database" (
    if not exist "bin\database" mkdir bin\database
    copy "src\main\resources\database\*" "bin\database\" >nul 2>&1
)
if exist "src\main\resources\*.properties" (
    copy "src\main\resources\*.properties" "bin\" >nul 2>&1
)

REM Create JAR
cd bin
if exist "..\MANIFEST.MF" (
    jar cfm ..\build\bookstore-management.jar ..\MANIFEST.MF *
) else (
    jar cf ..\build\bookstore-management.jar *
)
cd ..
copy "lib\*" "build\lib\" >nul 2>&1

echo.
echo ✅ BUILD COMPLETED!
echo    📂 bin\ - Compiled classes (ready to run)
echo    📄 build\bookstore-management.jar - JAR file
echo.

echo 🚀 Starting Bookstore Management...
echo 🔗 Connecting to XAMPP MySQL localhost:3306
java -cp "bin;lib\*" BookstoreApp

pause
goto MENU

:QUICKRUN
echo.
echo 🚀 Quick Run (using existing build)...

REM Check if bin directory exists
if not exist "bin" (
    echo ❌ No compiled classes found! Please build first (option 3)
    pause
    goto MENU
)

REM Check if main class exists
if not exist "bin\BookstoreApp.class" (
    echo ❌ Main class not found! Please build first (option 3)
    pause
    goto MENU
)

REM Check MySQL
echo Checking XAMPP MySQL Server...
netstat -an | findstr :3306 >nul
if %errorlevel% neq 0 (
    echo ⚠️  MySQL Server not running!
    echo 💡 Please start XAMPP MySQL first
    set /p choice="Continue anyway? (y/n): "
    if /i not "%choice%"=="y" goto MENU
) else (
    echo ✅ MySQL Server is running on port 3306
)

echo.
echo 🚀 Starting Bookstore Management...
echo 🔗 Connecting to XAMPP MySQL localhost:3306
java -cp "bin;lib\*" BookstoreApp

pause
goto MENU

:END
echo.
echo 👋 Goodbye!
exit
