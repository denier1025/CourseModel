package ru.coursemodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursemodel.model.StudentEntity;

/**
 * Created by Alexey on 22.10.2017.
 */
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
