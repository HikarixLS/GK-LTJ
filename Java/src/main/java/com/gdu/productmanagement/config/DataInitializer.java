package com.gdu.productmanagement.config;

import com.gdu.productmanagement.entity.*;
import com.gdu.productmanagement.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Initializing sample data...");
            initializeUsers();
            initializeStores();
            initializeStudents();
            initializeProducts();
            initializeBooks();
            log.info("Sample data initialization completed!");
        } else {
            log.info("Data already exists, skipping initialization.");
        }
    }

    private void initializeUsers() {
        // Admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("Quản trị viên hệ thống");
        admin.setEmail("admin@productmanagement.com");
        admin.setPhone("0901234567");
        admin.setRole(User.UserRole.ADMIN);
        userRepository.save(admin);

        // Manager user
        User manager = new User();
        manager.setUsername("manager");
        manager.setPassword(passwordEncoder.encode("manager123"));
        manager.setFullName("Nguyễn Văn Quản lý");
        manager.setEmail("manager@productmanagement.com");
        manager.setPhone("0901234568");
        manager.setRole(User.UserRole.MANAGER);
        userRepository.save(manager);

        // Regular user
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setFullName("Trần Thị Người dùng");
        user.setEmail("user@productmanagement.com");
        user.setPhone("0901234569");
        user.setRole(User.UserRole.USER);
        userRepository.save(user);

        log.info("Created {} users", userRepository.count());
    }

    private void initializeStores() {
        Store store1 = new Store();
        store1.setStoreCode("ST001");
        store1.setStoreName("Cửa hàng điện tử ABC");
        store1.setDescription("Chuyên bán các sản phẩm điện tử, máy tính, điện thoại");
        store1.setAddress("123 Nguyễn Văn Linh, Q.7, TP.HCM");
        store1.setPhone("0283456789");
        store1.setEmail("abc@electronics.com");
        store1.setWebsite("https://abc-electronics.com");
        store1.setManagerName("Nguyễn Văn A");
        store1.setOpeningHours("8:00 - 22:00");
        store1.setLicenseNumber("LIC001");
        storeRepository.save(store1);

        Store store2 = new Store();
        store2.setStoreCode("ST002");
        store2.setStoreName("Nhà sách Tri Thức");
        store2.setDescription("Chuyên kinh doanh sách giáo khoa, tham khảo và văn học");
        store2.setAddress("456 Lê Văn Việt, Q.9, TP.HCM");
        store2.setPhone("0283456790");
        store2.setEmail("info@trithuc.com");
        store2.setWebsite("https://trithuc.com");
        store2.setManagerName("Trần Thị B");
        store2.setOpeningHours("7:00 - 21:00");
        store2.setLicenseNumber("LIC002");
        storeRepository.save(store2);

        log.info("Created {} stores", storeRepository.count());
    }

    private void initializeStudents() {
        Student student1 = new Student();
        student1.setStudentCode("SV001");
        student1.setFullName("Nguyễn Văn An");
        student1.setEmail("an.nguyen@student.edu.vn");
        student1.setPhone("0912345678");
        student1.setAddress("123 Lê Lợi, Q.1, TP.HCM");
        student1.setGender(Student.Gender.MALE);
        student1.setBirthDate(LocalDate.of(2002, 5, 15));
        student1.setClassName("CNTT2022A");
        student1.setMajor("Công nghệ thông tin");
        student1.setGpa(3.75);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setStudentCode("SV002");
        student2.setFullName("Trần Thị Bình");
        student2.setEmail("binh.tran@student.edu.vn");
        student2.setPhone("0912345679");
        student2.setAddress("456 Nguyễn Huệ, Q.1, TP.HCM");
        student2.setGender(Student.Gender.FEMALE);
        student2.setBirthDate(LocalDate.of(2002, 8, 20));
        student2.setClassName("KT2022B");
        student2.setMajor("Kinh tế");
        student2.setGpa(3.85);
        studentRepository.save(student2);

        Student student3 = new Student();
        student3.setStudentCode("SV003");
        student3.setFullName("Lê Văn Cường");
        student3.setEmail("cuong.le@student.edu.vn");
        student3.setPhone("0912345680");
        student3.setAddress("789 Điện Biên Phủ, Q.3, TP.HCM");
        student3.setGender(Student.Gender.MALE);
        student3.setBirthDate(LocalDate.of(2001, 12, 10));
        student3.setClassName("CNTT2021A");
        student3.setMajor("Công nghệ thông tin");
        student3.setGpa(3.90);
        studentRepository.save(student3);

        log.info("Created {} students", studentRepository.count());
    }

    private void initializeProducts() {
        Store store1 = storeRepository.findByStoreCode("ST001").orElse(null);

        Product product1 = new Product();
        product1.setProductCode("PRD001");
        product1.setProductName("Laptop Dell Inspiron 15 3000");
        product1.setDescription("Laptop văn phòng, cấu hình ổn định, phù hợp học tập và làm việc");
        product1.setPrice(new BigDecimal("15000000"));
        product1.setStockQuantity(50);
        product1.setCategory(Product.ProductCategory.ELECTRONICS);
        product1.setBrand("Dell");
        product1.setManufacturer("Dell Technologies");
        product1.setWarrantyPeriod(24);
        product1.setStore(store1);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setProductCode("PRD002");
        product2.setProductName("iPhone 14 Pro Max 256GB");
        product2.setDescription("Điện thoại thông minh cao cấp với camera tiên tiến");
        product2.setPrice(new BigDecimal("32000000"));
        product2.setStockQuantity(25);
        product2.setCategory(Product.ProductCategory.ELECTRONICS);
        product2.setBrand("Apple");
        product2.setManufacturer("Apple Inc.");
        product2.setWarrantyPeriod(12);
        product2.setStore(store1);
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setProductCode("PRD003");
        product3.setProductName("Áo thun nam basic");
        product3.setDescription("Áo thun cotton 100%, form regular, nhiều màu");
        product3.setPrice(new BigDecimal("200000"));
        product3.setStockQuantity(100);
        product3.setCategory(Product.ProductCategory.CLOTHING);
        product3.setBrand("Local Brand");
        product3.setStore(store1);
        productRepository.save(product3);

        log.info("Created {} products", productRepository.count());
    }

    private void initializeBooks() {
        Store store2 = storeRepository.findByStoreCode("ST002").orElse(null);

        Book book1 = new Book();
        book1.setIsbn("9780134685991");
        book1.setTitle("Effective Java");
        book1.setAuthor("Joshua Bloch");
        book1.setPublisher("Addison-Wesley Professional");
        book1.setPublicationDate(LocalDate.of(2018, 1, 6));
        book1.setDescription("The definitive guide to Java programming language best practices");
        book1.setPrice(new BigDecimal("850000"));
        book1.setStockQuantity(30);
        book1.setPages(412);
        book1.setLanguage("English");
        book1.setCategory(Book.BookCategory.TECHNOLOGY);
        book1.setFormat(Book.BookFormat.PAPERBACK);
        book1.setRating(new BigDecimal("4.8"));
        book1.setReviewCount(1250);
        book1.setStore(store2);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setIsbn("9786043208726");
        book2.setTitle("Clean Code: Nghệ thuật viết code sạch");
        book2.setAuthor("Robert C. Martin");
        book2.setPublisher("NXB Dân Trí");
        book2.setPublicationDate(LocalDate.of(2020, 5, 15));
        book2.setDescription("Hướng dẫn viết code sạch, dễ đọc và bảo trì");
        book2.setPrice(new BigDecimal("320000"));
        book2.setStockQuantity(45);
        book2.setPages(464);
        book2.setLanguage("Vietnamese");
        book2.setCategory(Book.BookCategory.TECHNOLOGY);
        book2.setFormat(Book.BookFormat.PAPERBACK);
        book2.setRating(new BigDecimal("4.6"));
        book2.setReviewCount(890);
        book2.setStore(store2);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setIsbn("9786041141230");
        book3.setTitle("Đắc Nhân Tâm");
        book3.setAuthor("Dale Carnegie");
        book3.setPublisher("NXB Tổng Hợp TP.HCM");
        book3.setPublicationDate(LocalDate.of(2019, 3, 10));
        book3.setDescription("Nghệ thuật thu phục lòng người và giao tiếp hiệu quả");
        book3.setPrice(new BigDecimal("120000"));
        book3.setStockQuantity(80);
        book3.setPages(320);
        book3.setLanguage("Vietnamese");
        book3.setCategory(Book.BookCategory.SELF_HELP);
        book3.setFormat(Book.BookFormat.PAPERBACK);
        book3.setRating(new BigDecimal("4.5"));
        book3.setReviewCount(2340);
        book3.setStore(store2);
        bookRepository.save(book3);

        log.info("Created {} books", bookRepository.count());
    }
}
