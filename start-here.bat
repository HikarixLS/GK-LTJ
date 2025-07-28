@echo off
chcp 65001 >nul
color 0A
title 📚 BOOKSTORE MANAGEMENT - HƯỚNG DẪN CHẠY

echo.
echo   ╔═══════════════════════════════════════════════════════════════════════════════╗
echo   ║                         🏪 BOOKSTORE MANAGEMENT 🏪                           ║
echo   ║                              HƯỚNG DẪN CHẠY DỰ ÁN                            ║
echo   ╚═══════════════════════════════════════════════════════════════════════════════╝
echo.

:MENU
echo   🎯 CHỌN CÁCH CHẠY DỰ ÁN:
echo.
echo   [1] 🤖 TỰ ĐỘNG - Cài đặt tự động mọi thứ (Khuyến nghị)
echo   [2] ⚡ NHANH   - Nếu đã có Java ^& MySQL sẵn
echo   [3] 📖 HƯỚNG DẪN CHI TIẾT - Xem file hướng dẫn
echo   [4] 🔍 KIỂM TRA HỆ THỐNG - Xem trạng thái hiện tại
echo   [5] ❌ THOÁT
echo.

set /p choice="👉 Lựa chọn của bạn (1-5): "

if "%choice%"=="1" goto AUTO_INSTALL
if "%choice%"=="2" goto QUICK_RUN
if "%choice%"=="3" goto SHOW_GUIDE
if "%choice%"=="4" goto CHECK_SYSTEM
if "%choice%"=="5" goto EXIT
echo ❌ Lựa chọn không hợp lệ!
echo.
goto MENU

:AUTO_INSTALL
echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo 🤖 CHẠY CÀI ĐẶT TỰ ĐỘNG
echo ══════════════════════════════════════════════════════════════════════════════════
echo.
echo 💡 Sắp mở PowerShell để cài đặt tự động...
echo    📌 Cần quyền Administrator!
echo.
echo 📋 Các bước sẽ tự động thực hiện:
echo    ✅ Cài đặt Java JDK 17
echo    ✅ Cài đặt MySQL Server 8.0
echo    ✅ Tải MySQL JDBC Driver
echo    ✅ Thiết lập môi trường
echo.
pause
powershell.exe -ExecutionPolicy Bypass -File "install-windows.ps1"
echo.
echo 🎉 Cài đặt tự động hoàn tất!
echo 📍 Tiếp theo hãy chạy: setup.bat để thiết lập database
echo.
pause
goto MENU

:QUICK_RUN
echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo ⚡ CHẠY NHANH
echo ══════════════════════════════════════════════════════════════════════════════════
echo.
echo 🔍 Bước 1: Kiểm tra hệ thống...
call check-system.bat
echo.

echo 🔧 Bước 2: Setup database và build...
call setup.bat
echo.

echo 🚀 Bước 3: Chạy ứng dụng...
call run.bat
echo.
pause
goto MENU

:SHOW_GUIDE
echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo 📖 MỞ HƯỚNG DẪN CHI TIẾT
echo ══════════════════════════════════════════════════════════════════════════════════
echo.
echo 📄 Đang mở file hướng dẫn chi tiết...
echo    📂 File: HUONG-DAN-CHAY.md
echo.

if exist "HUONG-DAN-CHAY.md" (
    start "" "HUONG-DAN-CHAY.md"
    echo ✅ Đã mở file hướng dẫn
) else (
    echo ❌ Không tìm thấy file HUONG-DAN-CHAY.md
    echo 💡 Vui lòng xem file README.md hoặc SETUP.md
)
echo.
pause
goto MENU

:CHECK_SYSTEM
echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo 🔍 KIỂM TRA HỆ THỐNG
echo ══════════════════════════════════════════════════════════════════════════════════
echo.
call check-system.bat
echo.
pause
goto MENU

:EXIT
echo.
echo   ╔═══════════════════════════════════════════════════════════════════════════════╗
echo   ║                              👋 CẢM ƠN BẠN!                                  ║
echo   ║                     Chúc bạn sử dụng ứng dụng thành công! 🎉                 ║
echo   ╚═══════════════════════════════════════════════════════════════════════════════╝
echo.
echo 🔑 Tài khoản demo:
echo    👤 admin/admin123     - Quản trị viên
echo    👤 user/user123       - Người dùng  
echo    👤 manager/manager123 - Nhân viên
echo.
echo 📞 Nếu gặp vấn đề, chạy lại script này và chọn [4] để kiểm tra hệ thống
echo.
pause
exit /b 0
