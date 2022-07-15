package ru.hogwarts.shcool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.model.Student;
import ru.hogwarts.shcool.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student) {
        LOGGER.info("Was invoked method for add student.");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        LOGGER.info("Was invoked method for find student.");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        LOGGER.info("Was invoked method for edit student.");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        LOGGER.info("Was invoked method for delete student.");
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAge(Integer age) {
        LOGGER.info("Was invoked method for get students of age = {}.", age);
        return studentRepository.findAll().stream().
                filter(e -> e.getAge() == age).toList();
    }

    public Collection<Student> getAllStudents() {
        LOGGER.info("Was invoked method for get all students.");
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsbyAge(Integer minAge, Integer maxAge) {
        LOGGER.info("Was invoked method for get students of age from {} years to {} years.", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyOfStudent(Long id) {
        LOGGER.info("Was invoked method for get faculty of student.");
        return studentRepository.findById(id).get().getFaculty();
    }

    public Integer getQuantityOfStudents() {
        LOGGER.info("Was invoked method for get quantity of students.");
        return studentRepository.getQuantityOfStudents();
    }

    public Float getAvarageAgeOfStudents() {
        LOGGER.info("Was invoked method for get avarage age of students.");
        return studentRepository.getAvarageAgeOfStudents();
    }

    public Collection<Student> getLast5Students() {
        LOGGER.info("Was invoked method for get last 5 students.");
        return studentRepository.getLast5Students();
    }

    public List<String> getNamesOfStudentsStartingWithLetter(Character firstLetter) {
        return studentRepository.findAll().stream()
                .filter(s->s.getName().startsWith(String.valueOf(firstLetter)))
                .map(s->s.getName().toUpperCase()).sorted().collect(Collectors.toList());
    }

    public OptionalDouble getAvarageAgeOfStudents2() {
        LOGGER.info("Was invoked second method for get avarage age of students.");
        List<Integer> listOfAges = studentRepository.findAll().stream()
                .map(Student::getAge).toList();
        return listOfAges.stream().mapToDouble(e->e).average();
    }

    public Integer getSum() {
        LOGGER.info("Was invoked method for get sum.");
        long start = System.currentTimeMillis();
        Integer sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
//                .parallel()
                .reduce(0, (a, b) -> a + b );
        long operatingTime = System.currentTimeMillis() - start;
        LOGGER.info("Operating time is " + operatingTime + " ms.");
        return sum;
    }
}
