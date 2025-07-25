package com.gdu.productmanagement.service;

import com.gdu.productmanagement.entity.User;
import com.gdu.productmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        log.info("Creating new user with username: {}", user.getUsername());

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại: " + user.getUsername());
        }

        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại: " + user.getEmail());
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        log.info("Updating user with id: {}", id);

        User user = findById(id);

        // Check if username is being changed and if new username already exists
        if (!user.getUsername().equals(userDetails.getUsername()) &&
                userRepository.existsByUsername(userDetails.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại: " + userDetails.getUsername());
        }

        // Check if email is being changed and if new email already exists
        if (userDetails.getEmail() != null &&
                !userDetails.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(userDetails.getEmail())) {
            throw new RuntimeException("Email đã tồn tại: " + userDetails.getEmail());
        }

        // Update fields (except password)
        user.setUsername(userDetails.getUsername());
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setRole(userDetails.getRole());
        user.setIsEnabled(userDetails.getIsEnabled());
        user.setIsAccountNonExpired(userDetails.getIsAccountNonExpired());
        user.setIsAccountNonLocked(userDetails.getIsAccountNonLocked());
        user.setIsCredentialsNonExpired(userDetails.getIsCredentialsNonExpired());

        return userRepository.save(user);
    }

    public void changePassword(Long id, String currentPassword, String newPassword) {
        log.info("Changing password for user id: {}", id);

        User user = findById(id);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu hiện tại không đúng");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void resetPassword(Long id, String newPassword) {
        log.info("Resetting password for user id: {}", id);

        User user = findById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = findById(id);
        userRepository.delete(user);
    }

    public void enableUser(Long id) {
        log.info("Enabling user with id: {}", id);
        User user = findById(id);
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public void disableUser(Long id) {
        log.info("Disabling user with id: {}", id);
        User user = findById(id);
        user.setIsEnabled(false);
        userRepository.save(user);
    }

    public void updateLastLogin(String username) {
        log.debug("Updating last login for user: {}", username);
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        });
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<User> searchUsers(String searchTerm, User.UserRole role, Boolean isEnabled, Pageable pageable) {
        return userRepository.findBySearchCriteria(searchTerm, role, isEnabled, pageable);
    }

    @Transactional(readOnly = true)
    public List<User> findByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }

    @Transactional(readOnly = true)
    public List<User> findByFullName(String fullName) {
        return userRepository.findByFullNameContainingIgnoreCase(fullName);
    }

    @Transactional(readOnly = true)
    public List<User> findEnabledUsers() {
        return userRepository.findByIsEnabled(true);
    }

    @Transactional(readOnly = true)
    public Long countActiveUsers() {
        return userRepository.countActiveUsers();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getUserStatsByRole() {
        return userRepository.countUsersByRole();
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
