package ru.coursemodel.repository;

import org.springframework.data.repository.CrudRepository;
import ru.coursemodel.model.PassCourse;

/**
 * Created by Alexey on 30.10.2017.
 */
public interface PassCourseRepository extends CrudRepository<PassCourse, Long> {
}
