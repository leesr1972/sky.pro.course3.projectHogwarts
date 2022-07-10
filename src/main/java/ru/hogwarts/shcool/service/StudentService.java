package ru.hogwarts.shcool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.model.Student;
import ru.hogwarts.shcool.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student.");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student.");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student.");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student.");
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAge(Integer age) {
        logger.info("Was invoked method for get students of age = {}.", age);
        return studentRepository.findAll().stream().
                filter(e -> e.getAge() == age).toList();
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students.");
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsbyAge(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method for get students of age from {} years to {} years.", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyOfStudent(Long id) {
        logger.info("Was invoked method for get faculty of student.");
        return studentRepository.findById(id).get().getFaculty();
    }

    public Integer getQuantityOfStudents() {
        logger.info("Was invoked method for get quantity of students.");
        return studentRepository.getQuantityOfStudents();
    }

    public Float getAvarageAgeOfStudents() {
        logger.info("Was invoked method for get avarage age of students.");
        return studentRepository.getAvarageAgeOfStudents();
    }

    public Collection<Student> getLast5Students() {
        logger.info("Was invoked method for get last 5 students.");
        return studentRepository.getLast5Students();
    }
}
