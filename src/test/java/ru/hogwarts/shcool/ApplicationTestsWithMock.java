package ru.hogwarts.shcool;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.shcool.controller.FacultyController;
import ru.hogwarts.shcool.model.Faculty;
import ru.hogwarts.shcool.repository.FacultyRepository;
import ru.hogwarts.shcool.service.FacultyService;

@WebMvcTest
public class ApplicationTestsWithMock {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void testAddFaculty() throws Exception {
        Long id = 1L;
        String name = "radio";
        String color = "red";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color)

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

//        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
//        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/user") //send
//                        .content(userObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()) //receive
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.name").value(name));
    }
}
