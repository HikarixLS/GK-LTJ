@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion

echo.
echo ========================================================
echo    🚀 MINIMAL JAVA 24 TEST - CHỈ TestController
echo ========================================================

echo 🔍 Testing Java...
java -version
if errorlevel 1 (
    echo ❌ Java not found!
    pause
    exit /b 1
)

echo.
echo 📦 Creating minimal Spring Boot app structure...

REM Remove problematic files
echo 🗑️ Removing Lombok-dependent files...
if exist "src\main\java\com\gdu\productmanagement\config\SecurityConfig.java" del /F "src\main\java\com\gdu\productmanagement\config\SecurityConfig.java"
if exist "src\main\java\com\gdu\productmanagement\service\UserDetailsServiceImpl.java" del /F "src\main\java\com\gdu\productmanagement\service\UserDetailsServiceImpl.java"
if exist "src\main\java\com\gdu\productmanagement\controller\BookController.java" del /F "src\main\java\com\gdu\productmanagement\controller\BookController.java"
if exist "src\main\java\com\gdu\productmanagement\service\BookService.java" del /F "src\main\java\com\gdu\productmanagement\service\BookService.java"
if exist "src\main\java\com\gdu\productmanagement\service\StudentService.java" del /F "src\main\java\com\gdu\productmanagement\service\StudentService.java"
if exist "src\main\java\com\gdu\productmanagement\service\ProductService.java" del /F "src\main\java\com\gdu\productmanagement\service\ProductService.java"

echo.
echo 🔧 Setting Java 24 environment...
set JAVA_HOME=C:\Program Files\Java\jdk-24.0.1
set MAVEN_OPTS=--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --enable-native-access=ALL-UNNAMED

echo.
echo 🧹 Clean compile...
call mvnw.cmd clean compile -DskipTests -X

if errorlevel 1 (
    echo.
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo.
echo ✅ Compilation successful! Starting minimal Spring Boot app...
echo 🌐 Will be available at: http://localhost:8080/hello

call mvnw.cmd spring-boot:run -DskipTests

echo.
echo 🏁 Done!
pause
