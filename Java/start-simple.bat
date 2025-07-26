@echo off
cls
echo ========================================================
echo    🚀 PRODUCT MANAGEMENT SYSTEM - SIMPLE STARTUP
echo ========================================================
echo.

cd /d "%~dp0"
echo 📁 Current directory: %CD%
echo.

echo 🔍 [STEP 1] Setting up Java from H drive...

REM Try to find Java on H drive
set "JAVA_HOME="

REM Common Java locations on H drive
if exist "H:\Program Files\Java\jdk*" (
    for /d %%i in ("H:\Program Files\Java\jdk*") do set "JAVA_HOME=%%i"
)

if "%JAVA_HOME%"=="" (
    if exist "H:\Java\jdk*" (
        for /d %%i in ("H:\Java\jdk*") do set "JAVA_HOME=%%i"
    )
)

if "%JAVA_HOME%"=="" (
    if exist "H:\jdk*" (
        for /d %%i in ("H:\jdk*") do set "JAVA_HOME=%%i"
    )
)

REM Try Eclipse Adoptium on H drive
if "%JAVA_HOME%"=="" (
    if exist "H:\Program Files\Eclipse Adoptium\jdk*" (
        for /d %%i in ("H:\Program Files\Eclipse Adoptium\jdk*") do set "JAVA_HOME=%%i"
    )
)

REM Try other common locations
if "%JAVA_HOME%"=="" (
    if exist "H:\Development\Java\jdk*" (
        for /d %%i in ("H:\Development\Java\jdk*") do set "JAVA_HOME=%%i"
    )
)

if "%JAVA_HOME%"=="" (
    if exist "H:\Tools\Java\jdk*" (
        for /d %%i in ("H:\Tools\Java\jdk*") do set "JAVA_HOME=%%i"
    )
)

if "%JAVA_HOME%"=="" (
    echo ❌ Could not find Java on H drive
    echo 💡 Please tell me the exact path to your Java installation
    echo 📁 Example locations to check:
    echo    - H:\Program Files\Java\jdk-XX
    echo    - H:\Java\jdk-XX
    echo    - H:\Development\Java\jdk-XX
    echo.
    echo 🔍 Let's check what's on H drive:
    dir H:\ /AD 2>nul | findstr /i java
    echo.
    pause
    exit /b 1
)

echo ✅ Found Java at: %JAVA_HOME%

REM Test Java
echo 🔍 [STEP 2] Testing Java installation...
"%JAVA_HOME%\bin\java" -version
if %errorlevel% neq 0 (
    echo ❌ Java test failed
    pause
    exit /b 1
)

echo ✅ Java is working

REM Check MySQL
echo.
echo 🔍 [STEP 3] Checking XAMPP MySQL...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe" >NUL
if "%ERRORLEVEL%" == "0" (
    echo ✅ MySQL is running
) else (
    echo ⚠️  MySQL not running - Please start XAMPP MySQL first!
    pause
    exit /b 1
)

REM Set up environment
echo.
echo 🔍 [STEP 4] Setting up environment...
set "PATH=%JAVA_HOME%\bin;%PATH%"
echo ✅ Environment ready

REM Build and run
echo.
echo ========================================================
echo 🏗️  [STEP 5] BUILDING AND STARTING APPLICATION...
echo ========================================================
echo ⏳ Please wait, this may take a few minutes...
echo 📋 Using Java: %JAVA_HOME%
echo.

echo 🔨 Building project...
mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ❌ Build failed!
    pause
    exit /b 1
)

echo ✅ Build successful!
echo.
echo 🚀 Starting application...
echo 🌐 Application will be available at: http://localhost:8080
echo 🛑 Press Ctrl+C to stop the application
echo.

mvnw.cmd spring-boot:run

echo.
echo 🏁 Application stopped.
pause
