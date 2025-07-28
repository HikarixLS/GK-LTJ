# PowerShell Script để cài đặt tự động dependencies cho Windows
# Chạy với quyền Administrator

param(
    [switch]$SkipJava,
    [switch]$SkipMySQL,
    [switch]$SkipJDBC
)

Write-Host @"

  ██████╗  ██████╗  ██████╗ ██╗  ██╗███████╗████████╗ ██████╗ ██████╗ ███████╗
  ██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝
  ██████╔╝██║   ██║██║   ██║█████╔╝ ███████╗   ██║   ██║   ██║██████╔╝█████╗  
  ██╔══██╗██║   ██║██║   ██║██╔═██╗ ╚════██║   ██║   ██║   ██║██╔══██╗██╔══╝  
  ██████╔╝╚██████╔╝╚██████╔╝██║  ██╗███████║   ██║   ╚██████╔╝██║  ██║███████╗
  ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚══════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝

          🏪 BOOKSTORE MANAGEMENT - WINDOWS AUTO INSTALLER 🏪
                        PowerShell Installation Script

"@ -ForegroundColor Cyan

Write-Host "========================================================================" -ForegroundColor Yellow
Write-Host "🔧 Đang chuẩn bị cài đặt môi trường phát triển cho Windows..." -ForegroundColor Green
Write-Host "========================================================================`n" -ForegroundColor Yellow

# Kiểm tra quyền Administrator
function Test-Administrator {
    $currentUser = [Security.Principal.WindowsIdentity]::GetCurrent()
    $principal = New-Object Security.Principal.WindowsPrincipal($currentUser)
    return $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

if (-not (Test-Administrator)) {
    Write-Host "❌ Script này cần quyền Administrator!" -ForegroundColor Red
    Write-Host "💡 Vui lòng:" -ForegroundColor Yellow
    Write-Host "   1. Mở PowerShell as Administrator" -ForegroundColor White
    Write-Host "   2. Chạy: Set-ExecutionPolicy RemoteSigned -Scope CurrentUser" -ForegroundColor White
    Write-Host "   3. Chạy lại script này" -ForegroundColor White
    Read-Host "Nhấn Enter để thoát"
    exit 1
}

# Kiểm tra và cài đặt Chocolatey
function Install-Chocolatey {
    Write-Host "🍫 Kiểm tra Chocolatey Package Manager..." -ForegroundColor Cyan
    
    if (Get-Command choco -ErrorAction SilentlyContinue) {
        Write-Host "✅ Chocolatey đã được cài đặt" -ForegroundColor Green
    } else {
        Write-Host "📦 Đang cài đặt Chocolatey..." -ForegroundColor Yellow
        Set-ExecutionPolicy Bypass -Scope Process -Force
        [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
        Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Chocolatey đã được cài đặt thành công" -ForegroundColor Green
        } else {
            Write-Host "❌ Lỗi cài đặt Chocolatey" -ForegroundColor Red
            return $false
        }
    }
    return $true
}

# Cài đặt Java JDK
function Install-JavaJDK {
    if ($SkipJava) {
        Write-Host "⏭️  Bỏ qua cài đặt Java (theo yêu cầu)" -ForegroundColor Yellow
        return
    }

    Write-Host "`n☕ Kiểm tra Java JDK..." -ForegroundColor Cyan
    
    try {
        $javaVersion = java -version 2>&1 | Select-String "version"
        if ($javaVersion) {
            Write-Host "✅ Java đã được cài đặt: $($javaVersion.Line)" -ForegroundColor Green
            return
        }
    } catch {
        Write-Host "📦 Java chưa được cài đặt. Đang cài đặt..." -ForegroundColor Yellow
    }
    
    Write-Host "⬇️  Đang tải và cài đặt OpenJDK 17..." -ForegroundColor Yellow
    choco install openjdk17 -y
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Java JDK đã được cài đặt thành công" -ForegroundColor Green
        Write-Host "🔄 Vui lòng khởi động lại Command Prompt để sử dụng java" -ForegroundColor Yellow
    } else {
        Write-Host "❌ Lỗi cài đặt Java JDK" -ForegroundColor Red
    }
}

