@echo off
cls
echo ========================================================
echo    🔧 SIMPLE RUN - Product Management System
echo ========================================================
echo.

cd /d "%~dp0"
echo 📁 Directory: %CD%
echo.

REM Just use the full Maven path that we know works
echo 🚀 Starting with full Maven path...
echo ⏳ Please wait...
echo.

"C:\apache-maven-3.9.11\bin\mvn.cmd" spring-boot:run

echo.
echo 🏁 Done!
pause
