package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.coursemodel.model.ProfessorEntity;
import ru.coursemodel.service.ProfessorService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@RestController
@RequestMapping("/professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<ProfessorEntity> getProfessor(@PathVariable("id") long id) {
        ProfessorEntity professorEntity;
        try {
            professorEntity = professorService.findById(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(professorEntity);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProfessorEntity>> professorList() {
        List<ProfessorEntity> professorEntities = professorService.findAllProfessors();
        return ResponseEntity.ok(professorEntities);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProfessorEntity> createProfessor(@RequestBody ProfessorEntity professorEntity, UriComponentsBuilder ucBuilder) {
        ProfessorEntity professor;
        try {
            professor = professorService.createProfessor(professorEntity);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity
                .created(ucBuilder.path("/professors/{id}").buildAndExpand(professor.getId()).toUri())
                .body(professor);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProfessorEntity> editProfessor(
            @PathVariable("id") long id,
            @RequestBody ProfessorEntity professorEntity) {
        professorEntity.setId(id);
        try {
            return ResponseEntity.ok(professorService.updateProfessor(professorEntity));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ProfessorEntity> deleteProfessor(@PathVariable("id") long id) {
        try {
            professorService.deleteProfessor(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
