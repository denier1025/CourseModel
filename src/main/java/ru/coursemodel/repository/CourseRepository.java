package ru.coursemodel.repository;

import org.springframework.data.repository.CrudRepository;
import ru.coursemodel.model.Course;

/**
 * Created by Alexey on 30.10.2017.
 */
public interface CourseRepository extends CrudRepository<Course, Long> {
}
