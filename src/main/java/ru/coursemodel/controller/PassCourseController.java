package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.coursemodel.model.PassCourse;
import ru.coursemodel.service.PassCourseService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 30.10.2017.
 */
@RestController
@RequestMapping("/passcourses")
public class PassCourseController {
    @Autowired
    private PassCourseService passCourseService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<PassCourse> getPassCourse(@PathVariable("id") long id) {
        PassCourse passCourse;
        try {
            passCourse = passCourseService.findById(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(passCourse);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PassCourse>> passCourseList() {
        List<PassCourse> passCourses = passCourseService.findAllPassCourses();
        return ResponseEntity.ok(passCourses);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PassCourse> createPassCourse(@RequestBody PassCourse passCourseData, UriComponentsBuilder ucBuilder) {
        PassCourse passCourse;
        try {
            passCourse = passCourseService.createPassCourse(passCourseData);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity
                .created(ucBuilder.path("/passcourses/{id}").buildAndExpand(passCourse.getId()).toUri())
                .body(passCourse);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<PassCourse> editPassCourse(
            @PathVariable("id") long id,
            @RequestBody PassCourse passCourse) {
        passCourse.setId(id);
        try {
            return ResponseEntity.ok(passCourseService.updatePassCourse(passCourse));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<PassCourse> deletePassCourse(@PathVariable("id") long id) {
        try {
            passCourseService.deletePassCourse(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
