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
import ru.coursemodel.model.Professor;
import ru.coursemodel.repository.ProfessorRepository;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alexey on 03.11.2017.
 */
public class ProfessorControllerTests extends CoursemodelApplicationTests {

    private static final long UNKNOWN_ID = Long.MAX_VALUE;
    private static final int PORT = 8081;
    private static final String BASE_URL
            = "http://localhost:" + PORT + "/professors/";

    @Autowired
    private ProfessorRepository professorRepository;

    private Professor savedProfessor;

    @Before
    public void setUp() {
        savedProfessor = new Professor();
        savedProfessor.setName(randomAlphabetic(10));
        savedProfessor = professorRepository.save(savedProfessor);
    }

    @After
    public void tearDown() {
        professorRepository.deleteAll();

    }

    @Test
    public void whenGetAllProfessors_thenOK() {
        Response response = RestAssured.get(BASE_URL);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() == 1);
    }

    @Test
    public void whenGetOneProfessor_thenOK() {
        Response response = RestAssured.get(BASE_URL + savedProfessor.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(savedProfessor.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenGetOneProfessor_thenNotFound() {
        Response response = RestAssured.get(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateProfessor_thenCreated() {
        Professor newProfessor = new Professor();
        newProfessor.setName(randomAlphabetic(10));

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProfessor)
                .post(BASE_URL);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertEquals(newProfessor.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenCreateProfessor_thenConflict() {
        Professor newProfessor = new Professor();
        newProfessor.setId(savedProfessor.getId());

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProfessor)
                .post(BASE_URL);

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateProfessor_thenOk() {
        Professor newProfessor = new Professor();
        newProfessor.setName(randomAlphabetic(10));

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProfessor)
                .put(BASE_URL + savedProfessor.getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(newProfessor.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenUpdateProfessor_thenNotFound() {
        Professor newProfessor = new Professor();

        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(newProfessor)
                .put(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenDeleteProfessor_thenNoContent() {
        Response response = RestAssured
                .delete(BASE_URL + savedProfessor.getId());

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
        assertTrue(!professorRepository.findAll().iterator().hasNext());
    }

    @Test
    public void whenDeleteProfessor_thenNotFound() {
        Response response = RestAssured
                .delete(BASE_URL + UNKNOWN_ID);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
