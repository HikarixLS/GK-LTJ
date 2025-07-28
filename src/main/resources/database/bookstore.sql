-- Schema cho hệ thống quản lý cửa hàng sách
-- MySQL Database

-- Tạo database
CREATE DATABASE IF NOT EXISTS bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bookstore;

-- Bảng người dùng
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    role ENUM('ADMIN', 'EMPLOYEE', 'USER') DEFAULT 'USER',
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_login DATETIME
);

-- Bảng sách
CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    category VARCHAR(100),
    price DECIMAL(10, 2) DEFAULT 0.00,
    quantity INT DEFAULT 0,
    publisher VARCHAR(255),
    publish_date DATE,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng danh mục (tùy chọn, có thể mở rộng sau)
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Chèn dữ liệu mẫu cho users
INSERT INTO users (username, password, full_name, email, phone, role, is_active) VALUES
-- Mật khẩu được hash SHA-256
('admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', 'Quản trị viên', 'admin@bookstore.com', '0123456789', 'ADMIN', TRUE),
('user', '0a041b9462caa4a31bac3567e0b6e6fd9100787db2ab433d96f6d178cabfce90', 'Người dùng', 'user@bookstore.com', '0987654321', 'USER', TRUE),
('manager', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', 'Quản lý', 'manager@bookstore.com', '0111222333', 'EMPLOYEE', TRUE);

-- Chèn dữ liệu mẫu cho categories
INSERT INTO categories (category_name, description) VALUES
('Văn học', 'Sách văn học trong nước và nước ngoài'),
('Khoa học', 'Sách khoa học tự nhiên và ứng dụng'),
('Công nghệ', 'Sách về công nghệ thông tin và kỹ thuật'),
('Kinh tế', 'Sách về kinh tế, tài chính, quản trị'),
('Lịch sử', 'Sách lịch sử Việt Nam và thế giới'),
('Tâm lý', 'Sách tâm lý học và phát triển bản thân'),
('Giáo dục', 'Sách giáo khoa và tài liệu học tập'),
('Thiếu nhi', 'Sách dành cho trẻ em và thiếu niên'),
('Khác', 'Các loại sách khác');

-- Chèn dữ liệu mẫu cho books
INSERT INTO books (title, author, isbn, category, price, quantity, publisher, publish_date, description) VALUES
('Đắc Nhân Tâm', 'Dale Carnegie', '9786041033399', 'Tâm lý', 86000, 50, 'NXB Tổng Hợp TP.HCM', '2020-01-15', 'Cuốn sách kinh điển về nghệ thuật giao tiếp và ứng xử'),
('Sapiens: Lược sử loài người', 'Yuval Noah Harari', '9786041039223', 'Lịch sử', 200000, 30, 'NXB Tri Thức', '2019-05-20', 'Tác phẩm nghiên cứu về lịch sử tiến hóa của loài người'),
('Clean Code', 'Robert C. Martin', '9780132350884', 'Công nghệ', 450000, 25, 'Prentice Hall', '2018-03-10', 'Hướng dẫn viết code sạch và chuyên nghiệp'),
('Tôi thấy hoa vàng trên cỏ xanh', 'Nguyễn Nhật Ánh', '9786041019782', 'Văn học', 120000, 40, 'NXB Trẻ', '2017-09-12', 'Tiểu thuyết về tuổi thơ miền quê Việt Nam'),
('Java: The Complete Reference', 'Herbert Schildt', '9781260440232', 'Công nghệ', 550000, 20, 'McGraw-Hill', '2021-06-08', 'Tài liệu tham khảo đầy đủ về ngôn ngữ Java'),
('Kinh tế học vĩ mô', 'N.Gregory Mankiw', '9786041136547', 'Kinh tế', 350000, 35, 'NXB Kinh Tế', '2020-11-25', 'Giáo trình kinh tế học vĩ mô cơ bản'),
('Lịch sử Việt Nam', 'Trần Trọng Kim', '9786041033092', 'Lịch sử', 180000, 45, 'NXB Văn Học', '2019-08-18', 'Tác phẩm nghiên cứu lịch sử Việt Nam'),
('Dạy con làm giàu', 'Robert Kiyosaki', '9786041033115', 'Kinh tế', 150000, 60, 'NXB Lao Động', '2020-02-14', 'Hướng dẫn giáo dục tài chính cho trẻ em'),
('Doraemon - Tập 1', 'Fujiko F. Fujio', '9786041033139', 'Thiếu nhi', 25000, 100, 'NXB Kim Đồng', '2021-01-05', 'Truyện tranh nổi tiếng dành cho thiếu nhi'),
('Ngữ văn 12', 'Bộ Giáo Dục', '9786041033146', 'Giáo dục', 45000, 200, 'NXB Giáo Dục', '2021-07-01', 'Sách giáo khoa Ngữ văn lớp 12');

-- Tạo index để tối ưu hóa tìm kiếm
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author ON books(author);
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_books_category ON books(category);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);

-- Tạo view thống kê (tùy chọn)
CREATE VIEW book_stats AS
SELECT 
    category,
    COUNT(*) as total_books,
    SUM(quantity) as total_quantity,
    AVG(price) as avg_price,
    MIN(price) as min_price,
    MAX(price) as max_price
FROM books 
GROUP BY category;

-- Hiển thị thông tin đã tạo
SELECT 'Database setup completed successfully!' as message;
SELECT COUNT(*) as total_users FROM users;
SELECT COUNT(*) as total_books FROM books;
SELECT COUNT(*) as total_categories FROM categories;
