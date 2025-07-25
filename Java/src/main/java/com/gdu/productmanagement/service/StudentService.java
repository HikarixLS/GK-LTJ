package com.gdu.productmanagement.service;

import com.gdu.productmanagement.entity.Student;
import com.gdu.productmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public Student createStudent(Student student) {
        log.info("Creating new student with code: {}", student.getStudentCode());

        if (studentRepository.existsByStudentCode(student.getStudentCode())) {
            throw new RuntimeException("Mã sinh viên đã tồn tại: " + student.getStudentCode());
        }

        if (student.getEmail() != null && studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email đã tồn tại: " + student.getEmail());
        }

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        log.info("Updating student with id: {}", id);

        Student student = findById(id);

        // Check if studentCode is being changed and if new code already exists
        if (!student.getStudentCode().equals(studentDetails.getStudentCode()) &&
                studentRepository.existsByStudentCode(studentDetails.getStudentCode())) {
            throw new RuntimeException("Mã sinh viên đã tồn tại: " + studentDetails.getStudentCode());
        }

        // Check if email is being changed and if new email already exists
        if (studentDetails.getEmail() != null &&
                !studentDetails.getEmail().equals(student.getEmail()) &&
                studentRepository.existsByEmail(studentDetails.getEmail())) {
            throw new RuntimeException("Email đã tồn tại: " + studentDetails.getEmail());
        }

        // Update fields
        student.setStudentCode(studentDetails.getStudentCode());
        student.setFullName(studentDetails.getFullName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        student.setAddress(studentDetails.getAddress());
        student.setGender(studentDetails.getGender());
        student.setBirthDate(studentDetails.getBirthDate());
        student.setClassName(studentDetails.getClassName());
        student.setMajor(studentDetails.getMajor());
        student.setGpa(studentDetails.getGpa());
        student.setIsActive(studentDetails.getIsActive());

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        Student student = findById(id);
        studentRepository.delete(student);
    }

    public void deactivateStudent(Long id) {
        log.info("Deactivating student with id: {}", id);
        Student student = findById(id);
        student.setIsActive(false);
        studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID: " + id));
    }

    @Transactional(readOnly = true)
    public Optional<Student> findByStudentCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Student> searchStudents(String searchTerm, Pageable pageable) {
        return studentRepository.findBySearchTerm(searchTerm, pageable);
    }

    @Transactional(readOnly = true)
    public List<Student> findByFullName(String fullName) {
        return studentRepository.findByFullNameContainingIgnoreCase(fullName);
    }

    @Transactional(readOnly = true)
    public List<Student> findByClassName(String className) {
        return studentRepository.findByClassNameIgnoreCase(className);
    }

    @Transactional(readOnly = true)
    public List<Student> findByMajor(String major) {
        return studentRepository.findByMajorContainingIgnoreCase(major);
    }

    @Transactional(readOnly = true)
    public List<Student> findActiveStudents() {
        return studentRepository.findByIsActive(true);
    }

    @Transactional(readOnly = true)
    public List<Student> findByGpaGreaterThan(Double gpa) {
        return studentRepository.findByGpaGreaterThanEqual(gpa);
    }

    @Transactional(readOnly = true)
    public Long countActiveStudents() {
        return studentRepository.countActiveStudents();
    }

    @Transactional(readOnly = true)
    public long countAllStudents() {
        return studentRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getStudentStatsByClass() {
        return studentRepository.countStudentsByClass();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getStudentStatsByMajor() {
        return studentRepository.countStudentsByMajor();
    }

    @Transactional(readOnly = true)
    public boolean existsByStudentCode(String studentCode) {
        return studentRepository.existsByStudentCode(studentCode);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
}
