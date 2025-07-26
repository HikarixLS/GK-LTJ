package com.gdu.productmanagement.controller;

import com.gdu.productmanagement.entity.Book;
import com.gdu.productmanagement.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controller xử lý các thao tác CRUD cho Book (Sách)
 * Bao gồm: Hiển thị danh sách, thêm, sửa, xóa sách
 */
@Slf4j
@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Hiển thị danh sách sách với phân trang và tìm kiếm
     */
    @GetMapping
    public String listBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            Model model) {
        
        log.info("Hiển thị danh sách sách - Page: {}, Size: {}, Search: {}", page, size, search);
        
        try {
            Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            
            Page<Book> bookPage;
            if (search != null && !search.trim().isEmpty()) {
                bookPage = bookService.searchBooks(search.trim(), pageable);
                model.addAttribute("search", search);
            } else if (category != null && !category.trim().isEmpty()) {
                bookPage = bookService.findByCategory(Book.BookCategory.valueOf(category), pageable);
                model.addAttribute("category", category);
            } else {
                bookPage = bookService.findAllBooks(pageable);
            }
            
            model.addAttribute("bookPage", bookPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", bookPage.getTotalPages());
            model.addAttribute("totalElements", bookPage.getTotalElements());
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
            model.addAttribute("categories", Book.BookCategory.values());
            
            return "books/list";
            
        } catch (Exception e) {
            log.error("Lỗi khi hiển thị danh sách sách", e);
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách sách");
            return "error";
        }
    }

    /**
     * Hiển thị chi tiết sách
     */
    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("Xem chi tiết sách ID: {}", id);
        
        try {
            Optional<Book> book = bookService.findBookById(id);
            if (book.isPresent()) {
                model.addAttribute("book", book.get());
                return "books/view";
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy sách với ID: " + id);
                return "redirect:/books";
            }
        } catch (Exception e) {
            log.error("Lỗi khi xem chi tiết sách ID: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải thông tin sách");
            return "redirect:/books";
        }
    }

    /**
     * Hiển thị form thêm sách mới
     */
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public String showAddForm(Model model) {
        log.info("Hiển thị form thêm sách");
        
        model.addAttribute("book", new Book());
        model.addAttribute("categories", Book.BookCategory.values());
        model.addAttribute("formats", Book.BookFormat.values());
        model.addAttribute("statuses", Book.BookStatus.values());
        model.addAttribute("action", "add");
        
        return "books/form";
    }

    /**
     * Xử lý thêm sách mới
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public String addBook(@Valid @ModelAttribute Book book, 
                         BindingResult result, 
                         Model model, 
                         RedirectAttributes redirectAttributes) {
        
        log.info("Thêm sách mới: {}", book.getTitle());
        
        if (result.hasErrors()) {
            log.warn("Validation errors khi thêm sách: {}", result.getAllErrors());
            model.addAttribute("categories", Book.BookCategory.values());
            model.addAttribute("formats", Book.BookFormat.values());
            model.addAttribute("statuses", Book.BookStatus.values());
            model.addAttribute("action", "add");
            return "books/form";
        }
        
        try {
            // Kiểm tra ISBN trùng lặp
            if (bookService.existsByIsbn(book.getIsbn())) {
                result.rejectValue("isbn", "error.book", "ISBN đã tồn tại trong hệ thống");
                model.addAttribute("categories", Book.BookCategory.values());
                model.addAttribute("formats", Book.BookFormat.values());
                model.addAttribute("statuses", Book.BookStatus.values());
                model.addAttribute("action", "add");
                return "books/form";
            }
            
            Book savedBook = bookService.saveBook(book);
            log.info("Thêm sách thành công ID: {}", savedBook.getId());
            
            redirectAttributes.addFlashAttribute("success", 
                "Thêm sách '" + book.getTitle() + "' thành công!");
            return "redirect:/books";
            
        } catch (Exception e) {
            log.error("Lỗi khi thêm sách", e);
            model.addAttribute("error", "Có lỗi xảy ra khi thêm sách: " + e.getMessage());
            model.addAttribute("categories", Book.BookCategory.values());
            model.addAttribute("formats", Book.BookFormat.values());
            model.addAttribute("statuses", Book.BookStatus.values());
            model.addAttribute("action", "add");
            return "books/form";
        }
    }

    /**
     * Hiển thị form chỉnh sửa sách
     */
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("Hiển thị form chỉnh sửa sách ID: {}", id);
        
        try {
            Optional<Book> book = bookService.findBookById(id);
            if (book.isPresent()) {
                model.addAttribute("book", book.get());
                model.addAttribute("categories", Book.BookCategory.values());
                model.addAttribute("formats", Book.BookFormat.values());
                model.addAttribute("statuses", Book.BookStatus.values());
                model.addAttribute("action", "edit");
                return "books/form";
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy sách với ID: " + id);
                return "redirect:/books";
            }
        } catch (Exception e) {
            log.error("Lỗi khi hiển thị form chỉnh sửa sách ID: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra khi tải thông tin sách");
            return "redirect:/books";
        }
    }

    /**
     * Xử lý cập nhật sách
     */
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public String updateBook(@PathVariable Long id,
                           @Valid @ModelAttribute Book book,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        
        log.info("Cập nhật sách ID: {}", id);
        
        if (result.hasErrors()) {
            log.warn("Validation errors khi cập nhật sách: {}", result.getAllErrors());
            book.setId(id);
            model.addAttribute("categories", Book.BookCategory.values());
            model.addAttribute("formats", Book.BookFormat.values());
            model.addAttribute("statuses", Book.BookStatus.values());
            model.addAttribute("action", "edit");
            return "books/form";
        }
        
        try {
            // Kiểm tra sách có tồn tại
            if (!bookService.existsById(id)) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy sách với ID: " + id);
                return "redirect:/books";
            }
            
            // Kiểm tra ISBN trùng lặp (trừ chính nó)
            if (bookService.existsByIsbnAndIdNot(book.getIsbn(), id)) {
                result.rejectValue("isbn", "error.book", "ISBN đã tồn tại trong hệ thống");
                book.setId(id);
                model.addAttribute("categories", Book.BookCategory.values());
                model.addAttribute("formats", Book.BookFormat.values());
                model.addAttribute("statuses", Book.BookStatus.values());
                model.addAttribute("action", "edit");
                return "books/form";
            }
            
            book.setId(id);
            Book updatedBook = bookService.saveBook(book);
            log.info("Cập nhật sách thành công ID: {}", updatedBook.getId());
            
            redirectAttributes.addFlashAttribute("success", 
                "Cập nhật sách '" + book.getTitle() + "' thành công!");
            return "redirect:/books";
            
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật sách ID: {}", id, e);
            book.setId(id);
            model.addAttribute("error", "Có lỗi xảy ra khi cập nhật sách: " + e.getMessage());
            model.addAttribute("categories", Book.BookCategory.values());
            model.addAttribute("formats", Book.BookFormat.values());
            model.addAttribute("statuses", Book.BookStatus.values());
            model.addAttribute("action", "edit");
            return "books/form";
        }
    }

    /**
     * Xóa sách
     */
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Xóa sách ID: {}", id);
        
        try {
            Optional<Book> book = bookService.findBookById(id);
            if (book.isPresent()) {
                bookService.deleteBook(id);
                log.info("Xóa sách thành công ID: {}", id);
                redirectAttributes.addFlashAttribute("success", 
                    "Xóa sách '" + book.get().getTitle() + "' thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy sách với ID: " + id);
            }
        } catch (Exception e) {
            log.error("Lỗi khi xóa sách ID: {}", id, e);
            redirectAttributes.addFlashAttribute("error", 
                "Không thể xóa sách này. Có thể sách đang được sử dụng trong hệ thống.");
        }
        
        return "redirect:/books";
    }

    /**
     * API endpoint để lấy thông tin sách theo ID (JSON)
     */
    @GetMapping("/api/{id}")
    @ResponseBody
    public Book getBookById(@PathVariable Long id) {
        return bookService.findBookById(id).orElse(null);
    }
}
