package ru.coursemodel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.coursemodel.model.StudentEntity;
import ru.coursemodel.service.StudentService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<StudentEntity> getStudent(@PathVariable("id") long id) {
        StudentEntity studentEntity;
        try {
            studentEntity = studentService.findById(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentEntity);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<StudentEntity>> studentList() {
        List<StudentEntity> studentEntities = studentService.findAllStudents();
        return ResponseEntity.ok(studentEntities);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentEntity studentEntity, UriComponentsBuilder ucBuilder) {
        StudentEntity student;
        try {
            student = studentService.createStudent(studentEntity);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity
                .created(ucBuilder.path("/students/{id}").buildAndExpand(student.getId()).toUri())
                .body(student);

    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<StudentEntity> editStudent(
            @PathVariable("id") long id,
            @RequestBody StudentEntity studentEntity) {
        studentEntity.setId(id);
        try {
            return ResponseEntity.ok(studentService.updateStudent(studentEntity));
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<StudentEntity> deleteStudent(@PathVariable("id") long id) {
        try {
            studentService.deleteStudent(id);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
