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

/**
 * Centraliza as validações de negócio reutilizadas pelos services,
 * evitando duplicação de lógica de verificação entre eles.
 */
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

    /** Agrupa as entidades resolvidas necessárias para criar um agendamento */
    public record DadosVerificarAgendamento(Turma turma, Professor professor, Materia materia) {}

    /** Agrupa a turma validada para ser persistida */
    public record DadosVerificarTurma(Turma turma) {}

    /** Agrupa a matéria validada para ser persistida */
    public record DadosVerificarMateria(Materia materia) {}

    /**
     * Valida um agendamento:
     * - Resolve turma, professor e matéria pelo ID (lança EntityNotFoundException se algum não existir)
     * - Garante que não há outro agendamento para o mesmo recurso, data e aula (lança IllegalStateException se houver)
     */
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

    /**
     * Valida a atualização de um agendamento:
     * - Resolve turma, professor e matéria pelo ID (lança EntityNotFoundException se algum não existir)
     * - Garante que não há outro agendamento para o mesmo recurso, data e aula (lança IllegalStateException se houver)
     */
    public DadosVerificarAgendamento verificarExisteAgendamentoParaEdicao(AgendamentoRequestDTO dto, Long idAtual) {
        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada: " + dto.turmaId()));

        Professor professor = professorRepository.findById(dto.professorId())
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado: " + dto.professorId()));

        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada: " + dto.materiaId()));

        boolean jaExisteAgendamento = agendaRepository
                .existsByTipoAgendaAndDataAndTipoAulaAndIdNot(dto.tipoAgenda(), dto.data(), dto.tipoAula(), idAtual);

        if (jaExisteAgendamento) {
            throw new IllegalStateException("Já existe um agendamento para esse recurso nessa data e aula.");
        }

        return new DadosVerificarAgendamento(turma, professor, materia);
    }

    /**
     * Garante que não existe outra turma com a mesma combinação de série e turma
     * antes de retornar a entidade pronta para persistência.
     */
    public DadosVerificarTurma verificarExisteTurma(TurmaRequestDTO dto) {
        boolean jaExisteTurma = turmaRepository.existsBySerieAndTurma(dto.serie(), dto.turma());

        if (jaExisteTurma) {
            throw new IllegalStateException("Ja existe uma turma com esses dados.");
        }

        Turma turma = new Turma(dto.periodo(), dto.serie(), dto.turma());
        return new DadosVerificarTurma(turma);
    }

    /**
     * Garante que não existe outra matéria com o mesmo nome
     * antes de retornar a entidade pronta para persistência.
     */
    public DadosVerificarMateria verificarMateria(MateriaRequestDTO dto) {
        boolean jaExisteMateria = materiaRepository.existsByMateria(dto.materia());

        if (jaExisteMateria) {
            throw new IllegalStateException("Ja existe uma materia com esse nome");
        }

        Materia materia = new Materia(dto.materia());
        return new DadosVerificarMateria(materia);
    }
}
