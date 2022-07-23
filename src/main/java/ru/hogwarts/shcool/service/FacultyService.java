package ru.hogwarts.shcool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.model.Student;
import ru.hogwarts.shcool.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        LOGGER.info("Was invoked method for add faculty.");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty (Long id){
        LOGGER.info("Was invoked method for find faculty.");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        LOGGER.info("Was invoked method for edit faculty.");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty (Long id){
        LOGGER.info("Was invoked method for delete faculty.");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        LOGGER.info("Was invoked method for get faculties by color.");
        return facultyRepository.findAll().stream().
                filter(e -> e.getColor().equals(color)).toList();
    }

    public Collection<Faculty> getAllFaculties() {
        LOGGER.info("Was invoked method for get all faculties.");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultyByNameOrColor (String name, String color) {
        LOGGER.info("Was invoked method for find faculties with name {} or color {}.", name, color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> getStudentsOfFaculty (Long id) {
        LOGGER.info("Was invoked method for get of faculty {}.", facultyRepository.findById(id).get().getName());
        return facultyRepository.findById(id).get().getStudents();
    }

    public String getLongestNameOfFaculty() {
        LOGGER.info("Was invoked method for get the longest name of the faculty.");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName).max(String::compareTo).orElseThrow();
    }
}
