package ru.coursemodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.coursemodel.model.Course;
import ru.coursemodel.repository.CourseRepository;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Alexey on 04.11.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CoursemodelApplication.class })
@AutoConfigureMockMvc
public class CourseControllerMockTests {

    private static final long UNKNOWN_ID = Long.MAX_VALUE;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MockMvc mockMvc;

    private Course savedCourse;

    @Before
    public void setUp() {
        savedCourse = new Course();
        savedCourse.setName(randomAlphabetic(10));
        savedCourse = courseRepository.save(savedCourse);
    }

    @After
    public void tearDown() throws Exception {
        courseRepository.deleteAll();
    }

    @Test
    public void whenGetAllCourses_thenOK() throws Exception {
        Set<Course> courses = new HashSet<>();
        courses.add(savedCourse);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(courses)));
    }

    @Test
    public void whenGetOneCourse_thenOK() throws Exception {
        mockMvc.perform(get("/courses/{id}", savedCourse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(savedCourse)));
    }

    @Test
    public void whenGetOneCourse_thenNotFound() throws Exception {
        mockMvc.perform(get("/courses/{id}", UNKNOWN_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateCourse_thenCreated() throws Exception {
        Course newCourse = new Course();
        newCourse.setName(randomAlphabetic(10));

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newCourse)))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenCreateCourse_thenConflict() throws Exception {
        Course newCourse = new Course();
        newCourse.setId(savedCourse.getId());

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newCourse)))
                .andExpect(status().isConflict());
    }

    @Test
    public void whenUpdateCourse_thenOk() throws Exception {
        Course newCourse = new Course();
        newCourse.setId(savedCourse.getId());
        newCourse.setName(randomAlphabetic(10));

        mockMvc.perform(put("/courses/{id}", savedCourse.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newCourse)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(newCourse)));
        assertEquals(newCourse.getName(), courseRepository.findOne(savedCourse.getId()).getName());
    }

    @Test
    public void whenUpdateCourse_thenNotFound() throws Exception {
        Course newCourse = new Course();

        mockMvc.perform(put("/courses/{id}", UNKNOWN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newCourse)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenDeleteCourse_thenNoContent() throws Exception {
        mockMvc.perform(delete("/courses/{id}", savedCourse.getId()))
                .andExpect(status().isNoContent());
        assertEquals(true, courseRepository.findOne(savedCourse.getId()) == null);
    }

    @Test
    public void whenDeleteCourse_thenNotFound() throws Exception {
        mockMvc.perform(delete("/courses/{id}", UNKNOWN_ID))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
