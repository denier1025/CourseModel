package ru.coursemodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.coursemodel.CoursemodelApplication;
import ru.coursemodel.model.Course;
import ru.coursemodel.repository.CourseRepository;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Alexey on 04.11.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CoursemodelApplication.class })
public class CourseControllerMockTests {

    private static final long UNKNOWN_ID = Long.MAX_VALUE;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private CourseRepository courseRepository;

    private MockMvc mockMvc;

    private Course savedCourse;

    @Before
    public void setUp() {
        savedCourse = new Course();
        savedCourse.setName(randomAlphabetic(10));
        savedCourse.setNumber(new Random().nextInt());
        savedCourse.setPrice(new Random().nextFloat());
        savedCourse = courseRepository.save(savedCourse);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
