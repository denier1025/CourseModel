package ru.coursemodel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursemodel.model.Student;
import ru.coursemodel.repository.StudentRepository;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 30.10.2017.
 */
@Service
public class StudentService {
    @Resource
    private StudentRepository studentRepository;

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public Student findById(Long id) {
        Student student = studentRepository.findOne(id);
        if(student == null) {
            throw new EntityNotFoundException();
        }
        return student;
    }

    @Transactional
    public List<Student> findAllStudents() {
        List<Student> list = new ArrayList<>();
        Iterable<Student> student = studentRepository.findAll();
        student.forEach(list::add);
        return list;
    }

    @Transactional(rollbackFor = EntityExistsException.class)
    public Student createStudent(Student student) {
        if (student.getId() != null && studentRepository.findOne(student.getId()) != null) {
            throw new EntityExistsException();
        }
        return studentRepository.save(student);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public Student updateStudent(Student student) {
        Student updatedStudent = studentRepository.findOne(student.getId());
        if (updatedStudent == null) {
            throw new EntityNotFoundException();
        }
        return studentRepository.save(student);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteStudent(Long id) {
        Student student = studentRepository.findOne(id);
        if(student == null) {
            throw new EntityNotFoundException();
        }
        studentRepository.delete(student.getId());
    }
}
