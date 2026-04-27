package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.dto.ProfRequestDTO;
import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    private List<ProfDTO> converteDados(List<Professor> prof) {
        return prof.stream()
                .map(p -> new ProfDTO(
                        p.getId(),
                        p.getName()
                ))
                .collect(Collectors.toList());
    }


    public List<ProfDTO> exibirProfessores() {
        return converteDados(repository.findAll());
    }

    public Professor salvarProfessor(ProfRequestDTO dto) {
        Professor professor = new Professor(dto.name());
        return repository.save(professor);
    }
}
