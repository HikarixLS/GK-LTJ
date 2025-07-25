package com.gdu.productmanagement.controller;

import com.gdu.productmanagement.entity.Student;
import com.gdu.productmanagement.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public String listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search,
            Model model) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Student> students;
        if (search != null && !search.trim().isEmpty()) {
            students = studentService.searchStudents(search.trim(), pageable);
            model.addAttribute("search", search);
        } else {
            students = studentService.findAll(pageable);
        }

        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", students.getTotalPages());
        model.addAttribute("totalElements", students.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "students/list";
    }

    @GetMapping("/create")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("genders", Student.Gender.values());
        return "students/create";
    }

    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute Student student,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("genders", Student.Gender.values());
            return "students/create";
        }

        try {
            studentService.createStudent(student);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Sinh viên đã được tạo thành công!");
            return "redirect:/students";
        } catch (Exception e) {
            log.error("Error creating student", e);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("genders", Student.Gender.values());
            return "students/create";
        }
    }

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        try {
            Student student = studentService.findById(id);
            model.addAttribute("student", student);
            return "students/view";
        } catch (Exception e) {
            log.error("Error viewing student", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/students";
        }
    }

    @GetMapping("/{id}/edit")
    public String editStudentForm(@PathVariable Long id, Model model) {
        try {
            Student student = studentService.findById(id);
            model.addAttribute("student", student);
            model.addAttribute("genders", Student.Gender.values());
            return "students/edit";
        } catch (Exception e) {
            log.error("Error loading student for edit", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/students";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateStudent(@PathVariable Long id,
            @Valid @ModelAttribute Student student,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("genders", Student.Gender.values());
            return "students/edit";
        }

        try {
            studentService.updateStudent(id, student);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Thông tin sinh viên đã được cập nhật!");
            return "redirect:/students/" + id;
        } catch (Exception e) {
            log.error("Error updating student", e);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("genders", Student.Gender.values());
            return "students/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Sinh viên đã được xóa thành công!");
        } catch (Exception e) {
            log.error("Error deleting student", e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deactivateStudent(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Sinh viên đã được vô hiệu hóa!");
        } catch (Exception e) {
            log.error("Error deactivating student", e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/students/" + id;
    }

    @GetMapping("/stats")
    public String studentStats(Model model) {
        try {
            Long totalActive = studentService.countActiveStudents();
            model.addAttribute("totalActiveStudents", totalActive);

            model.addAttribute("statsByClass", studentService.getStudentStatsByClass());
            model.addAttribute("statsByMajor", studentService.getStudentStatsByMajor());

            return "students/stats";
        } catch (Exception e) {
            log.error("Error loading student statistics", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/students";
        }
    }
}
