@echo off
cls
echo ========================================================
echo    ⚡ QUICK START - Product Management System
echo ========================================================
echo.

cd /d "%~dp0"

echo 🚀 Starting application in quick mode...
echo 💡 Make sure XAMPP MySQL is running!
echo.

REM Quick check for MySQL
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe" >NUL
if "%ERRORLEVEL%" neq "0" (
    echo ❌ MySQL not running! Please start XAMPP first.
    timeout /t 3 >nul
    exit /b 1
)

echo ✅ MySQL is running
echo.

REM Try different Maven options
echo 🔍 Detecting Maven...

REM Option 1: Use full path to Maven if available
if exist "C:\apache-maven-3.9.11\bin\mvn.cmd" (
    echo ✅ Using Maven from C:\apache-maven-3.9.11\bin\
    "C:\apache-maven-3.9.11\bin\mvn.cmd" spring-boot:run -Dspring-boot.run.fork=false
    goto end
)

REM Option 2: Try system Maven
mvn -version >nul 2>&1
if %errorlevel% == 0 (
    echo ✅ Using system Maven
    mvn spring-boot:run -Dspring-boot.run.fork=false
    goto end
)

REM Option 3: Use Maven Wrapper
if exist "mvnw.cmd" (
    echo ✅ Using Maven Wrapper
    mvnw.cmd spring-boot:run -Dspring-boot.run.fork=false
    goto end
)

REM Option 4: Last resort - call the main script
echo ⚠️ Maven not found in quick mode
echo 💡 Using main startup script...
call start.bat

:end
pause
