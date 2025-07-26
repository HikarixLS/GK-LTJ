@echo off
cls
echo ========================================================
echo    🚀 BOOK STORE MANAGEMENT - OPTIMIZED RUNNER  
echo ========================================================
echo.

cd /d "%~dp0"
echo 📁 Directory: %CD%
echo.

REM Java 24 Setup
set "JAVA_HOME=H:\JDK 24"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Enhanced Maven JVM Arguments for Java 24 - Suppress warnings
set "MAVEN_OPTS=-XX:+IgnoreUnrecognizedVMOptions --enable-native-access=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.base/sun.nio.ch=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.desktop/java.awt.font=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"

echo 📋 Java: %JAVA_HOME%
echo 🔧 Enhanced Java 24 compatibility mode
echo.

REM Check Java
"%JAVA_HOME%\bin\java" -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java 24 not found at: %JAVA_HOME%
    echo 💡 Please install Java 24 or update JAVA_HOME
    pause
    exit /b 1
)
echo ✅ Java 24 detected

REM Check MySQL
echo.
echo 🔍 Checking MySQL...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe" >NUL
if "%ERRORLEVEL%" == "0" (
    echo ✅ MySQL running
) else (
    echo ⚠️  MySQL not running
    echo 🔧 Starting XAMPP MySQL automatically...
    
    REM Try to start MySQL via XAMPP
    if exist "C:\xampp\mysql_start.bat" (
        call "C:\xampp\mysql_start.bat"
        timeout /t 3 >nul
    ) else (
        echo 💡 Please start XAMPP MySQL manually
        pause
        exit /b 1
    )
)

echo.
echo ========================================================
echo 🏗️  BUILDING ^& STARTING APPLICATION
echo ========================================================
echo 🌐 App URL: http://localhost:8080
echo 🛑 Press Ctrl+C to stop  
echo 📊 Loading books, students ^& sample data...
echo.

REM Clean build and run
echo 🧹 Cleaning previous build...
call mvnw.cmd clean -q

echo 🔨 Compiling with Java 24...
call mvnw.cmd compile
if %errorlevel% neq 0 (
    echo ❌ Compilation failed! Trying verbose mode...
    call mvnw.cmd compile
    pause
    exit /b 1
)

echo ✅ Build successful!
echo.
echo 🚀 Starting Spring Boot application...
echo ⏳ Please wait for "Started ProductManagementApplication" message...
echo.

REM Run with full output to see server startup
call mvnw.cmd spring-boot:run

echo.
echo 🏁 Application stopped.
pause