@echo off
cls
echo ========================================================
echo    🚀 SPRING BOOT DIRECT RUN - No Maven Build
echo ========================================================
echo.

cd /d "%~dp0"
echo 📁 Current directory: %CD%
echo.

REM Set Java environment
set "JAVA_HOME=H:\JDK 24"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo 📋 Using Java: %JAVA_HOME%
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
echo 🚀 Trying Spring Boot without Maven build...
echo 🌐 Application will be available at: http://localhost:8080
echo 🛑 Press Ctrl+C to stop
echo.

REM Try to run with gradle wrapper or direct Spring Boot
if exist "gradlew.bat" (
    echo 📦 Found Gradle wrapper, using Gradle...
    gradlew.bat bootRun
) else (
    echo 📦 Using Maven wrapper with simpler approach...
    REM Force using system java instead of release checking
    set "MAVEN_OPTS=-Dmaven.compiler.release="
    mvnw.cmd -Dmaven.compiler.source=17 -Dmaven.compiler.target=17 spring-boot:run
)

echo.
echo 🏁 Application stopped.
pause
