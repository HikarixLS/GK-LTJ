package com.bookstore.config;

import com.bookstore.entity.Role;
import com.bookstore.entity.RoleName;
import com.bookstore.entity.User;
import com.bookstore.repository.RoleRepository;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        // Tạo roles nếu chưa tồn tại
        if (roleRepository.findByName(RoleName.ROLE_USER).isEmpty()) {
            Role userRole = new Role(RoleName.ROLE_USER, "Người dùng thông thường");
            roleRepository.save(userRole);
        }
        
        if (roleRepository.findByName(RoleName.ROLE_ADMIN).isEmpty()) {
            Role adminRole = new Role(RoleName.ROLE_ADMIN, "Quản trị viên hệ thống");
            roleRepository.save(adminRole);
        }
        
        // Tạo admin user mặc định nếu chưa tồn tại
        if (!userService.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setEmail("admin@bookstore.com");
            admin.setFullName("Quản trị viên");
            admin.setPhoneNumber("0123456789");
            admin.setAddress("Hà Nội");
            admin.setEnabled(true);
            
            userService.createAdminUser(admin);
            System.out.println("Đã tạo admin user - Username: admin, Password: admin123");
        }
        
        // Tạo user thông thường mặc định nếu chưa tồn tại
        if (!userService.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("user123");
            user.setEmail("user@bookstore.com");
            user.setFullName("Người dùng");
            user.setPhoneNumber("0987654321");
            user.setAddress("TP.HCM");
            user.setEnabled(true);
            
            userService.registerUser(user);
            System.out.println("Đã tạo user thông thường - Username: user, Password: user123");
        }
    }
}
