package ru.hogwarts.shcool;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.shcool.controller.FacultyController;
import ru.hogwarts.shcool.controller.StudentController;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.model.Student;
import ru.hogwarts.shcool.repository.FacultyRepository;
import ru.hogwarts.shcool.repository.StudentRepository;
import ru.hogwarts.shcool.service.StudentService;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setAge(30);

        HttpEntity<Student> entity = new HttpEntity<>(student, new HttpHeaders());

        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port +
                        "/student/", HttpMethod.POST, entity, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student.getName(), Objects.requireNonNull(response.getBody()).getName());
        assertEquals(student.getAge(), response.getBody().getAge());

        studentRepository.deleteById(response.getBody().getId());
    }

    @Test
    public void testGetStudent() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setAge(30);

        HttpEntity<Student> entity = new HttpEntity<>(student, new HttpHeaders());

        ResponseEntity<Student> response1 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity, Student.class);

        ResponseEntity<Student> response2 = restTemplate.exchange("http://localhost:" + port +
                "/student/" + Objects.requireNonNull(response1.getBody()).getId(),
                HttpMethod.GET, entity, Student.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(student.getName(), Objects.requireNonNull(response2.getBody()).getName());
        assertEquals(student.getAge(), response2.getBody().getAge());

        studentRepository.deleteById(response1.getBody().getId());
    }

    @Test
    public void testPutStudent() throws Exception {
        Student student1 = new Student();
        student1.setName("John");
        student1.setAge(30);

        HttpEntity<Student> entity1 = new HttpEntity<>(student1, new HttpHeaders());

        ResponseEntity<Student> response1 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity1, Student.class);

        Student student2 = new Student();
        student2.setId(Objects.requireNonNull(response1.getBody()).getId());
        student2.setName("Bob");
        student2.setAge(25);

        HttpEntity<Student> entity2 = new HttpEntity<>(student2, new HttpHeaders());

        ResponseEntity<Student> response2 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.PUT, entity2, Student.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(student2.getName(), Objects.requireNonNull(response2.getBody()).getName());
        assertEquals(student2.getAge(), response2.getBody().getAge());

        studentRepository.deleteById(response1.getBody().getId());
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setAge(30);

        HttpEntity<Student> entity1 = new HttpEntity<>(student, new HttpHeaders());

        ResponseEntity<Student> response1 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity1, Student.class);

        HttpEntity<String> entity2 = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<String> response2 = restTemplate.exchange("http://localhost:" + port +
                "/student/" + Objects.requireNonNull(response1.getBody()).getId(),
                HttpMethod.DELETE, entity2, String.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        Student student1 = new Student();
        student1.setName("John");
        student1.setAge(150);
        HttpEntity<Student> entity1 = new HttpEntity<>(student1, new HttpHeaders());
        ResponseEntity<Student> response1 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity1, Student.class);
        student1.setId(Objects.requireNonNull(response1.getBody()).getId());
        student1.setFaculty(null);

        Student student2 = new Student();
        student2.setName("Bob");
        student2.setAge(120);
        HttpEntity<Student> entity2 = new HttpEntity<>(student2, new HttpHeaders());
        ResponseEntity<Student> response2 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity2, Student.class);
        student2.setId(Objects.requireNonNull(response2.getBody()).getId());
        student2.setFaculty(null);

        Student student3 = new Student();
        student3.setName("Jack");
        student3.setAge(150);
        HttpEntity<Student> entity3 = new HttpEntity<>(student3, new HttpHeaders());
        ResponseEntity<Student> response3 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity3, Student.class);
        student3.setId(Objects.requireNonNull(response3.getBody()).getId());
        student3.setFaculty(null);

        List<Student> studentsByAge = List.of(student1, student3);
        String expected = om.writeValueAsString(studentsByAge);

        ResponseEntity<String> response = restTemplate.
                getForEntity("http://localhost:" + port + "/student/age/150",
                        String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());

        studentRepository.deleteById(Objects.requireNonNull(response1.getBody()).getId());
        studentRepository.deleteById(Objects.requireNonNull(response2.getBody()).getId());
        studentRepository.deleteById(Objects.requireNonNull(response3.getBody()).getId());
    }

    @Test
    public void testGetStudentsByAges() throws Exception {
        Student student1 = new Student();
        student1.setName("John");
        student1.setAge(150);
        HttpEntity<Student> entity1 = new HttpEntity<>(student1, new HttpHeaders());
        ResponseEntity<Student> response1 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity1, Student.class);
        student1.setId(Objects.requireNonNull(response1.getBody()).getId());
        student1.setFaculty(null);

        Student student2 = new Student();
        student2.setName("Bob");
        student2.setAge(120);
        HttpEntity<Student> entity2 = new HttpEntity<>(student2, new HttpHeaders());
        ResponseEntity<Student> response2 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity2, Student.class);
        student2.setId(Objects.requireNonNull(response2.getBody()).getId());
        student2.setFaculty(null);

        Student student3 = new Student();
        student3.setName("Jack");
        student3.setAge(150);
        HttpEntity<Student> entity3 = new HttpEntity<>(student3, new HttpHeaders());
        ResponseEntity<Student> response3 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity3, Student.class);
        student3.setId(Objects.requireNonNull(response3.getBody()).getId());
        student3.setFaculty(null);

        List<Student> studentsByAges = List.of(student2);
        String expected = om.writeValueAsString(studentsByAges);

        ResponseEntity<String> response = restTemplate.
                getForEntity("http://localhost:" + port + "/student/?minAge=110&maxAge=130",
                        String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());

        studentRepository.deleteById(Objects.requireNonNull(response1.getBody()).getId());
        studentRepository.deleteById(Objects.requireNonNull(response2.getBody()).getId());
        studentRepository.deleteById(Objects.requireNonNull(response3.getBody()).getId());
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        Faculty facultyOfStudent = new Faculty();
        facultyOfStudent.setName("market");
        facultyOfStudent.setColor("black");
        HttpEntity<Faculty> entity2 = new HttpEntity<>(facultyOfStudent, new HttpHeaders());
        ResponseEntity<Faculty> response2 = restTemplate.exchange("http://localhost:" + port +
                "/faculty/", HttpMethod.POST, entity2, Faculty.class);
        facultyOfStudent.setId((Objects.requireNonNull(response2.getBody())).getId());

        Student student1 = new Student();
        student1.setName("John");
        student1.setAge(150);
        HttpEntity<Student> entity1 = new HttpEntity<>(student1, new HttpHeaders());
        ResponseEntity<Student> response1 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity1, Student.class);
        student1.setId(Objects.requireNonNull(response1.getBody()).getId());

        Student student3 = new Student();
        student3.setName("Jack");
        student3.setAge(150);
        HttpEntity<Student> entity3 = new HttpEntity<>(student3, new HttpHeaders());
        ResponseEntity<Student> response3 = restTemplate.exchange("http://localhost:" + port +
                "/student/", HttpMethod.POST, entity3, Student.class);
        student3.setId(Objects.requireNonNull(response3.getBody()).getId());

        facultyOfStudent.setStudents(List.of(student1, student3));
        student1.setFaculty(facultyOfStudent);
        student3.setFaculty(facultyOfStudent);

        ResponseEntity<Faculty> response = restTemplate.
                getForEntity("http://localhost:" + port + "/student/faculty/?id="
                        + response1.getBody().getId(), Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(facultyRepository.findById(response2.getBody().getId()).get(),
                response.getBody());

        studentRepository.deleteById(Objects.requireNonNull(response1.getBody()).getId());
        studentRepository.deleteById(response3.getBody().getId());
        facultyRepository.deleteById(response2.getBody().getId());
    }
}
