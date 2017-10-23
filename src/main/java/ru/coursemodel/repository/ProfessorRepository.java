package ru.coursemodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coursemodel.model.ProfessorEntity;

/**
 * Created by Alexey on 22.10.2017.
 */
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {
}
