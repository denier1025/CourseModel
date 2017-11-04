package ru.coursemodel.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.coursemodel.CoursemodelApplicationTests;
import ru.coursemodel.model.Course;
import ru.coursemodel.repository.CourseRepository;

import java.util.List;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexey on 03.11.2017.
 */
public class CourseControllerTests extends CoursemodelApplicationTests {

    private static final long UNKNOWN_ID = Long.MAX_VALUE;

    private static final String API_ROOT
            = "http://localhost:8081/courses/";

    @Autowired
    private CourseRepository courseRepository;

    private Course savedCourse;

    @Before
    public void setUp() {
        savedCourse = new Course();
        savedCourse.setName(randomAlphabetic(10));
        savedCourse.setNumber(new Random().nextInt());
        savedCourse.setPrice(new Random().nextFloat());
        savedCourse = courseRepository.save(savedCourse);
    }

    @After
    public void tearDown() {
        courseRepository.deleteAll();

    }

    @Test
    public void whenGetAllCourses_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() == 1);
    }

    @Test
    public void whenGetOneCourse_thenOK() {
        Response response = RestAssured.get(API_ROOT + savedCourse.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(savedCourse.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenGetOneCourse_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateCourse_thenCreated() {
        Course newCourse = new Course();
        newCourse.setName(randomAlphabetic(10));

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newCourse)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertEquals(newCourse.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenCreateCourse_thenConflict() {
        Course newCourse = new Course();
        newCourse.setId(savedCourse.getId());

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newCourse)
                .post(API_ROOT);

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCourse_thenOk() {
        Course newCourse = new Course();
        newCourse.setName(randomAlphabetic(10));

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newCourse)
                .put(API_ROOT + savedCourse.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(newCourse.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenUpdateCourse_thenNotFound() {
        Course newCourse = new Course();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newCourse)
                .put(API_ROOT + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenDeleteCourse_thenNoContent() {
        Response response = RestAssured
                .delete(API_ROOT + savedCourse.getId());

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
        assertTrue(!courseRepository.findAll().iterator().hasNext());
    }

    @Test
    public void whenDeleteCourse_thenNotFound() {
        Response response = RestAssured
                .delete(API_ROOT + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
