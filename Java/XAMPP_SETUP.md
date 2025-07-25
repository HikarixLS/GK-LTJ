# 🚀 HƯỚNG DẪN CHẠY VỚI XAMPP MYSQL

## 📋 **BƯỚC 1: CÀI ĐẶT VÀ KHỞI ĐỘNG XAMPP**

1. **Tải XAMPP**: https://www.apachefriends.org/download.html
2. **Cài đặt XAMPP** với các component:
   - ✅ Apache
   - ✅ MySQL
   - ✅ phpMyAdmin
3. **Mở XAMPP Control Panel**
4. **Start Apache và MySQL**

## 🗄️ **BƯỚC 2: TẠO DATABASE**

### **Cách 1: Tự động (Khuyến nghị)**
```cmd
cd "e:\File App\GDU files\Java"
setup_xampp.bat
```

### **Cách 2: Thủ công qua phpMyAdmin**
1. Mở trình duyệt: `http://localhost/phpmyadmin`
2. Click **"New"** bên trái
3. Tên database: `product_management_db`
4. Collation: `utf8mb4_unicode_ci`
5. Click **"Create"**

### **Cách 3: Import SQL file**
1. Mở phpMyAdmin
2. Chọn database `product_management_db`
3. Click tab **"Import"**
4. Chọn file: `database/create_database.sql`
5. Click **"Go"**

## ⚙️ **BƯỚC 3: CẤU HÌNH ĐÃ SẴN SÀNG**

File `application.properties` đã được cấu hình:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/product_management_db
spring.datasource.username=root
spring.datasource.password=
```

## 🚀 **BƯỚC 4: CHẠY ỨNG DỤNG**

```cmd
cd "e:\File App\GDU files\Java"
start.bat
```

## 🌐 **TRUY CẬP ỨNG DỤNG**

- **Ứng dụng**: http://localhost:8080
- **phpMyAdmin**: http://localhost/phpmyadmin
- **XAMPP Dashboard**: http://localhost

## 🔐 **ĐĂNG NHẬP**

- **Admin**: `admin` / `admin123`
- **Manager**: `manager` / `manager123`
- **User**: `user` / `user123`

## 🛠️ **TROUBLESHOOTING**

### **Lỗi kết nối database:**
1. Kiểm tra XAMPP MySQL đang chạy
2. Kiểm tra database `product_management_db` đã tồn tại
3. Kiểm tra port 3306 không bị chiếm

### **Lỗi port 8080:**
- Thêm vào `application.properties`: `server.port=8081`

### **Lỗi build:**
- Chạy: `mvn clean install -DskipTests`

## 📊 **TÍNH NĂNG**

- ✅ Quản lý sinh viên
- ✅ Quản lý sản phẩm  
- ✅ Quản lý cửa hàng
- ✅ Quản lý thư viện sách
- ✅ Xác thực và phân quyền
- ✅ Database MySQL persistent
