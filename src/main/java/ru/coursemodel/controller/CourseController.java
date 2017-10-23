package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.coursemodel.model.CourseEntity;
import ru.coursemodel.service.CourseService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<CourseEntity> getCourse(@PathVariable("id") long id) {
        CourseEntity courseEntity;
        try {
            courseEntity = courseService.findById(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(courseEntity);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CourseEntity>> courseList() {
        List<CourseEntity> courseEntities = courseService.findAllCourses();
        return ResponseEntity.ok(courseEntities);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CourseEntity> createCourse(@RequestBody CourseEntity courseEntity, UriComponentsBuilder ucBuilder) {
        CourseEntity course;
        try {
            course = courseService.createCourse(courseEntity);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity
                .created(ucBuilder.path("/courses/{id}").buildAndExpand(course.getId()).toUri())
                .body(course);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<CourseEntity> editCourse(
            @PathVariable("id") long id,
            @RequestBody CourseEntity courseEntity) {
        courseEntity.setId(id);
        try {
            return ResponseEntity.ok(courseService.updateCourse(courseEntity));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CourseEntity> deleteCourse(@PathVariable("id") long id) {
        try {
            courseService.deleteCourse(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
