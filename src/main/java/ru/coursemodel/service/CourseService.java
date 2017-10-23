package ru.coursemodel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursemodel.model.CourseEntity;
import ru.coursemodel.repository.CourseRepository;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@Service
public class CourseService {
    @Resource
    private CourseRepository courseRepository;

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public CourseEntity findById(Long id) {
        CourseEntity courseEntity = courseRepository.findOne(id);
        if(courseEntity == null) {
            throw new EntityNotFoundException();
        }
        return courseEntity;
    }

    @Transactional
    public List<CourseEntity> findAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(rollbackFor = EntityExistsException.class)
    public CourseEntity createCourse(CourseEntity courseEntity) {
        if (courseEntity.getId() != null && courseRepository.findOne(courseEntity.getId()) != null) {
            throw new EntityExistsException();
        }
        return courseRepository.save(courseEntity);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public CourseEntity updateCourse(CourseEntity courseEntity) {
        CourseEntity updatedCourse = courseRepository.findOne(courseEntity.getId());
        if (updatedCourse == null) {
            throw new EntityNotFoundException();
        }
        return courseRepository.save(courseEntity);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteCourse(Long id) {
        CourseEntity courseEntity = courseRepository.findOne(id);
        if(courseEntity == null) {
            throw new EntityNotFoundException();
        }
        courseRepository.delete(courseEntity.getId());
    }
}
