package ru.coursemodel.repository;

import org.springframework.data.repository.CrudRepository;
import ru.coursemodel.model.Professor;

/**
 * Created by Alexey on 30.10.2017.
 */
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
}
