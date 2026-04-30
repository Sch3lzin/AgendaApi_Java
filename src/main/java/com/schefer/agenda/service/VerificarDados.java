package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.dto.MateriaRequestDTO;
import com.schefer.agenda.dto.TurmaRequestDTO;
import com.schefer.agenda.model.Materia;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.model.Turma;
import com.schefer.agenda.repository.AgendaRepository;
import com.schefer.agenda.repository.MateriaRepository;
import com.schefer.agenda.repository.ProfessorRepository;
import com.schefer.agenda.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VerificarDados {

    private final AgendaRepository agendaRepository;
    private final MateriaRepository materiaRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaRepository turmaRepository;

    public VerificarDados(AgendaRepository agendaRepository,
                                MateriaRepository materiaRepository,
                                ProfessorRepository professorRepository,
                                TurmaRepository turmaRepository) {
        this.agendaRepository = agendaRepository;
        this.materiaRepository = materiaRepository;
        this.professorRepository = professorRepository;
        this.turmaRepository = turmaRepository;
    }

    public record DadosVerificarAgendamento(Turma turma, Professor professor, Materia materia) {}
    public record DadosVerificarTurma(Turma turma) {}
    public record DadosVerificarMateria(Materia materia) {}

    public DadosVerificarAgendamento verificarExisteAgendamento(AgendamentoRequestDTO dto) {
        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada: " + dto.turmaId()));

        Professor professor = professorRepository.findById(dto.professorId())
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado: " + dto.professorId()));

        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada: " + dto.materiaId()));

        boolean jaExisteAgendamento = agendaRepository.existsByTipoAgendaAndDataAndTipoAula(dto.tipoAgenda(), dto.data(), dto.tipoAula());

        if (jaExisteAgendamento) {
            throw new IllegalStateException("Já existe um agendamento para esse recurso nessa data e aula.");
        }

        return new DadosVerificarAgendamento(turma, professor, materia);
    }

    public DadosVerificarTurma verificarExisteTurma(TurmaRequestDTO dto) {
        boolean jaExisteTurma = turmaRepository.existsBySerieAndTurma(dto.serie(), dto.turma());

        if (jaExisteTurma) {
            throw new IllegalStateException("Ja existe uma turma com esses dados.");
        }

        Turma turma = new Turma(dto.periodo(), dto.serie(), dto.turma());
        return new DadosVerificarTurma(turma);
    }

    public DadosVerificarMateria verificarMateria(MateriaRequestDTO dto) {
        boolean jaExisteMateria = materiaRepository.existsByMateria(dto.materia());

        if (jaExisteMateria) {
            throw new IllegalStateException("Ja existe uma materia com esse nome");
        }

        Materia materia = new Materia(dto.materia());
        return new DadosVerificarMateria(materia);
    }
}
