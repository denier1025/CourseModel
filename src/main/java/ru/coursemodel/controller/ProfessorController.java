package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.coursemodel.model.Professor;
import ru.coursemodel.service.ProfessorService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 30.10.2017.
 */
@RestController
@RequestMapping("/professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Professor> getProfessor(@PathVariable("id") long id) {
        Professor professor;
        try {
            professor = professorService.findById(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(professor);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Professor>> professorList() {
        List<Professor> professors = professorService.findAllProfessors();
        return ResponseEntity.ok(professors);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor professorData, UriComponentsBuilder ucBuilder) {
        Professor professor;
        try {
            professor = professorService.createProfessor(professorData);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity
                .created(ucBuilder.path("/professors/{id}").buildAndExpand(professor.getId()).toUri())
                .body(professor);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Professor> editProfessor(
            @PathVariable("id") long id,
            @RequestBody Professor professor) {
        professor.setId(id);
        try {
            return ResponseEntity.ok(professorService.updateProfessor(professor));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Professor> deleteProfessor(@PathVariable("id") long id) {
        try {
            professorService.deleteProfessor(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
