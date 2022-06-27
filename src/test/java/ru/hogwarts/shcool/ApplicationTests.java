package ru.hogwarts.shcool;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.shcool.controller.StudentController;
import ru.hogwarts.shcool.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ObjectMapper om = new ObjectMapper();

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testPostStudent() throws Exception {
        Student student = new Student();
        student.setId(7L);
        student.setName("Bob");
        student.setAge(26);

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class))
                .isNotNull();
    }

    @Test
    public void testPutStudent() throws Exception {
        Student student = new Student();
        student.setId(2L);
        student.setName("Михаил");
        student.setAge(26);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(student), headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/student/",
                HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteStudent() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/student/3",
                HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age/20", String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentsByAges() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port +
                        "/student/?minAge=20&maxAge=30", String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port +
                        "/student/faculty/?id=1", String.class))
                .isNotNull();
    }
}
