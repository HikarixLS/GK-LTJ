@echo off
cls
echo ========================================================
echo    🔨 MANUAL COMPILATION - Java Direct Compile
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

REM Create target directories
if not exist "target\classes" mkdir "target\classes"

echo 🔍 Copying resources...
xcopy "src\main\resources\*" "target\classes\" /E /I /Y >nul 2>&1

echo 📁 Setting up classpath...
REM Download dependencies manually if needed, but let's try to use existing .m2 cache

echo 🔨 Compiling Java sources...
dir /S /B "src\main\java\*.java" > temp_sources.txt

echo 🚀 Starting compilation...
javac -cp "C:\Users\YUE\.m2\repository\org\springframework\boot\*\*;C:\Users\YUE\.m2\repository\org\springframework\*\*;C:\Users\YUE\.m2\repository\jakarta\*\*;C:\Users\YUE\.m2\repository\org\projectlombok\*\*;C:\Users\YUE\.m2\repository\mysql\*\*;." -d "target\classes" @temp_sources.txt

if %errorlevel% neq 0 (
    echo ❌ Compilation failed!
    del temp_sources.txt 2>nul
    pause
    exit /b 1
)

echo ✅ Compilation successful!
del temp_sources.txt 2>nul

echo.
echo 🚀 Starting application manually...
cd target\classes
java -cp ".;C:\Users\YUE\.m2\repository\org\springframework\boot\*\*;C:\Users\YUE\.m2\repository\org\springframework\*\*;C:\Users\YUE\.m2\repository\jakarta\*\*;C:\Users\YUE\.m2\repository\org\projectlombok\*\*;C:\Users\YUE\.m2\repository\mysql\*\*" com.gdu.productmanagement.ProductManagementApplication

pause
