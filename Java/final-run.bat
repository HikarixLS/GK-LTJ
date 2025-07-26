@echo off
title Java 24 Spring Boot Launcher
echo ========================================================
echo             JAVA 24 SPRING BOOT LAUNCHER
echo ========================================================
echo.

cd /d "H:\Github\LTJ\GK-LTJ\Java"

set JAVA_HOME=H:\JDK 24
set JAVA_OPTS=-Dspring.classformat.ignore=true
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.lang=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.util=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.io=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.time=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.net=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.util.concurrent=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.security=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.math=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/sun.nio.ch=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/sun.net.util=ALL-UNNAMED
set JAVA_OPTS=%JAVA_OPTS% --enable-native-access=ALL-UNNAMED

echo Starting Spring Boot with Java 24...
echo JAVA_HOME: %JAVA_HOME%
echo Classpath: target/classes;target/lib/*
echo.

"%JAVA_HOME%\bin\java" %JAVA_OPTS% -cp "target/classes;target/lib/*" com.gdu.productmanagement.ProductManagementApplication

if errorlevel 1 (
    echo.
    echo ========================================================
    echo            APPLICATION START FAILED
    echo ========================================================
    echo Error Code: %errorlevel%
) else (
    echo.
    echo ========================================================
    echo            APPLICATION STOPPED
    echo ========================================================
)

echo.
pause
