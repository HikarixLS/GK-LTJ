@echo off
cls
echo ========================================================
echo    🚀 BOOK STORE MANAGEMENT - QUICK START
echo ========================================================
echo.

cd /d "%~dp0"
echo 📁 Current directory: %CD%
echo.

REM Set Java Home for H drive
set "JAVA_HOME=H:\JDK 24"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo 📋 Using Java: %JAVA_HOME%
echo.

REM Test Java
echo 🔍 Testing Java...
java -version
if %errorlevel% neq 0 (
    echo ❌ Java failed
    pause
    exit /b 1
)

echo ✅ Java OK
echo.

REM Check MySQL
echo 🔍 Checking MySQL...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe" >NUL
if "%ERRORLEVEL%" == "0" (
    echo ✅ MySQL is running
) else (
    echo ⚠️  Please start XAMPP MySQL first!
    pause
    exit /b 1
)

echo.
echo ========================================================
echo 🏗️  BUILDING AND STARTING APPLICATION...
echo ========================================================
echo 🌐 Application will be available at: http://localhost:8080
echo 🛑 Press Ctrl+C to stop
echo.

REM Try to run directly with Spring Boot Maven plugin
echo 🚀 Starting Spring Boot application...
mvnw.cmd spring-boot:run

echo.
echo 🏁 Application stopped.
pause
