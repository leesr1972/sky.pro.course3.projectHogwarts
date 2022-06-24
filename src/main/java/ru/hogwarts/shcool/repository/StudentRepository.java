package ru.hogwarts.shcool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.shcool.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
