package ru.coursemodel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursemodel.model.ProfessorEntity;
import ru.coursemodel.repository.ProfessorRepository;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@Service
public class ProfessorService {
    @Resource
    private ProfessorRepository professorRepository;

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public ProfessorEntity findById(Long id) {
        ProfessorEntity professorEntity = professorRepository.findOne(id);
        if(professorEntity == null) {
            throw new EntityNotFoundException();
        }
        return professorEntity;
    }

    @Transactional
    public List<ProfessorEntity> findAllProfessors() {
        return professorRepository.findAll();
    }

    @Transactional(rollbackFor = EntityExistsException.class)
    public ProfessorEntity createProfessor(ProfessorEntity professorEntity) {
        if (professorEntity.getId() != null && professorRepository.findOne(professorEntity.getId()) != null) {
            throw new EntityExistsException();
        }
        return professorRepository.save(professorEntity);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public ProfessorEntity updateProfessor(ProfessorEntity professorEntity) {
        ProfessorEntity updatedProfessor = professorRepository.findOne(professorEntity.getId());
        if (updatedProfessor == null) {
            throw new EntityNotFoundException();
        }
        return professorRepository.save(professorEntity);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteProfessor(Long id) {
        ProfessorEntity professorEntity = professorRepository.findOne(id);
        if(professorEntity == null) {
            throw new EntityNotFoundException();
        }
        professorRepository.delete(professorEntity.getId());
    }
}
