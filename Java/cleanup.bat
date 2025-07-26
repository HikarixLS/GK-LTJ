@echo off
cls
echo ========================================================
echo    🧹 PROJECT CLEANUP - Remove unnecessary files
echo ========================================================
echo.

cd /d "%~dp0"
echo 📁 Current directory: %CD%
echo.

echo 🗑️  Cleaning up temporary files...

REM Remove Maven target directory
if exist "target" (
    echo   - Removing target/ directory...
    rmdir /s /q "target"
)

REM Remove IDE files
if exist ".idea" (
    echo   - Removing .idea/ directory...
    rmdir /s /q ".idea"
)

if exist "*.iml" (
    echo   - Removing IntelliJ files...
    del /q "*.iml"
)

if exist ".vscode" (
    echo   - Removing .vscode/ directory...
    rmdir /s /q ".vscode"
)

REM Remove OS specific files
if exist "Thumbs.db" (
    echo   - Removing Thumbs.db...
    del /q "Thumbs.db"
)

if exist ".DS_Store" (
    echo   - Removing .DS_Store...
    del /q ".DS_Store"
)

REM Remove log files
if exist "*.log" (
    echo   - Removing log files...
    del /q "*.log"
)

if exist "logs" (
    echo   - Removing logs/ directory...
    rmdir /s /q "logs"
)

REM Remove temporary files
if exist "*.tmp" (
    echo   - Removing temporary files...
    del /q "*.tmp"
)

echo.
echo ✅ Cleanup completed!
echo 💡 Use 'mvnw clean' to rebuild the project
echo.
pause
