package ru.hogwarts.shcool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.shcool.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
