package com.schefer.agenda.service;

import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.dto.TurmaDTO;
import com.schefer.agenda.dto.TurmaRequestDTO;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.model.Turma;
import com.schefer.agenda.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository repository;

    private List<TurmaDTO> converteDados(List<Turma> turma) {
        return turma.stream()
                .map(t -> new TurmaDTO(
                        t.getId(),
                        t.getTipoPeriodo(),
                        t.getSerie(),
                        t.getTurma()
                ))
                .collect(Collectors.toList());
    }

    public List<TurmaDTO> exibirTurma() {
        return converteDados(repository.findAll());
    }

    public Turma salvarTurma(TurmaRequestDTO dto) {
        Turma turma = new Turma(dto.periodo(), dto.serie(), dto.turma());
        return repository.save(turma);
    }
}
