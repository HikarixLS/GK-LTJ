package com.gdu.productmanagement.controller;

import com.gdu.productmanagement.service.BookService;
import com.gdu.productmanagement.service.StudentService;
import com.gdu.productmanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final StudentService studentService;
    private final ProductService productService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("authorities", authentication.getAuthorities());
        }
        
        // Thêm thống kê
        try {
            model.addAttribute("totalBooks", bookService.countAllBooks());
            model.addAttribute("availableBooks", bookService.countAvailableBooks());
            model.addAttribute("borrowedBooks", bookService.countBorrowedBooks());
            model.addAttribute("totalStudents", studentService.countAllStudents());
            model.addAttribute("totalProducts", productService.countAllProducts());
            
            // Lấy sách mới nhất
            model.addAttribute("latestBooks", bookService.findLatestBooks(5));
            
            // Lấy sách phổ biến
            model.addAttribute("popularBooks", bookService.findPopularBooks(5));
            
        } catch (Exception e) {
            // Nếu services chưa ready, đặt giá trị mặc định
            model.addAttribute("totalBooks", 0);
            model.addAttribute("availableBooks", 0);
            model.addAttribute("borrowedBooks", 0);
            model.addAttribute("totalStudents", 0);
            model.addAttribute("totalProducts", 0);
        }
        
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }
}
