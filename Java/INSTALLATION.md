# Hướng dẫn cài đặt và chạy dự án

## Yêu cầu hệ thống

### 1. Java Development Kit (JDK) 17+
- **Windows**: Tải từ [Adoptium](https://adoptium.net/) hoặc [Oracle](https://www.oracle.com/java/technologies/downloads/)
- **Kiểm tra**: Mở Command Prompt và chạy `java -version`
- **Cài đặt biến môi trường**:
  - Thêm `JAVA_HOME` trỏ đến thư mục cài đặt JDK
  - Thêm `%JAVA_HOME%\bin` vào PATH

### 2. Apache Maven 3.6+
- **Windows**: Tải từ [Maven](https://maven.apache.org/download.cgi)
- **Kiểm tra**: Mở Command Prompt và chạy `mvn -version`
- **Cài đặt biến môi trường**:
  - Thêm `MAVEN_HOME` trỏ đến thư mục cài đặt Maven
  - Thêm `%MAVEN_HOME%\bin` vào PATH

### 3. Database (Tùy chọn)
- **SQL Server**: Cho production
- **MySQL**: Alternative cho production  
- **H2**: Đã được cấu hình sẵn cho development (không cần cài thêm)

## Cách chạy dự án

### Cách 1: Sử dụng script tự động (Khuyến nghị)
```bash
# Windows
start.bat

# Linux/Mac
chmod +x start.sh
./start.sh
```

### Cách 2: Sử dụng Maven thủ công
```bash
# Build dự án
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

### Cách 3: Sử dụng IDE
1. Import dự án vào IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Chờ IDE tải dependencies
3. Chạy class `ProductManagementApplication.java`

## Truy cập ứng dụng

Sau khi ứng dụng khởi động thành công:
- **URL chính**: http://localhost:8080
- **H2 Console** (nếu dùng H2): http://localhost:8080/h2-console

## Tài khoản mặc định

| Username | Password | Role |
|----------|----------|------|
| admin    | admin123 | ADMIN |
| manager  | manager123 | MANAGER |
| user     | user123  | USER |

## Cấu hình Database

### Sử dụng H2 (Mặc định)
Không cần cấu hình gì thêm. Database sẽ được tạo tự động trong memory.

### Chuyển sang SQL Server
1. Mở file `src/main/resources/application.properties`
2. Comment các dòng H2, uncomment các dòng SQL Server
3. Cập nhật connection string, username, password

### Chuyển sang MySQL
1. Mở file `src/main/resources/application.properties`
2. Comment các dòng H2, uncomment các dòng MySQL
3. Cập nhật connection string, username, password

## Xử lý lỗi thường gặp

### 1. "java is not recognized"
- Cài đặt JDK và thiết lập biến môi trường JAVA_HOME
- Thêm Java vào PATH

### 2. "mvn is not recognized"
- Cài đặt Maven và thiết lập biến môi trường MAVEN_HOME
- Thêm Maven vào PATH

### 3. Port 8080 đã được sử dụng
- Đổi port trong `application.properties`: `server.port=8081`
- Hoặc tắt ứng dụng đang chạy trên port 8080

### 4. Database connection error
- Kiểm tra database server đã chạy chưa
- Kiểm tra username/password trong `application.properties`
- Tạo database nếu chưa tồn tại

### 5. Build failed - compilation errors
- Kiểm tra Java version >= 17
- Xóa thư mục `target` và build lại
- Kiểm tra internet connection để tải dependencies

## IDE Setup

### Visual Studio Code
1. Cài extension "Java Extension Pack"
2. Cài extension "Spring Boot Extension Pack"
3. Mở thư mục dự án trong VS Code

### IntelliJ IDEA
1. File -> Open -> Chọn thư mục dự án
2. Chờ IDEA import Maven project
3. Chạy từ class ProductManagementApplication

### Eclipse
1. File -> Import -> Existing Maven Projects
2. Chọn thư mục dự án
3. Chờ Eclipse build workspace

## Development Tips

### Hot Reload
Dự án đã cấu hình Spring Boot DevTools cho hot reload:
- Thay đổi code Java -> Tự động restart
- Thay đổi template/static files -> Tự động reload

### Debugging
- IntelliJ: Click vào dòng code để set breakpoint, chạy Debug mode
- VS Code: Sử dụng Java Debug configuration
- Eclipse: Right-click project -> Debug As -> Java Application

### Database Console (H2)
Khi dùng H2, truy cập http://localhost:8080/h2-console:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (để trống)

## Cấu trúc thư mục quan trọng

```
src/main/
├── java/com/gdu/productmanagement/
│   ├── entity/          # Database entities
│   ├── repository/      # Data access
│   ├── service/         # Business logic
│   ├── controller/      # Web controllers
│   └── config/          # Configuration
└── resources/
    ├── templates/       # Thymeleaf templates
    ├── static/          # CSS, JS, images
    └── application.properties
```

## Liên hệ hỗ trợ

Nếu gặp vấn đề, vui lòng:
1. Kiểm tra file README.md
2. Xem log errors trong console
3. Tạo issue trên repository
