@echo off
chcp 65001 >nul 2>&1
echo ===== BOOKSTORE MANAGEMENT SYSTEM =====

:MENU
echo.
echo ===== MAIN MENU =====
echo 1. Check System
echo 2. Setup Database
echo 3. Build and Run
echo 4. Exit
echo.
set /p "choice=Choose option (1-4): "

if "%choice%"=="1" goto CHECK
if "%choice%"=="2" goto SETUP
if "%choice%"=="3" goto BUILD
if "%choice%"=="4" goto END
echo Invalid choice!
pause
goto MENU

:CHECK
echo.
echo Checking Java...
java -version
echo.
echo Checking files...
if exist "lib\mysql-connector-j-8.0.33.jar" (
    echo [OK] MySQL JAR found
) else (
    echo [ERROR] MySQL JAR missing
)
if exist "src\main\java\BookstoreApp.java" (
    echo [OK] Main Java file found
) else (
    echo [ERROR] Main Java file missing
)
pause
goto MENU

:SETUP
echo.
echo Setting up database...
echo.
echo Checking for XAMPP...
set XAMPP_PATH=
if exist "C:\xampp\mysql\bin\mysql.exe" set XAMPP_PATH=C:\xampp
if exist "C:\Program Files\XAMPP\mysql\bin\mysql.exe" set XAMPP_PATH=C:\Program Files\XAMPP
if exist "d:\soft\xampp\xp\mysql\bin\mysql.exe" set XAMPP_PATH=d:\soft\xampp\xp
if exist "D:\xampp\mysql\bin\mysql.exe" set XAMPP_PATH=D:\xampp
if exist "E:\xampp\mysql\bin\mysql.exe" set XAMPP_PATH=E:\xampp

if "%XAMPP_PATH%"=="" (
    echo [ERROR] XAMPP not found!
    echo Please install XAMPP and try again.
    pause
    goto MENU
)

echo [OK] XAMPP found at: %XAMPP_PATH%
echo.
echo Starting XAMPP Control Panel...
start "" "%XAMPP_PATH%\xampp-control.exe"
echo.
echo Please start MySQL service in XAMPP Control Panel
echo Then press any key to continue...
pause

echo.
echo Creating database...
"%XAMPP_PATH%\mysql\bin\mysql.exe" -u root -e "CREATE DATABASE IF NOT EXISTS bookstore_db;"
if errorlevel 1 (
    echo [ERROR] Failed to create database!
    echo Please make sure MySQL is running.
) else (
    echo [OK] Database 'bookstore_db' created successfully!
    if exist "src\main\resources\database\bookstore.sql" (
        echo Importing database schema...
        "%XAMPP_PATH%\mysql\bin\mysql.exe" -u root bookstore_db < "src\main\resources\database\bookstore.sql"
        if errorlevel 1 (
            echo [WARNING] Failed to import schema
        ) else (
            echo [OK] Database schema imported!
        )
    )
)
echo.
echo You can access phpMyAdmin at: http://localhost/phpmyadmin
pause
goto MENU

:BUILD
echo.
echo Building project...
if exist bin rmdir /s /q bin
mkdir bin
echo Compiling...
javac -encoding UTF-8 -cp "lib\mysql-connector-j-8.0.33.jar" -d bin src\main\java\*.java src\main\java\model\*.java src\main\java\dao\*.java src\main\java\service\*.java src\main\java\gui\*.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    goto MENU
)
echo Copying resources...
if exist "src\main\resources" xcopy "src\main\resources" "bin" /E /I /Q >nul
echo Running...
cd bin
java -cp ".;..\lib\mysql-connector-j-8.0.33.jar" BookstoreApp
cd ..
pause
goto MENU

:END
echo Goodbye!
exit
