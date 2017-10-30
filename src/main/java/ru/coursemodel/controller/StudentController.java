package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.coursemodel.model.Student;
import ru.coursemodel.service.StudentService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 30.10.2017.
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudent(@PathVariable("id") long id) {
        Student student;
        try {
            student = studentService.findById(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Student>> studentList() {
        List<Student> students = studentService.findAllStudents();
        return ResponseEntity.ok(students);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Student> createStudent(@RequestBody Student studentData, UriComponentsBuilder ucBuilder) {
        Student student;
        try {
            student = studentService.createStudent(studentData);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity
                .created(ucBuilder.path("/students/{id}").buildAndExpand(student.getId()).toUri())
                .body(student);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Student> editStudent(
            @PathVariable("id") long id,
            @RequestBody Student student) {
        student.setId(id);
        try {
            return ResponseEntity.ok(studentService.updateStudent(student));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") long id) {
        try {
            studentService.deleteStudent(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
