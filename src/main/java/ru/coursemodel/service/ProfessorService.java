package ru.coursemodel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursemodel.model.Professor;
import ru.coursemodel.repository.ProfessorRepository;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 30.10.2017.
 */
@Service
public class ProfessorService {
    @Resource
    private ProfessorRepository professorRepository;

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public Professor findById(Long id) {
        Professor professor = professorRepository.findOne(id);
        if(professor == null) {
            throw new EntityNotFoundException();
        }
        return professor;
    }

    @Transactional
    public List<Professor> findAllProfessors() {
        List<Professor> list = new ArrayList<>();
        Iterable<Professor> professors = professorRepository.findAll();
        professors.forEach(list::add);
        return list;
    }

    @Transactional(rollbackFor = EntityExistsException.class)
    public Professor createProfessor(Professor professor) {
        if (professor.getId() != null && professorRepository.findOne(professor.getId()) != null) {
            throw new EntityExistsException();
        }
        return professorRepository.save(professor);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public Professor updateProfessor(Professor professor) {
        Professor updatedProfessor = professorRepository.findOne(professor.getId());
        if (updatedProfessor == null) {
            throw new EntityNotFoundException();
        }
        return professorRepository.save(professor);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteProfessor(Long id) {
        Professor professor = professorRepository.findOne(id);
        if(professor == null) {
            throw new EntityNotFoundException();
        }
        professorRepository.delete(professor.getId());
    }
}
