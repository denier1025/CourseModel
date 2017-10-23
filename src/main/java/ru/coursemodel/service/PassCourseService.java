package ru.coursemodel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursemodel.model.PassCourseEntity;
import ru.coursemodel.repository.PassCourseRepository;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@Service
public class PassCourseService {
    @Resource
    private PassCourseRepository passCourseRepository;

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public PassCourseEntity findById(Long id) {
        PassCourseEntity passCourseEntity = passCourseRepository.findOne(id);
        if(passCourseEntity == null) {
            throw new EntityNotFoundException();
        }
        return passCourseEntity;
    }

    @Transactional
    public List<PassCourseEntity> findAllPassCourses() {
        return passCourseRepository.findAll();
    }

    @Transactional(rollbackFor = EntityExistsException.class)
    public PassCourseEntity createPassCourse(PassCourseEntity passCourseEntity) {
        if (passCourseEntity.getId() != null && passCourseRepository.findOne(passCourseEntity.getId()) != null) {
            throw new EntityExistsException();
        }
        return passCourseRepository.save(passCourseEntity);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public PassCourseEntity updatePassCourse(PassCourseEntity passCourseEntity) {
        PassCourseEntity updatedPassCourse = passCourseRepository.findOne(passCourseEntity.getId());
        if (updatedPassCourse == null) {
            throw new EntityNotFoundException();
        }
        return passCourseRepository.save(passCourseEntity);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deletePassCourse(Long id) {
        PassCourseEntity passCourseEntity = passCourseRepository.findOne(id);
        if(passCourseEntity == null) {
            throw new EntityNotFoundException();
        }
        passCourseRepository.delete(passCourseEntity.getId());
    }
}
