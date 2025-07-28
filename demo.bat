@echo off
chcp 65001 >nul
color 0B
title 🎬 DEMO - Hướng dẫn sử dụng Bookstore Management

:DEMO_START
cls
echo.
echo   ╔═══════════════════════════════════════════════════════════════════════════════╗
echo   ║                    🎬 DEMO - BOOKSTORE MANAGEMENT 🎬                        ║
echo   ║                         Hướng dẫn sử dụng tương tác                         ║
echo   ╚═══════════════════════════════════════════════════════════════════════════════╝
echo.

echo 🎯 DEMO này sẽ hướng dẫn bạn:
echo    ✅ Cách đăng nhập vào hệ thống
echo    ✅ Cách quản lý sách (thêm, sửa, xóa, tìm kiếm)
echo    ✅ Cách quản lý người dùng (admin)
echo    ✅ Cách sử dụng các tính năng khác
echo.

echo 🔑 Tài khoản demo có sẵn:
echo    👤 admin/admin123     - Quản trị viên (toàn quyền)
echo    👤 user/user123       - Người dùng (chỉ xem)
echo    👤 manager/manager123 - Nhân viên (quản lý sách)
echo.

set /p start_demo="📺 Bạn có muốn xem demo không? (y/n): "
if /i "%start_demo%"=="n" goto EXIT
if /i "%start_demo%"=="no" goto EXIT

echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo 🚀 BẮT ĐẦU DEMO
echo ══════════════════════════════════════════════════════════════════════════════════

echo.
echo 📝 Bước 1: Khởi động ứng dụng
echo    💡 Chạy lệnh: run.bat
echo    ⏱️  Chờ cửa sổ login xuất hiện...
echo.
pause

echo 📝 Bước 2: Đăng nhập với tài khoản Admin
echo    👤 Username: admin
echo    🔒 Password: admin123
echo    🖱️  Click nút "Đăng nhập"
echo.
pause

echo 📝 Bước 3: Khám phá giao diện chính
echo    📊 Main Frame với 2 tab chính:
echo       📚 "Quản lý Sách" - Quản lý kho sách
echo       👥 "Quản lý Người dùng" - Quản lý user (chỉ admin)
echo    🔝 Menu bar với các tùy chọn hệ thống
echo.
pause

echo 📝 Bước 4: Demo quản lý sách
echo    ➕ Thêm sách mới:
echo       🖱️  Click nút "➕ Thêm"
echo       📝 Điền thông tin: Tên sách, Tác giả, ISBN, etc.
echo       💾 Click "Lưu"
echo.
echo    ✏️  Sửa sách:
echo       🖱️  Double-click vào dòng sách trong bảng
echo       📝 Chỉnh sửa thông tin
echo       💾 Click "Lưu"
echo.
echo    🗑️  Xóa sách:
echo       ☑️  Chọn sách trong bảng
echo       🖱️  Click nút "🗑️ Xóa"
echo       ✅ Xác nhận xóa
echo.
pause

echo 📝 Bước 5: Demo tìm kiếm và lọc
echo    🔍 Tìm kiếm:
echo       📝 Nhập từ khóa vào ô "Từ khóa"
echo       🔍 Tự động tìm theo tên, tác giả, ISBN
echo.
echo    🏷️  Lọc theo danh mục:
echo       📋 Chọn danh mục từ dropdown
echo       📊 Kết quả được cập nhật tự động
echo.
echo    🔄 Reset:
echo       🖱️  Click "🔄 Làm mới" để xem tất cả sách
echo.
pause

echo 📝 Bước 6: Demo quản lý người dùng (Admin only)
echo    👥 Chuyển sang tab "Quản lý Người dùng"
echo.
echo    ➕ Thêm user mới:
echo       🖱️  Click "➕ Thêm"
echo       📝 Điền: Username, Password, Full name, Email
echo       🎭 Chọn Role: USER, EMPLOYEE, ADMIN
echo       💾 Click "Lưu"
echo.
echo    ✏️  Sửa thông tin user:
echo       🖱️  Double-click vào user
echo       📝 Chỉnh sửa thông tin
echo       💾 Lưu thay đổi
echo.
echo    ❌ Vô hiệu hóa user:
echo       ☑️  Chọn user
echo       🖱️  Click "🗑️ Xóa"
echo       ✅ User bị vô hiệu hóa (is_active = false)
echo.
pause

echo 📝 Bước 7: Demo các tính năng khác
echo    🔧 Menu "Hệ thống":
echo       🔑 "Đổi mật khẩu" - Thay đổi password
echo       📊 "Thông tin hệ thống" - Xem thông tin ứng dụng
echo       🚪 "Đăng xuất" - Thoát và về màn hình login
echo.
echo    💡 Tips sử dụng:
echo       ⌨️  Có thể dùng Enter để submit form
echo       ❌ ESC để đóng dialog
echo       🖱️  Double-click để chỉnh sửa nhanh
echo       📊 Status bar hiển thị thông tin hệ thống
echo.
pause

echo 📝 Bước 8: Test với các loại tài khoản khác
echo    🔄 Đăng xuất khỏi admin
echo.
echo    👤 Đăng nhập với user/user123:
echo       ❌ Không thấy tab "Quản lý Người dùng"
echo       👁️  Chỉ có thể xem danh sách sách
echo       🚫 Không thể thêm/sửa/xóa sách
echo.
echo    👤 Đăng nhập với manager/manager123:
echo       ✅ Có thể quản lý sách
echo       ❌ Không thấy tab "Quản lý Người dùng"
echo       🔧 Quyền nhân viên
echo.
pause

echo.
echo ══════════════════════════════════════════════════════════════════════════════════
echo 🎉 HOÀN TẤT DEMO!
echo ══════════════════════════════════════════════════════════════════════════════════
echo.

echo 🎯 Bạn đã học được:
echo    ✅ Cách đăng nhập với nhiều loại tài khoản
echo    ✅ Quản lý sách: thêm, sửa, xóa, tìm kiếm
echo    ✅ Quản lý người dùng (admin)
echo    ✅ Phân quyền và bảo mật
echo    ✅ Sử dụng giao diện GUI một cách hiệu quả
echo.

echo 🚀 Bây giờ bạn có thể:
echo    1️⃣  Chạy ứng dụng: run.bat
echo    2️⃣  Đăng nhập và thử nghiệm
echo    3️⃣  Xem hướng dẫn chi tiết: HUONG-DAN-CHAY.md
echo.

echo 💡 Gợi ý thêm:
echo    📚 Thử thêm một số sách mới
echo    👥 Tạo tài khoản người dùng mới
echo    🔍 Test tính năng tìm kiếm với từ khóa khác nhau
echo    🎨 Khám phá các chức năng trong menu
echo.

:LAUNCH_APP
set /p launch="🚀 Bạn có muốn khởi động ứng dụng ngay bây giờ không? (y/n): "
if /i "%launch%"=="y" goto RUN_APP
if /i "%launch%"=="yes" goto RUN_APP
goto EXIT

:RUN_APP
echo.
echo 🚀 Đang khởi động Bookstore Management...
call run.bat
goto EXIT

:EXIT
echo.
echo   ╔═══════════════════════════════════════════════════════════════════════════════╗
echo   ║                            👋 HẸN GẶP LẠI!                                   ║
echo   ║                    Chúc bạn sử dụng ứng dụng thành công! 🎉                  ║
echo   ╚═══════════════════════════════════════════════════════════════════════════════╝
echo.
pause
exit /b 0
