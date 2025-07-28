package com.bookstore.controller;

import com.bookstore.service.BookService;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    // Trang chủ
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        // Lấy một số sách để hiển thị trên trang chủ
        model.addAttribute("recentBooks", bookService.findAll().stream().limit(6).toList());
        model.addAttribute("categories", bookService.getAllCategories());
        
        // Thống kê cơ bản
        model.addAttribute("totalBooks", bookService.countAll());
        model.addAttribute("totalUsers", userService.countAll());
        model.addAttribute("availableBooks", bookService.findAvailableBooks().size());
        
        return "home";
    }
    
    // Trang giới thiệu
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    // Trang liên hệ
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
