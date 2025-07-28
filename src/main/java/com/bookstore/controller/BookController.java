package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    // Hiển thị danh sách sách
    @GetMapping
    public String listBooks(Model model, 
                           @RequestParam(required = false) String search,
                           @RequestParam(required = false) String category) {
        List<Book> books;
        
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchBooks(search, search, null, search);
        } else if (category != null && !category.trim().isEmpty()) {
            books = bookService.findByCategory(category);
        } else {
            books = bookService.findAll();
        }
        
        model.addAttribute("books", books);
        model.addAttribute("categories", bookService.getAllCategories());
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentCategory", category);
        
        return "books/list";
    }
    
    // Tìm kiếm sách
    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false) String title,
                             @RequestParam(required = false) String author,
                             @RequestParam(required = false) String category,
                             @RequestParam(required = false) String publisher,
                             Model model) {
        List<Book> books = bookService.searchBooks(title, author, category, publisher);
        
        model.addAttribute("books", books);
        model.addAttribute("categories", bookService.getAllCategories());
        model.addAttribute("publishers", bookService.getAllPublishers());
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchAuthor", author);
        model.addAttribute("searchCategory", category);
        model.addAttribute("searchPublisher", publisher);
        
        return "books/search";
    }
    
    // Xem chi tiết sách
    @GetMapping("/view/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "books/view";
        }
        return "redirect:/books?error=notfound";
    }
    
    // Form thêm sách mới (Admin only)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", bookService.getAllCategories());
        model.addAttribute("publishers", bookService.getAllPublishers());
        return "books/add";
    }
    
    // Xử lý thêm sách mới
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute Book book, 
                         BindingResult result, 
                         Model model,
                         RedirectAttributes redirectAttributes) {
        
        // Kiểm tra ISBN đã tồn tại
        if (bookService.findByIsbn(book.getIsbn()).isPresent()) {
            result.rejectValue("isbn", "error.book", "ISBN đã tồn tại");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("categories", bookService.getAllCategories());
            model.addAttribute("publishers", bookService.getAllPublishers());
            return "books/add";
        }
        
        bookService.save(book);
        redirectAttributes.addFlashAttribute("success", "Thêm sách thành công!");
        return "redirect:/books";
    }
    
    // Form sửa sách (Admin only)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("categories", bookService.getAllCategories());
            model.addAttribute("publishers", bookService.getAllPublishers());
            return "books/edit";
        }
        return "redirect:/books?error=notfound";
    }
    
    // Xử lý cập nhật sách
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id,
                           @Valid @ModelAttribute Book book,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        
        // Kiểm tra ISBN đã tồn tại (trừ sách hiện tại)
        if (bookService.isIsbnExists(book.getIsbn(), id)) {
            result.rejectValue("isbn", "error.book", "ISBN đã tồn tại");
        }
        
        if (result.hasErrors()) {
            book.setId(id);
            model.addAttribute("categories", bookService.getAllCategories());
            model.addAttribute("publishers", bookService.getAllPublishers());
            return "books/edit";
        }
        
        book.setId(id);
        bookService.save(book);
        redirectAttributes.addFlashAttribute("success", "Cập nhật sách thành công!");
        return "redirect:/books";
    }
    
    // Xóa sách (Admin only)
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (bookService.existsById(id)) {
            bookService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa sách thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sách!");
        }
        return "redirect:/books";
    }
}
