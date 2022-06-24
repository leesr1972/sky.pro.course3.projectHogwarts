package ru.hogwarts.shcool.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.model.Student;
import ru.hogwarts.shcool.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty (Long id){
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty (Long id){
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAll().stream().
                filter(e -> e.getColor().equals(color)).toList();
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultyByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Student> getStudentsOfFaculty (Long id) {
        return facultyRepository.findById(id).get().getStudents();
    }
}
