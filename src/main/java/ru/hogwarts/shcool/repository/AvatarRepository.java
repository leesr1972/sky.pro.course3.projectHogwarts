package ru.hogwarts.shcool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.hogwarts.shcool.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends PagingAndSortingRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}
