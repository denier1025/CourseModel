package ru.coursemodel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursemodel.model.Course;
import ru.coursemodel.repository.CourseRepository;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 30.10.2017.
 */
@Service
public class CourseService {
    @Resource
    private CourseRepository courseRepository;

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public Course findById(Long id) {
        Course course = courseRepository.findOne(id);
        if(course == null) {
            throw new EntityNotFoundException();
        }
        return course;
    }

    @Transactional
    public List<Course> findAllCourses() {
        List<Course> list = new ArrayList<>();
        Iterable<Course> courses = courseRepository.findAll();
        courses.forEach(list::add);
        return list;
    }

    @Transactional(rollbackFor = EntityExistsException.class)
    public Course createCourse(Course course) {
        if (course.getId() != null && courseRepository.findOne(course.getId()) != null) {
            throw new EntityExistsException();
        }
        return courseRepository.save(course);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public Course updateCourse(Course course) {
        Course updatedCourse = courseRepository.findOne(course.getId());
        if (updatedCourse == null) {
            throw new EntityNotFoundException();
        }
        return courseRepository.save(course);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteCourse(Long id) {
        Course course = courseRepository.findOne(id);
        if(course == null) {
            throw new EntityNotFoundException();
        }
        courseRepository.delete(course.getId());
    }
}
