package com.bookstore.controller;

import com.bookstore.entity.User;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    // Dashboard admin
    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", userService.countAll());
        return "admin/dashboard";
    }
    
    // Quản lý người dùng
    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }
    
    // Xem chi tiết user
    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "admin/user-detail";
        }
        return "redirect:/admin/users?error=notfound";
    }
    
    // Toggle trạng thái user (kích hoạt/vô hiệu hóa)
    @PostMapping("/users/{id}/toggle")
    public String toggleUserStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.toggleUserStatus(id);
            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái user thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    // Xóa user
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<User> user = userService.findById(id);
            if (user.isPresent()) {
                // Không cho phép xóa admin
                if (user.get().getUsername().equals("admin")) {
                    redirectAttributes.addFlashAttribute("error", "Không thể xóa tài khoản admin!");
                } else {
                    userService.deleteById(id);
                    redirectAttributes.addFlashAttribute("success", "Xóa user thành công!");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy user!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }
}
