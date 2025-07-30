@echo off
title Bookstore Management - Windows Only
color 0B

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

REM Check Java
echo [1/3] Checking Java...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java not found! Please install Java JDK 11+
    pause
    exit /b 1
)
javac -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Java Compiler not found! Please install JDK
    pause
    exit /b 1
)
echo ✅ Java OK

REM Check dependencies
echo [2/4] Checking dependencies...
if not exist "lib\mysql-connector-*.jar" (
    echo ❌ MySQL JDBC Driver not found in lib\
    pause
    exit /b 1
)
echo ✅ MySQL JDBC Driver OK

REM Check XAMPP MySQL (optional for build)
echo [3/4] Checking XAMPP MySQL Server...
netstat -an | findstr :3306 >nul
if %errorlevel% neq 0 (
    echo ⚠️  MySQL Server not running (will be needed to run app)
    echo 💡 Remember to start XAMPP MySQL before running the app
) else (
    echo ✅ MySQL Server is running on port 3306
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
    exit /b 1
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
echo 💡 To run: Make sure XAMPP MySQL is running first!

set /p choice="Run application now? (y/n): "
if /i "%choice%"=="y" (
    echo.
    echo 🚀 Starting Bookstore Management...
    echo 🔗 Connecting to XAMPP MySQL localhost:3306
    java -cp "bin;lib\*" BookstoreApp
)

echo.
pause
