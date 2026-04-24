package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.model.Materia;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.model.Turma;
import com.schefer.agenda.repository.AgendaRepository;
import com.schefer.agenda.repository.MateriaRepository;
import com.schefer.agenda.repository.ProfessorRepository;
import com.schefer.agenda.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    public Agenda salvarAgendamento(AgendamentoRequestDTO dto) {
        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada: " + dto.turmaId()));

        Professor professor = professorRepository.findById(dto.professorId())
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado: " + dto.professorId()));

        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada: " + dto.materiaId()));

        Agenda agenda = new Agenda(
                turma,
                materia,
                dto.tipoAula(),
                dto.tipoAgenda(),
                dto.tipoPeriodo(),
                dto.data(),
                professor,
                dto.observacao()
        );

        return agendaRepository.save(agenda);
    }
}
