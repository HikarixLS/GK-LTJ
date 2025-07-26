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

REM Check if Java is installed and setup JAVA_HOME
echo.
echo 🔍 [STEP 2] Checking Java installation and setting up JAVA_HOME...

REM First check if JAVA_HOME is already set
if not "%JAVA_HOME%"=="" (
    echo 📋 JAVA_HOME already set: %JAVA_HOME%
    goto :check_java_version
)

REM Try to find Java installation automatically
echo 🔍 Auto-detecting Java installation...

REM Check common Oracle JDK locations
for /f "tokens=2*" %%A in ('reg query "HKLM\SOFTWARE\JavaSoft\Java Development Kit" /v CurrentVersion 2^>nul') do set JavaCurrentVersion=%%B
if not "%JavaCurrentVersion%"=="" (
    for /f "tokens=2*" %%A in ('reg query "HKLM\SOFTWARE\JavaSoft\Java Development Kit\%JavaCurrentVersion%" /v JavaHome 2^>nul') do set JAVA_HOME=%%B
)

REM If not found, try OpenJDK locations
if "%JAVA_HOME%"=="" (
    for /f "tokens=2*" %%A in ('reg query "HKLM\SOFTWARE\Eclipse Adoptium\JDK" /v CurrentVersion 2^>nul') do set JavaCurrentVersion=%%B
    if not "%JavaCurrentVersion%"=="" (
        for /f "tokens=2*" %%A in ('reg query "HKLM\SOFTWARE\Eclipse Adoptium\JDK\%JavaCurrentVersion%\hotspot\MSI" /v Path 2^>nul') do set JAVA_HOME=%%B
    )
)

REM Try Program Files locations
if "%JAVA_HOME%"=="" (
    if exist "C:\Program Files\Java\jdk*" (
        for /d %%i in ("C:\Program Files\Java\jdk*") do set JAVA_HOME=%%i
    )
)

if "%JAVA_HOME%"=="" (
    if exist "C:\Program Files\Eclipse Adoptium\jdk*" (
        for /d %%i in ("C:\Program Files\Eclipse Adoptium\jdk*") do set JAVA_HOME=%%i
    )
)

REM Check where java command points to
if "%JAVA_HOME%"=="" (
    for /f "tokens=*" %%A in ('where java 2^>nul') do (
        set "JAVA_PATH=%%A"
        goto :found_java_path
    )
)

:found_java_path
if not "%JAVA_PATH%"=="" (
    REM Extract directory from java.exe path and go up to JDK root
    for %%i in ("%JAVA_PATH%") do set "JAVA_HOME=%%~dpi.."
    for %%i in ("%JAVA_HOME%") do set "JAVA_HOME=%%~fi"
)

:check_java_version
if "%JAVA_HOME%"=="" (
    echo ❌ Could not find Java installation
    echo 💡 Please install Java 17+ or set JAVA_HOME manually
    echo 📖 Example: set JAVA_HOME=C:\Program Files\Java\jdk-17
    pause
    exit /b 1
)

echo 📋 Using JAVA_HOME: %JAVA_HOME%

REM Verify Java version
"%JAVA_HOME%\bin\java" -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java executable not found at: %JAVA_HOME%\bin\java
    echo 💡 Please check your Java installation
    pause
    exit /b 1
)

echo ✅ Java is available and JAVA_HOME is set

REM Check Maven Wrapper
echo.
echo 🔍 [STEP 3] Checking Maven Wrapper...
if exist "mvnw.cmd" (
    echo ✅ Maven Wrapper found
    set "MVN_CMD=mvnw.cmd"
) else (
    echo ❌ Maven Wrapper not found
    echo 💡 Please ensure mvnw.cmd exists
    pause
    exit /b 1
)

echo.
echo ========================================================
echo 🏗️  [STEP 4] BUILDING PROJECT...
echo ========================================================
echo ⏳ Please wait, this may take a few minutes on first run...
echo 📋 Using Java: %JAVA_HOME%\bin\java
echo.

REM Set JAVA_HOME for Maven
set "PATH=%JAVA_HOME%\bin;%PATH%"

call "%MVN_CMD%" clean compile -q
if %errorlevel% neq 0 (
    echo.
    echo ❌ Build failed! Trying with verbose output...
    echo.
    call "%MVN_CMD%" clean compile
    echo.
    echo 💡 Please check the error messages above
    echo 🔧 Java Home: %JAVA_HOME%
    echo 🔧 Maven Command: %MVN_CMD%
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
