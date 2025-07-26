# 📚 Hệ thống Quản lý Cửa hàng Sách (Book Store Management System)

Dự án này là một ứng dụng web quản lý cửa hàng sách toàn diện được xây dựng với **Java Spring Boot**, cung cấp các chức năng quản lý sách, sinh viên, sản phẩm và cửa hàng với giao diện hiện đại và hệ thống bảo mật hoàn chỉnh.

## 🚀 Tính năng chính

### � **Quản lý Sách (Book Management)**
- ✅ **Full CRUD operations** - Thêm, sửa, xóa, xem chi tiết sách
- ✅ **Tìm kiếm nâng cao** - Theo tên sách, tác giả, ISBN, thể loại
- ✅ **Phân trang và sắp xếp** - User-friendly pagination với nhiều tiêu chí
- ✅ **Form validation** - Client & server-side validation hoàn chỉnh  
- ✅ **Real-time preview** - Preview sách khi nhập thông tin
- ✅ **Image management** - Hỗ trợ URL ảnh bìa sách
- ✅ **Category filtering** - Filter theo 16+ thể loại sách
- ✅ **Stock management** - Quản lý số lượng tồn kho
- ✅ **Rating system** - Hệ thống đánh giá 5 sao với review count
- ✅ **Statistics** - Thống kê sách theo category, status, popularity

### �👨‍🎓 **Quản lý Sinh viên**
- ✅ Thêm, sửa, xóa thông tin sinh viên
- ✅ Tìm kiếm và lọc sinh viên theo nhiều tiêu chí
- ✅ Quản lý thông tin lớp học, ngành học
- ✅ Theo dõi điểm GPA và trạng thái hoạt động
- ✅ Thống kê sinh viên theo lớp và ngành

### 🛍️ **Quản lý Sản phẩm** (Backend ready)
- ✅ Quản lý danh mục sản phẩm đầy đủ
- ✅ Theo dõi tồn kho và giá cả
- ✅ Phân loại sản phẩm theo danh mục
- ✅ Cảnh báo sản phẩm sắp hết hàng
- ✅ Thống kê giá trị tồn kho

### 🏪 **Quản lý Cửa hàng** (Backend ready)
- ✅ Thông tin chi tiết cửa hàng
- ✅ Quản lý địa chỉ và liên hệ
- ✅ Theo dõi trạng thái hoạt động
- ✅ Thống kê cửa hàng theo khu vực

### � **Hệ thống Bảo mật**
- ✅ **Authentication** - Đăng nhập/đăng xuất an toàn
- ✅ **Authorization** - 3 cấp độ quyền:
  - **USER**: Xem thông tin sách và sinh viên
  - **MANAGER**: Thêm/sửa sách (không xóa)  
  - **ADMIN**: Full quyền truy cập (bao gồm xóa)
- ✅ **Password encryption** - BCrypt hashing
- ✅ **Session management** - Remember me functionality

## 🛠️ Công nghệ sử dụng

- **Backend**: Java 17+, Spring Boot 3.2, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome 6, JavaScript
- **Database**: MySQL (chính), H2 (development/testing)  
- **Build Tool**: Maven với wrapper
- **Libraries**: Lombok, Hibernate Validator, Jackson

## 📋 Yêu cầu hệ thống

- Java 17 hoặc cao hơn
- Maven 3.6+
- SQL Server 2019+ hoặc MySQL 8.0+ (tùy chọn)
- 4GB RAM (khuyến nghị)
- 500MB dung lượng đĩa cứng

## 🚀 Cài đặt và chạy dự án

### 1. Clone repository
```bash
git clone <repository-url>
cd product-management-system
```

### 2. Cấu hình database

#### Sử dụng H2 Database (Development - Mặc định)
Không cần cấu hình thêm, ứng dụng sẽ tự động tạo database trong memory.

#### Sử dụng SQL Server
```properties
# Cập nhật src/main/resources/application.properties
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ProductManagementDB;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=yourpassword
```

