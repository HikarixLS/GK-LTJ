package com.bookstore.service;

import com.bookstore.entity.Role;
import com.bookstore.entity.RoleName;
import com.bookstore.entity.User;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // UserDetailsService implementation
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user: " + username));
        
        return UserPrincipal.create(user);
    }
    
    // Lấy tất cả user
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    // Tìm user theo ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    // Tìm user theo username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // Tìm user theo email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // Đăng ký user mới
    public User registerUser(User user) {
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Gán role USER mặc định
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role USER không tồn tại"));
        user.addRole(userRole);
        
        return userRepository.save(user);
    }
    
    // Tạo admin user
    public User createAdminUser(User user) {
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Gán role ADMIN
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Role ADMIN không tồn tại"));
        user.addRole(adminRole);
        
        return userRepository.save(user);
    }
    
    // Cập nhật user
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    // Xóa user
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    
    // Kiểm tra username đã tồn tại
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    // Kiểm tra email đã tồn tại
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    // Thay đổi mật khẩu
    public void changePassword(Long userId, String newPassword) {
        User user = findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        user.setPassword(passwordEncoder.encode(newPassword));
        updateUser(user);
    }
    
    // Kích hoạt/vô hiệu hóa user
    public void toggleUserStatus(Long userId) {
        User user = findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        user.setEnabled(!user.getEnabled());
        updateUser(user);
    }
    
    // Gán role cho user
    public void assignRole(Long userId, RoleName roleName) {
        User user = findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));
        user.addRole(role);
        updateUser(user);
    }
    
    // Xóa role khỏi user
    public void removeRole(Long userId, RoleName roleName) {
        User user = findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));
        user.removeRole(role);
        updateUser(user);
    }
    
    // Đếm tổng số user
    public long countAll() {
        return userRepository.count();
    }
}
