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
import ru.coursemodel.model.Student;
import ru.coursemodel.repository.StudentRepository;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexey on 03.11.2017.
 */
public class StudentControllerTests extends CoursemodelApplicationTests {

    private static final long UNKNOWN_ID = Long.MAX_VALUE;
    private static final int PORT = 8081;
    private static final String BASE_URL
            = "http://localhost:" + PORT + "/students/";

    @Autowired
    private StudentRepository studentRepository;

    private Student savedStudent;

    @Before
    public void setUp() {
        savedStudent = new Student();
        savedStudent.setName(randomAlphabetic(10));
        savedStudent = studentRepository.save(savedStudent);
    }

    @After
    public void tearDown() {
        studentRepository.deleteAll();

    }

    @Test
    public void whenGetAllStudents_thenOK() {
        Response response = RestAssured.get(BASE_URL);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() == 1);
    }

    @Test
    public void whenGetOneStudent_thenOK() {
        Response response = RestAssured.get(BASE_URL + savedStudent.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(savedStudent.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenGetOneStudent_thenNotFound() {
        Response response = RestAssured.get(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateStudent_thenCreated() {
        Student newStudent = new Student();
        newStudent.setName(randomAlphabetic(10));

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newStudent)
                .post(BASE_URL);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertEquals(newStudent.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenCreateStudent_thenConflict() {
        Student newStudent = new Student();
        newStudent.setId(savedStudent.getId());

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newStudent)
                .post(BASE_URL);

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateStudent_thenOk() {
        Student newStudent = new Student();
        newStudent.setName(randomAlphabetic(10));

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newStudent)
                .put(BASE_URL + savedStudent.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(newStudent.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenUpdateStudent_thenNotFound() {
        Student newStudent = new Student();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newStudent)
                .put(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenDeleteStudent_thenNoContent() {
        Response response = RestAssured
                .delete(BASE_URL + savedStudent.getId());

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
        assertTrue(!studentRepository.findAll().iterator().hasNext());
    }

    @Test
    public void whenDeleteStudent_thenNotFound() {
        Response response = RestAssured
                .delete(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
