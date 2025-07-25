@echo off
cls
echo ========================================================
echo    🚀 PRODUCT MANAGEMENT SYSTEM - STARTUP SCRIPT
echo ========================================================
echo.

REM Change to the script directory
cd /d "%~dp0"
echo 📁 Current directory: %CD%
echo.

REM Check XAMPP MySQL
echo 🔍 [STEP 1] Checking XAMPP MySQL...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe" >NUL
if "%ERRORLEVEL%" == "0" (
    echo ✅ MySQL is running
) else (
    echo ⚠️  MySQL not running - Please start XAMPP MySQL first!
    echo 💡 Open XAMPP Control Panel and start MySQL
    pause
    exit /b 1
)

REM Set JAVA_HOME if not set
if not defined JAVA_HOME (
    set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot"
)

REM Add Java to PATH
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Check if Java is installed
echo.
echo 🔍 [STEP 2] Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java is not installed or not in PATH
    echo 💡 Please install Java 17 or higher from: https://adoptium.net/
    pause
    exit /b 1
)
echo ✅ Java is available

REM Check if Maven is installed
echo.
echo 🔍 [STEP 3] Checking Maven installation...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  Maven not found in PATH
    echo 💡 Using Maven Wrapper...
    if exist "mvnw.cmd" (
        echo ✅ Maven Wrapper found
        set "MVN_CMD=mvnw.cmd"
    ) else (
        echo ❌ Maven Wrapper not found
        echo 💡 Please install Maven or use Maven Wrapper
        pause
        exit /b 1
    )
) else (
    echo ✅ Maven is available
    set "MVN_CMD=mvn"
)

echo.
echo 🛠️  Using build tool: %MVN_CMD%
echo.
echo ========================================================
echo 🏗️  [STEP 4] BUILDING PROJECT...
echo ========================================================
echo ⏳ Please wait, this may take a few minutes on first run...
echo.

call "%MVN_CMD%" clean compile -q
if %errorlevel% neq 0 (
    echo.
    echo ❌ Build failed! Trying with verbose output...
    echo.
    call "%MVN_CMD%" clean compile
    echo.
    echo 💡 Please check the error messages above
    pause
    exit /b 1
)

echo ✅ Build successful!
echo.

echo ========================================================
echo 🚀 [STEP 5] STARTING APPLICATION...
echo ========================================================
echo 🌐 Application will be available at: http://localhost:8080
echo 🛑 Press Ctrl+C to stop the application
echo 📊 Loading sample data and starting services...
echo.

call "%MVN_CMD%" spring-boot:run

echo.
echo 🏁 Application stopped.
pause
