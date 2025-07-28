# 📚 Hệ thống Quản lý Cửa hàng Sách

Dự án Spring Boot để xây dựng hệ thống quản lý cửa hàng sách với đầy đủ các chức năng CRUD, authentication và authorization.

## 🚀 Tính năng chính

### 📖 Quản lý Sách (CRUD)
- **Hiển thị** danh sách sách với phân trang
- **Thêm** sách mới (Admin only)
- **Sửa** thông tin sách (Admin only)  
- **Xóa** sách (Admin only)
- **Tìm kiếm** sách theo nhiều tiêu chí
- **Xem chi tiết** sách

### 👥 Quản lý Người dùng
- **Đăng ký** tài khoản mới
- **Đăng nhập/Đăng xuất**
- **Phân quyền** User/Admin
- **Quản lý** danh sách người dùng (Admin only)

### 🔍 Tìm kiếm & Lọc
- Tìm kiếm theo tên sách, tác giả, nhà xuất bản
- Lọc theo thể loại
- Tìm kiếm nâng cao

### 🔐 Bảo mật
- Authentication với Spring Security
- Authorization dựa trên roles
- Mã hóa mật khẩu với BCrypt
- Session management

## 🛠️ Công nghệ sử dụng

- **Framework**: Spring Boot 3.2.0
- **Database**: H2 (development), MySQL (production)  
- **ORM**: Spring Data JPA
- **Security**: Spring Security 6
- **Template Engine**: Thymeleaf
- **Frontend**: Bootstrap 5, Bootstrap Icons
- **Build Tool**: Maven
- **Java Version**: 17

## 📁 Cấu trúc Project

```
src/
├── main/
│   ├── java/com/bookstore/
│   │   ├── config/               # Cấu hình ứng dụng
│   │   │   ├── SecurityConfig.java
│   │   │   └── DataInitializer.java
│   │   ├── controller/           # REST Controllers
│   │   │   ├── HomeController.java
│   │   │   ├── BookController.java
│   │   │   ├── AuthController.java
│   │   │   └── AdminController.java
│   │   ├── entity/               # JPA Entities
│   │   │   ├── Book.java
│   │   │   ├── User.java
│   │   │   ├── Role.java
│   │   │   └── RoleName.java
│   │   ├── repository/           # Data Access Layer
│   │   │   ├── BookRepository.java
│   │   │   ├── UserRepository.java
│   │   │   └── RoleRepository.java
│   │   ├── service/              # Business Logic
│   │   │   ├── BookService.java
│   │   │   ├── UserService.java
│   │   │   └── UserPrincipal.java
│   │   └── BookstoreApplication.java
│   └── resources/
│       ├── templates/            # Thymeleaf Templates
│       │   ├── home.html
│       │   ├── layout.html
│       │   ├── auth/
│       │   │   ├── login.html
│       │   │   └── register.html
│       │   └── books/
│       │       ├── list.html
│       │       ├── add.html
│       │       ├── edit.html
│       │       └── view.html
│       └── application.properties
└── pom.xml
```

## 🏃‍♂️ Cách chạy dự án

### Yêu cầu hệ thống
- Java 17 hoặc cao hơn (đã có Java 24 ✅)
- Maven 3.6+ hoặc sử dụng Maven Wrapper
- IDE: VS Code, IntelliJ IDEA, Eclipse

### Bước 1: Cài đặt Maven (nếu chưa có)

#### Option 1: Sử dụng Maven Wrapper (Khuyến nghị)
Dự án đã bao gồm Maven Wrapper, bạn có thể chạy trực tiếp:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### Option 2: Cài đặt Maven
1. Download Maven từ: https://maven.apache.org/download.cgi
2. Giải nén và thêm `bin` folder vào PATH
3. Kiểm tra: `mvn -version`

### Bước 2: Chạy ứng dụng

#### Cách 1: Sử dụng Maven Wrapper (Khuyến nghị)
```bash
# Chạy trực tiếp
.\mvnw.cmd spring-boot:run

# Hoặc build và chạy jar
.\mvnw.cmd clean package
java -jar target/bookstore-management-0.0.1-SNAPSHOT.jar
```

#### Cách 2: Sử dụng VS Code Task
1. Mở VS Code
2. Nhấn `Ctrl+Shift+P`
3. Gõ "Tasks: Run Task"
4. Chọn "Build and Run Spring Boot Application"

