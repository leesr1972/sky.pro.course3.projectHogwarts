package ru.hogwarts.shcool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.shcool.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getQuantityOfStudents();

    @Query(value = "SELECT AVG (age) FROM student", nativeQuery = true)
    Float getAvarageAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLast5Students();
}