# Cài đặt MySQL Server
function Install-MySQL {
    if ($SkipMySQL) {
        Write-Host "⏭️  Bỏ qua cài đặt MySQL (theo yêu cầu)" -ForegroundColor Yellow
        return
    }

    Write-Host "`n🐬 Kiểm tra MySQL Server..." -ForegroundColor Cyan
    
    $mysqlService = Get-Service -Name "MySQL80" -ErrorAction SilentlyContinue
    if ($mysqlService) {
        Write-Host "✅ MySQL Server đã được cài đặt" -ForegroundColor Green
        if ($mysqlService.Status -eq "Running") {
            Write-Host "✅ MySQL Service đang chạy" -ForegroundColor Green
        } else {
            Write-Host "⚠️  MySQL Service không chạy. Đang khởi động..." -ForegroundColor Yellow
            Start-Service -Name "MySQL80" -ErrorAction SilentlyContinue
        }
        return
    }
    
    Write-Host "📦 MySQL Server chưa được cài đặt." -ForegroundColor Yellow
    Write-Host "⬇️  Đang tải và cài đặt MySQL Server..." -ForegroundColor Yellow
    
    # Cài đặt MySQL qua Chocolatey
    choco install mysql -y
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ MySQL Server đã được cài đặt" -ForegroundColor Green
        Write-Host "🔧 Cấu hình MySQL..." -ForegroundColor Yellow
        
        # Khởi động MySQL service
        Start-Service -Name "MySQL80" -ErrorAction SilentlyContinue
        
        Write-Host "💡 Lưu ý: Bạn cần thiết lập root password cho MySQL" -ForegroundColor Yellow
        Write-Host "   Chạy: mysql_secure_installation" -ForegroundColor White
    } else {
        Write-Host "❌ Lỗi cài đặt MySQL Server" -ForegroundColor Red
        Write-Host "💡 Vui lòng tải MySQL Installer thủ công từ:" -ForegroundColor Yellow
        Write-Host "   https://dev.mysql.com/downloads/installer/" -ForegroundColor White
    }
}

# Tải MySQL JDBC Driver
function Download-MySQLJDBC {
    if ($SkipJDBC) {
        Write-Host "⏭️  Bỏ qua tải MySQL JDBC Driver (theo yêu cầu)" -ForegroundColor Yellow
        return
    }

    Write-Host "`n🔌 Kiểm tra MySQL JDBC Driver..." -ForegroundColor Cyan
    
    # Tạo thư mục lib nếu chưa có
    if (-not (Test-Path "lib")) {
        New-Item -ItemType Directory -Name "lib" | Out-Null
        Write-Host "📁 Đã tạo thư mục lib" -ForegroundColor Green
    }
    
    # Kiểm tra xem đã có JDBC driver chưa
    $existingJDBC = Get-ChildItem -Path "lib" -Filter "mysql-connector-*.jar" -ErrorAction SilentlyContinue
    if ($existingJDBC) {
        Write-Host "✅ MySQL JDBC Driver đã có sẵn: $($existingJDBC.Name)" -ForegroundColor Green
        return
    }
    
    Write-Host "⬇️  Đang tải MySQL JDBC Driver..." -ForegroundColor Yellow
    
    # URL cho MySQL Connector/J 8.0.33
    $jdbcUrl = "https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.33.zip"
    $zipFile = "lib\mysql-connector-java-8.0.33.zip"
    
    try {
        # Tải file
        Invoke-WebRequest -Uri $jdbcUrl -OutFile $zipFile -UseBasicParsing
        
        # Giải nén
        Expand-Archive -Path $zipFile -DestinationPath "lib\temp" -Force
        
        # Copy file JAR
        $jarFile = Get-ChildItem -Path "lib\temp" -Filter "*.jar" -Recurse | Select-Object -First 1
        if ($jarFile) {
            Copy-Item $jarFile.FullName -Destination "lib\" -Force
            Write-Host "✅ MySQL JDBC Driver đã được tải về: $($jarFile.Name)" -ForegroundColor Green
        }
        
        # Dọn dẹp
        Remove-Item "lib\temp" -Recurse -Force -ErrorAction SilentlyContinue
        Remove-Item $zipFile -Force -ErrorAction SilentlyContinue
        
    } catch {
        Write-Host "❌ Lỗi tải MySQL JDBC Driver: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "💡 Vui lòng tải thủ công từ:" -ForegroundColor Yellow
        Write-Host "   https://dev.mysql.com/downloads/connector/j/" -ForegroundColor White
        Write-Host "   Và đặt file .jar vào thư mục lib\" -ForegroundColor White
    }
}

