package ru.hogwarts.shcool.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.model.Student;
import ru.hogwarts.shcool.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAge(Integer age) {
        return studentRepository.findAll().stream().
                filter(e -> e.getAge() == age).toList();
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsbyAge(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyOfStudent(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }
}
