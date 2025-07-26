@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion

echo.
echo ========================================================
echo    🚀 JAVA 24 SPRING BOOT - FINAL OPTIMIZED VERSION  
echo ========================================================

echo 🔍 Java 24 Environment Setup...
set JAVA_HOME=H:\JDK 24

echo 🌟 Java Version:
java -version

echo.
echo 🛠️ Enhanced Maven Options for Java 24...
set MAVEN_OPTS=--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.text=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED --add-opens java.rmi/sun.rmi.transport=ALL-UNNAMED --enable-native-access=ALL-UNNAMED

echo.
echo 🧹 Clean build...
call .\mvnw.cmd clean compile -DskipTests -q

if errorlevel 1 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo.
echo ✅ Build successful! Starting Spring Boot application...
echo 🌐 Application will be available at: http://localhost:8080/hello

echo.
echo 📊 Starting with enhanced JVM arguments...
call .\mvnw.cmd spring-boot:run -DskipTests -Dspring-boot.run.jvmArguments="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --enable-native-access=ALL-UNNAMED -Djava.security.manager=allow"

echo.
echo 🏁 Application finished!
pause