# Thiết lập Database
function Setup-Database {
    Write-Host "`n🗄️  Thiết lập Database..." -ForegroundColor Cyan
    
    # Kiểm tra MySQL có chạy không
    $mysqlService = Get-Service -Name "MySQL80" -ErrorAction SilentlyContinue
    if (-not $mysqlService -or $mysqlService.Status -ne "Running") {
        Write-Host "⚠️  MySQL Service không chạy. Không thể thiết lập database tự động." -ForegroundColor Yellow
        Write-Host "💡 Vui lòng:" -ForegroundColor Yellow
        Write-Host "   1. Khởi động MySQL Service" -ForegroundColor White
        Write-Host "   2. Chạy script SQL thủ công:" -ForegroundColor White
        Write-Host "      mysql -u root -p bookstore < src\main\resources\database\bookstore.sql" -ForegroundColor White
        return
    }
    
    Write-Host "📋 Hướng dẫn thiết lập database:" -ForegroundColor Yellow
    Write-Host "   1. Mở Command Prompt" -ForegroundColor White
    Write-Host "   2. Chạy: mysql -u root -p" -ForegroundColor White
    Write-Host "   3. Tạo database: CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" -ForegroundColor White
    Write-Host "   4. Thoát: exit" -ForegroundColor White
    Write-Host "   5. Import: mysql -u root -p bookstore < `"src\main\resources\database\bookstore.sql`"" -ForegroundColor White
    Write-Host "   6. Cập nhật mật khẩu trong: src\main\resources\database.properties" -ForegroundColor White
}

# Main execution
try {
    # Bắt đầu cài đặt
    if (Install-Chocolatey) {
        Install-JavaJDK
        Install-MySQL
        Download-MySQLJDBC
        Setup-Database
        
        Write-Host "`n========================================================================" -ForegroundColor Yellow
        Write-Host "✅ HOÀN TẤT CÀI ĐẶT TỰ ĐỘNG!" -ForegroundColor Green
        Write-Host "========================================================================" -ForegroundColor Yellow
        
        Write-Host "`n🎯 Các bước tiếp theo:" -ForegroundColor Cyan
        Write-Host "   1. ✅ Khởi động lại Command Prompt" -ForegroundColor White
        Write-Host "   2. ✅ Thiết lập database bookstore (xem hướng dẫn trên)" -ForegroundColor White
        Write-Host "   3. ✅ Cập nhật database.properties với mật khẩu MySQL" -ForegroundColor White
        Write-Host "   4. ✅ Chạy: setup.bat (để kiểm tra và build)" -ForegroundColor White
        Write-Host "   5. ✅ Chạy: run.bat (để khởi động ứng dụng)" -ForegroundColor White
        
        Write-Host "`n🔑 Tài khoản demo:" -ForegroundColor Cyan
        Write-Host "   👤 admin/admin123     - Quản trị viên" -ForegroundColor White
        Write-Host "   👤 user/user123       - Người dùng" -ForegroundColor White
        Write-Host "   👤 manager/manager123 - Nhân viên" -ForegroundColor White
        
        Write-Host "`n📞 Hỗ trợ:" -ForegroundColor Cyan
        Write-Host "   📧 Nếu gặp vấn đề, hãy chạy: setup.bat" -ForegroundColor White
        Write-Host "   📧 Hoặc xem: SETUP.md" -ForegroundColor White
    }
} catch {
    Write-Host "`n❌ Lỗi trong quá trình cài đặt: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "💡 Vui lòng cài đặt thủ công theo hướng dẫn trong SETUP.md" -ForegroundColor Yellow
}

Write-Host "`n👋 Cảm ơn bạn đã sử dụng Bookstore Management Auto Installer!" -ForegroundColor Green
Read-Host "Nhấn Enter để thoát"
