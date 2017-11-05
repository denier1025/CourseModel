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
import ru.coursemodel.model.PassCourse;
import ru.coursemodel.repository.PassCourseRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexey on 03.11.2017.
 */
public class PassCourseTests extends CoursemodelApplicationTests {

    private static final long UNKNOWN_ID = Long.MAX_VALUE;
    private static final int PORT = 8081;
    private static final String BASE_URL
            = "http://localhost:" + PORT + "/passcourses/";

    @Autowired
    private PassCourseRepository passCourseRepository;

    private PassCourse savedPassCourse;

    @Before
    public void setUp() {
        savedPassCourse = new PassCourse();
        savedPassCourse = passCourseRepository.save(savedPassCourse);
    }

    @After
    public void tearDown() {
        passCourseRepository.deleteAll();

    }

    @Test
    public void whenGetAllPassCourses_thenOK() {
        Response response = RestAssured.get(BASE_URL);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() == 1);
    }

    @Test
    public void whenGetOnePassCourse_thenOK() {
        Response response = RestAssured.get(BASE_URL + savedPassCourse.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetOnePassCourse_thenNotFound() {
        Response response = RestAssured.get(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreatePassCourse_thenCreated() {
        PassCourse newPassCourse = new PassCourse();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newPassCourse)
                .post(BASE_URL);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenCreatePassCourse_thenConflict() {
        PassCourse newPassCourse = new PassCourse();
        newPassCourse.setId(savedPassCourse.getId());

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newPassCourse)
                .post(BASE_URL);

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdatePassCourse_thenOk() {
        PassCourse newPassCourse = new PassCourse();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newPassCourse)
                .put(BASE_URL + savedPassCourse.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdatePassCourse_thenNotFound() {
        PassCourse newPassCourse = new PassCourse();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newPassCourse)
                .put(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenDeletePassCourse_thenNoContent() {
        Response response = RestAssured
                .delete(BASE_URL + savedPassCourse.getId());

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
        assertTrue(!passCourseRepository.findAll().iterator().hasNext());
    }

    @Test
    public void whenDeletePassCourse_thenNotFound() {
        Response response = RestAssured
                .delete(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}