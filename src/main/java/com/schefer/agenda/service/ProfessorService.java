package com.schefer.agenda.service;

import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.dto.ProfRequestDTO;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    private List<ProfDTO> converteDados(List<Professor> prof) {
        return prof.stream()
                .map(p -> new ProfDTO(p.getId(), p.getName()))
                .toList();
    }

    public List<ProfDTO> exibirProfessores() {
        return converteDados(repository.findAll());
    }

    public ProfDTO salvarProfessor(ProfRequestDTO dto) {
        Professor professor = new Professor(dto.name());
        Professor salvo = repository.save(professor);
        return new ProfDTO(salvo.getId(), salvo.getName());
    }
}
