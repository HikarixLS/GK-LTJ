package com.bookstore.controller;

import com.bookstore.entity.User;
import com.bookstore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    // Trang đăng nhập
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
        if (logout != null) {
            model.addAttribute("message", "Đăng xuất thành công!");
        }
        return "auth/login";
    }
    
    // Trang đăng ký
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }
    
    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user,
                              BindingResult result,
                              @RequestParam String confirmPassword,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        // Kiểm tra mật khẩu xác nhận
        if (!user.getPassword().equals(confirmPassword)) {
            result.rejectValue("password", "error.user", "Mật khẩu xác nhận không khớp");
        }
        
        // Kiểm tra username đã tồn tại
        if (userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Tên đăng nhập đã tồn tại");
        }
        
        // Kiểm tra email đã tồn tại
        if (userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email đã được sử dụng");
        }
        
        if (result.hasErrors()) {
            return "auth/register";
        }
        
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", 
                "Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi đăng ký: " + e.getMessage());
            return "auth/register";
        }
    }
}
