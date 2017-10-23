package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.coursemodel.model.PassCourseEntity;
import ru.coursemodel.service.PassCourseService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@RestController
@RequestMapping("/passcourses")
public class PassCourseController {
    @Autowired
    private PassCourseService passCourseService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<PassCourseEntity> getPassCourse(@PathVariable("id") long id) {
        PassCourseEntity passCourseEntity;
        try {
            passCourseEntity = passCourseService.findById(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passCourseEntity);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PassCourseEntity>> passCourseList() {
        List<PassCourseEntity> passCourseEntities = passCourseService.findAllPassCourses();
        return ResponseEntity.ok(passCourseEntities);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PassCourseEntity> createPassCourse(@RequestBody PassCourseEntity passCourseEntity, UriComponentsBuilder ucBuilder) {
        PassCourseEntity passCourse;
        try {
            passCourse = passCourseService.createPassCourse(passCourseEntity);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity
                .created(ucBuilder.path("/passcourses/{id}").buildAndExpand(passCourse.getId()).toUri())
                .body(passCourse);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<PassCourseEntity> editPassCourse(
            @PathVariable("id") long id,
            @RequestBody PassCourseEntity passCourseEntity) {
        passCourseEntity.setId(id);
        try {
            return ResponseEntity.ok(passCourseService.updatePassCourse(passCourseEntity));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<PassCourseEntity> deletePassCourse(@PathVariable("id") long id) {
        try {
            passCourseService.deletePassCourse(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
