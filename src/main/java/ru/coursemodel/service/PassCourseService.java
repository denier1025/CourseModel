package ru.coursemodel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursemodel.model.PassCourse;
import ru.coursemodel.repository.PassCourseRepository;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 30.10.2017.
 */
@Service
public class PassCourseService {
    @Resource
    private PassCourseRepository passCourseRepository;

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public PassCourse findById(Long id) {
        PassCourse passCourse = passCourseRepository.findOne(id);
        if(passCourse == null) {
            throw new EntityNotFoundException();
        }
        return passCourse;
    }

    @Transactional
    public List<PassCourse> findAllPassCourses() {
        List<PassCourse> list = new ArrayList<>();
        Iterable<PassCourse> passCourses = passCourseRepository.findAll();
        passCourses.forEach(list::add);
        return list;
    }

    @Transactional(rollbackFor = EntityExistsException.class)
    public PassCourse createPassCourse(PassCourse passCourse) {
        if (passCourse.getId() != null && passCourseRepository.findOne(passCourse.getId()) != null) {
            throw new EntityExistsException();
        }
        return passCourseRepository.save(passCourse);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public PassCourse updatePassCourse(PassCourse passCourse) {
        PassCourse updatedPassCourse = passCourseRepository.findOne(passCourse.getId());
        if (updatedPassCourse == null) {
            throw new EntityNotFoundException();
        }
        return passCourseRepository.save(passCourse);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deletePassCourse(Long id) {
        PassCourse passCourse = passCourseRepository.findOne(id);
        if(passCourse == null) {
            throw new EntityNotFoundException();
        }
        passCourseRepository.delete(passCourse.getId());
    }
}
