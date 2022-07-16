package ru.hogwarts.shcool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.model.Student;
import ru.hogwarts.shcool.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student foundStudent = studentService.findStudent(id);
        if (foundStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<Student>> getStudentsByAge(@PathVariable Integer age) {
        List<Student> foundStudents = studentService.getStudentsByAge(age);
        return ResponseEntity.ok(foundStudents);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudents(@RequestParam(required = false) Integer minAge,
                                                           @RequestParam(required = false) Integer maxAge) {
        if (minAge != null && maxAge != null) {
            return ResponseEntity.ok(studentService.findStudentsbyAge(minAge, maxAge));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/faculty")
    public ResponseEntity<Faculty> getFacultyOfStudent(@RequestParam Long id) {
        return ResponseEntity.ok(studentService.getFacultyOfStudent(id));
    }

    @GetMapping("/getQuantityOFStudents")
    public ResponseEntity<Integer> getQuantityOfStudents() {
        return ResponseEntity.ok(studentService.getQuantityOfStudents());
    }

    @GetMapping("/getAvarageAgeOfStudents")
    public ResponseEntity<Float> getAvarageOfStudents() {
        return ResponseEntity.ok(studentService.getAvarageAgeOfStudents());
    }

    @GetMapping("/getLastStudents")
    public ResponseEntity<Collection<Student>> getLast5Students() {
        return ResponseEntity.ok(studentService.getLast5Students());
    }

    @GetMapping("/getNamesOfStudentsStartingWithLetter")
    public ResponseEntity<List<String>> getNamesOfStudentsStartingWithLetter(@RequestParam Character firstLetter) {
        return ResponseEntity.ok(studentService.getNamesOfStudentsStartingWithLetter(firstLetter));
    }

    @GetMapping("/getAvarageAgeOfStudents2")
    public ResponseEntity<OptionalDouble> getAvarageOfStudents2() {
        return ResponseEntity.ok(studentService.getAvarageAgeOfStudents2());
    }

    @GetMapping("/getSum")
    public ResponseEntity<Integer> getSum() {
        return ResponseEntity.ok(studentService.getSum());
    }
}