#### Sử dụng MySQL
```properties
# Cập nhật src/main/resources/application.properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/product_management_db?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3. Build và chạy ứng dụng

#### Sử dụng Maven
```bash
# Build dự án
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

#### Sử dụng IDE
1. Import dự án vào IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Chạy class `ProductManagementApplication.java`

### 4. Truy cập ứng dụng
- URL: http://localhost:8080
- Console H2 (nếu sử dụng): http://localhost:8080/h2-console

## 👤 Tài khoản mặc định

| Tên đăng nhập | Mật khẩu | Quyền |
|---------------|----------|-------|
| admin | admin123 | ADMIN |
| manager | manager123 | MANAGER |
| user | user123 | USER |

## 📖 Hướng dẫn sử dụng

### Dashboard
- Xem tổng quan hệ thống
- Thống kê nhanh các module
- Truy cập nhanh các chức năng chính

### Quản lý sinh viên
1. Truy cập menu "Sinh viên"
2. Thêm sinh viên mới bằng nút "Thêm mới"
3. Tìm kiếm sinh viên theo tên, mã SV, email
4. Chỉnh sửa thông tin bằng nút "Sửa"
5. Xem thống kê trong tab "Thống kê"

### Quản lý sản phẩm
1. Truy cập menu "Sản phẩm"
2. Lọc sản phẩm theo danh mục, trạng thái
3. Theo dõi tồn kho và cập nhật giá
4. Xem báo cáo sản phẩm sắp hết hàng

### Quản lý cửa hàng
1. Truy cập menu "Cửa hàng"
2. Thêm thông tin cửa hàng mới
3. Cập nhật trạng thái hoạt động
4. Xem danh sách sản phẩm của cửa hàng

### Thư viện sách
1. Truy cập menu "Sách"
2. Tìm kiếm theo tên, tác giả, ISBN
3. Lọc theo thể loại và định dạng
4. Xem đánh giá và nhận xét

## 🔧 Cấu hình nâng cao

### Logging
```properties
# Cấu hình level log
logging.level.com.gdu.productmanagement=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

### Security
```properties
# Cấu hình session timeout (giây)
server.servlet.session.timeout=1800

# Cấu hình remember-me token validity (giây)
security.remember-me.token-validity-seconds=86400
```

### Performance
```properties
# Cấu hình JPA batch processing
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true

# Connection pool
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
```

## 📁 Cấu trúc dự án

```
src/
├── main/
│   ├── java/com/gdu/productmanagement/
│   │   ├── config/          # Cấu hình Spring
│   │   ├── controller/      # REST Controllers
│   │   ├── entity/          # JPA Entities
│   │   ├── repository/      # Data Access Layer
│   │   ├── service/         # Business Logic
│   │   └── ProductManagementApplication.java
│   └── resources/
│       ├── templates/       # Thymeleaf Templates
│       ├── static/          # CSS, JS, Images
│       └── application.properties
└── test/                    # Unit & Integration Tests
```

## 🧪 Testing

```bash
# Chạy tất cả tests
mvn test

# Chạy test với coverage
mvn test jacoco:report

# Chạy integration tests
mvn failsafe:integration-test
```

## 📊 API Documentation

Dự án sử dụng Spring Boot Actuator để monitoring:
- Health check: http://localhost:8080/actuator/health
- Application info: http://localhost:8080/actuator/info

## 🤝 Đóng góp

1. Fork repository
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📝 License

Dự án này được phát hành dưới license MIT. Xem file `LICENSE` để biết thêm chi tiết.

## 📞 Liên hệ

- Email: support@productmanagement.com
- GitHub Issues: [Tạo issue mới](issues)

## 🔄 Cập nhật gần đây

- **v1.0.0** (2025-01-25): Phiên bản đầu tiên
  - Hoàn thiện các chức năng cơ bản
  - Hệ thống authentication và authorization
  - Dashboard và báo cáo thống kê
  - Responsive design với Bootstrap 5

---

⭐ Nếu dự án này hữu ích, hãy cho chúng tôi một star trên GitHub!
