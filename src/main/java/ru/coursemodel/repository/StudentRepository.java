package ru.coursemodel.repository;

import org.springframework.data.repository.CrudRepository;
import ru.coursemodel.model.Student;

/**
 * Created by Alexey on 30.10.2017.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
}
