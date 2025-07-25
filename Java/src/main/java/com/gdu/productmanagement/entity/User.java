package com.gdu.productmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải từ 3-50 ký tự")
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Họ tên không được để trống")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Email(message = "Email không hợp lệ")
    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải từ 10-11 chữ số")
    @Column(name = "phone", length = 11)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.USER;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @Column(name = "is_account_non_expired")
    private Boolean isAccountNonExpired = true;

    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked = true;

    @Column(name = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired = true;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Spring Security UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired != null ? isAccountNonExpired : true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked != null ? isAccountNonLocked : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired != null ? isCredentialsNonExpired : true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled != null ? isEnabled : true;
    }

    public enum UserRole {
        ADMIN("Quản trị viên"),
        MANAGER("Quản lý"),
        USER("Người dùng"),
        STUDENT("Sinh viên");

        private final String displayName;

        UserRole(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
