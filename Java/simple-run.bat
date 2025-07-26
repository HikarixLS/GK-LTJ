@echo off
title Book Store Server
cls

echo ========================================================
echo    🚀 BOOK STORE MANAGEMENT - SIMPLE RUNNER
echo ========================================================

cd /d "%~dp0"
echo 📁 Current Directory: %CD%

REM Set Java 24
set JAVA_HOME=H:\JDK 24
set PATH=%JAVA_HOME%\bin;%PATH%

echo 📋 Testing Java...
java -version

echo.
echo 🔍 Checking MySQL...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I "mysqld.exe" >NUL
if %errorlevel% == 0 (
    echo ✅ MySQL is running
) else (
    echo ❌ MySQL not running - Please start XAMPP MySQL!
    pause
    exit /b 1
)

echo.
echo 🧹 Cleaning...
call mvnw.cmd clean

echo 🔨 Compiling...
call mvnw.cmd compile
if %errorlevel% neq 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo ✅ Compilation successful!
echo.
echo 🚀 Starting server...
echo ⏳ Wait for "APPLICATION STARTED SUCCESSFULLY" message...
echo.

call mvnw.cmd spring-boot:run

pause
