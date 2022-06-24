package ru.hogwarts.shcool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        facultyService.addFaculty(faculty);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty foundFaculty = facultyService.findFaculty(id);
        if (foundFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@PathVariable String color) {
        List<Faculty> foundFaculties = facultyService.getFacultiesByColor(color);
        return ResponseEntity.ok(foundFaculties);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }
}
