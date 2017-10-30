package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.coursemodel.model.Course;
import ru.coursemodel.service.CourseService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 30.10.2017.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Course> getCourse(@PathVariable("id") long id) {
        Course course;
        try {
            course = courseService.findById(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(course);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Course>> courseList() {
        List<Course> courses = courseService.findAllCourses();
        return ResponseEntity.ok(courses);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Course> createCourse(@RequestBody Course courseData, UriComponentsBuilder ucBuilder) {
        Course course;
        try {
            course = courseService.createCourse(courseData);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity
                .created(ucBuilder.path("/courses/{id}").buildAndExpand(course.getId()).toUri())
                .body(course);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Course> editCourse(
            @PathVariable("id") long id,
            @RequestBody Course course) {
        course.setId(id);
        try {
            return ResponseEntity.ok(courseService.updateCourse(course));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Course> deleteCourse(@PathVariable("id") long id) {
        try {
            courseService.deleteCourse(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
