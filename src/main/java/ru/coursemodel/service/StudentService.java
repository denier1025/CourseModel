package ru.coursemodel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursemodel.model.StudentEntity;
import ru.coursemodel.repository.StudentRepository;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@Service
public class StudentService {
    @Resource
    private StudentRepository studentRepository;

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public StudentEntity findById(Long id) {
        StudentEntity studentEntity = studentRepository.findOne(id);
        if(studentEntity == null) {
            throw new EntityNotFoundException();
        }
        return studentEntity;
    }

    @Transactional
    public List<StudentEntity> findAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(rollbackFor = EntityExistsException.class)
    public StudentEntity createStudent(StudentEntity studentEntity) {
        if (studentEntity.getId() != null && studentRepository.findOne(studentEntity.getId()) != null) {
            throw new EntityExistsException();
        }
        return studentRepository.save(studentEntity);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public StudentEntity updateStudent(StudentEntity studentEntity) {
        StudentEntity updatedStudent = studentRepository.findOne(studentEntity.getId());
        if (updatedStudent == null) {
            throw new EntityNotFoundException();
        }
        return studentRepository.save(studentEntity);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteStudent(Long id) {
        StudentEntity studentEntity = studentRepository.findOne(id);
        if(studentEntity == null) {
            throw new EntityNotFoundException();
        }
        studentRepository.delete(studentEntity.getId());
    }
}