#### Cách 3: Nếu đã cài Maven
```bash
# Sử dụng Maven
mvn spring-boot:run

# Hoặc build và chạy jar
mvn clean package
java -jar target/bookstore-management-0.0.1-SNAPSHOT.jar
```

### Bước 3: Truy cập ứng dụng
- **URL**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:bookstore`
  - Username: `sa`
  - Password: `password`

## 👤 Tài khoản mặc định

Hệ thống sẽ tự động tạo các tài khoản sau khi khởi động:

### Admin
- **Username**: `admin`
- **Password**: `admin123`
- **Email**: `admin@bookstore.com`
- **Quyền**: Toàn quyền quản trị

### User thường
- **Username**: `user`  
- **Password**: `user123`
- **Email**: `user@bookstore.com`
- **Quyền**: Chỉ xem thông tin

## 🔧 Cấu hình Database

### H2 Database (Mặc định - Development)
```properties
spring.datasource.url=jdbc:h2:mem:bookstore
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
```

### MySQL (Production)
Uncomment và cấu hình trong `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookstore
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## 📋 API Endpoints

### Public Endpoints
- `GET /` - Trang chủ
- `GET /books` - Danh sách sách
- `GET /books/view/{id}` - Chi tiết sách
- `GET /books/search` - Tìm kiếm sách
- `GET /login` - Trang đăng nhập
- `GET /register` - Trang đăng ký

### Admin Endpoints
- `GET /admin` - Dashboard admin
- `GET /admin/users` - Quản lý users
- `GET /books/add` - Form thêm sách
- `POST /books/add` - Thêm sách mới
- `GET /books/edit/{id}` - Form sửa sách
- `POST /books/edit/{id}` - Cập nhật sách
- `POST /books/delete/{id}` - Xóa sách

## 🎯 Chức năng đã thực hiện

### ✅ Phân tích & Thiết kế
- [x] Thiết kế Entity relationships
- [x] Thiết kế Database schema
- [x] Thiết kế kiến trúc hệ thống (3-layer)

### ✅ Framework Spring Boot
- [x] Cấu hình Spring Boot project
- [x] Integration với Spring Data JPA
- [x] Integration với Spring Security
- [x] Integration với Thymeleaf

### ✅ CRUD Operations
- [x] **Create**: Thêm sách mới (Admin only)
- [x] **Read**: Hiển thị danh sách và chi tiết sách
- [x] **Update**: Sửa thông tin sách (Admin only)  
- [x] **Delete**: Xóa sách (Admin only)

### ✅ Database Connection
- [x] Kết nối H2 Database (Development)
- [x] Hỗ trợ MySQL (Production)
- [x] JPA/Hibernate configuration
- [x] Database initialization

### ✅ Authentication & Authorization
- [x] User registration/login
- [x] Role-based access control
- [x] Session management
- [x] Password encryption

## 🔄 Tính năng bổ sung

### 🔍 Tìm kiếm nâng cao
- Tìm kiếm theo multiple criteria
- Auto-complete cho thể loại và NXB
- Filter và sort results

### 📊 Dashboard & Statistics  
- Thống kê số lượng sách, users
- Báo cáo theo thể loại
- Admin dashboard

### 🎨 UI/UX
- Responsive design với Bootstrap 5
- Modern và user-friendly interface
- Interactive components
- Flash messages

## 🤝 Hướng phát triển

- [ ] Shopping cart functionality
- [ ] Order management
- [ ] Payment integration
- [ ] Email notifications
- [ ] File upload cho book images
- [ ] REST API for mobile app
- [ ] Advanced reporting
- [ ] Multi-language support

## 📝 Ghi chú

- Dự án được phát triển cho mục đích học tập và demo
- Code được comment chi tiết bằng tiếng Việt
- Follow các best practices của Spring Boot
- Responsive design tương thích mobile

## 🐛 Troubleshooting

### Lỗi port đã được sử dụng
```bash
# Thay đổi port trong application.properties
server.port=8081
```

### Lỗi Java version
```bash
# Kiểm tra Java version
java -version
# Cần Java 17+
```

### Lỗi database connection
```bash
# Kiểm tra H2 console: http://localhost:8080/h2-console
# Hoặc check application.properties
```

---

**Developed with ❤️ using Spring Boot & Thymeleaf**
