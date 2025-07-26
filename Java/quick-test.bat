@echo off
title Quick Test Server
cls

echo ========================================================
echo    ⚡ QUICK TEST - MINIMAL SPRING BOOT APP
echo ========================================================

cd /d "%~dp0"

REM Set Java 24
set JAVA_HOME=H:\JDK 24
set PATH=%JAVA_HOME%\bin;%PATH%

echo 📋 Testing Java...
java -version

echo.
echo 🔨 Quick compile (no data initializer)...
call mvnw.cmd clean compile -DskipTests

if %errorlevel% neq 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo ✅ Compilation successful!
echo.
echo 🚀 Starting basic server...
echo 🌐 Open: http://localhost:8080/hello
echo.

call mvnw.cmd spring-boot:run -DskipTests

pause
