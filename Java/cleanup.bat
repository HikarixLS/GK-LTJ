@echo off
echo === CLEANING UP PROJECT ===
cd /d "%~dp0"

echo Removing duplicate files...
if exist "INSTALLATION.md" del "INSTALLATION.md"
if exist "PROJECT_STATUS.md" del "PROJECT_STATUS.md" 
if exist "README_OPTIMIZED.md" del "README_OPTIMIZED.md"
if exist "setup_xampp.bat" del "setup_xampp.bat"
if exist "simple-run.bat" del "simple-run.bat"
if exist "quick-start.bat" del "quick-start.bat"
if exist "start.sh" del "start.sh"
if exist "XAMPP_SETUP.md" del "XAMPP_SETUP.md"

echo Removing target directory...
if exist "target" rmdir /s /q "target"

echo === CLEANUP COMPLETED ===
pause
