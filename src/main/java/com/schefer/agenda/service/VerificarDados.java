package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.dto.MateriaRequestDTO;
import com.schefer.agenda.dto.TurmaRequestDTO;
import com.schefer.agenda.enums.TipoUsuario;
import com.schefer.agenda.exception.SemPermissaoException;
import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.model.Materia;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.model.Turma;
import com.schefer.agenda.repository.AgendaRepository;
import com.schefer.agenda.repository.MateriaRepository;
import com.schefer.agenda.repository.ProfessorRepository;
import com.schefer.agenda.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public DadosVerificarAgendamento verificarExisteAgendamento(AgendamentoRequestDTO dto, Long id) {
        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada: " + dto.turmaId()));

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado: " + id));

        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada: " + dto.materiaId()));

        boolean jaExisteAgendamento = agendaRepository.existsByTipoAgendaAndDataAndTipoAula(dto.tipoAgenda(), dto.data(), dto.tipoAula());

        if (jaExisteAgendamento) {
            throw new IllegalStateException("Já existe um agendamento para esse recurso nessa data e aula!");
        }

        return new DadosVerificarAgendamento(turma, professor, materia);
    }

    /**
     * Valida a atualização de um agendamento:
     * - Resolve turma, professor e matéria pelo ID (lança EntityNotFoundException se algum não existir)
     * - Garante que não há outro agendamento para o mesmo recurso, data e aula (lança IllegalStateException se houver)
     */
    public DadosVerificarAgendamento verificarExisteAgendamentoParaEdicao(AgendamentoRequestDTO dto, Long idAtual, Long professorId) {
        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada: " + dto.turmaId()));

        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado: " + professorId));

        Materia materia = materiaRepository.findById(dto.materiaId())
                .orElseThrow(() -> new EntityNotFoundException("Matéria não encontrada: " + dto.materiaId()));

        boolean jaExisteAgendamento = agendaRepository
                .existsByTipoAgendaAndDataAndTipoAulaAndIdNot(dto.tipoAgenda(), dto.data(), dto.tipoAula(), idAtual);

        if (jaExisteAgendamento) {
            throw new IllegalStateException("Já existe um agendamento para esse recurso nessa data e aula!");
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
            throw new IllegalStateException("Ja existe uma turma com esses dados!");
        }

        Turma turma = new Turma(dto.periodo(), dto.serie(), dto.turma());
        return new DadosVerificarTurma(turma);
    }

    /**
     * Valida a atualização de uma turma:
     * - Garante que não existe outra turma com a mesma combinação de série e turma
     * antes de retornar a entidade pronta para persistência.
     */
    public DadosVerificarTurma verificarExisteTurmaEdicao(TurmaRequestDTO dto, Long idAtual) {
        boolean jaExisteTurma = turmaRepository.existsBySerieAndTurmaAndIdNot(dto.serie(), dto.turma(), idAtual);

        if (jaExisteTurma) {
            throw new IllegalStateException("Ja existe uma turma com esses dados!");
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
            throw new IllegalStateException("Ja existe uma materia com esse nome!");
        }

        Materia materia = new Materia(dto.materia());
        return new DadosVerificarMateria(materia);
    }

    /**
     * Valida a atualização de uma matéria:
     * - Garante que não existe outra matéria com o mesmo nome, ignorando a própria
     *   antes de retornar a entidade pronta para persistência.
     */
    public DadosVerificarMateria verificarMateriaEdicao(MateriaRequestDTO dto, Long id) {
        boolean jaExisteMateria = materiaRepository.existsByMateriaAndIdNot(dto.materia(), id);

        if (jaExisteMateria) {
            throw new IllegalStateException("Ja existe uma materia com esse nome!");
        }

        Materia materia = new Materia(dto.materia());
        return new DadosVerificarMateria(materia);
    }

    /**
     * Verifica se o usuário logado tem permissão para modificar o agendamento.
     * Só o criador, SECRETARIO ou ADMIN podem editar/deletar.
     * Lança SemPermissaoException se não tiver permissão.
     */
    public void verificarPermissaoAgendamento(Agenda agenda) {
        Long idUsuarioLogado = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().iterator().next().getAuthority();

        boolean ehCriador = agenda.getProfessor().getId().equals(idUsuarioLogado);
        boolean temPermissao = role.equals("ROLE_ADMIN") || role.equals("ROLE_SECRETARIO");

        if (!ehCriador && !temPermissao) {
            throw new SemPermissaoException("Sem permissão nesse agendamento!");
        }
    }

    /**
     * Verifica se o usuário logado tem permissão para atualizar o professor.
     * Lança IllegalStateException se não tiver permissão.
     */
    public void verificarPermissaoProfessor(Long id) {
        Long idUsuarioLogado = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String role = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities()
                .iterator().next().getAuthority();

        boolean ehOProprio = idUsuarioLogado.equals(id);

        if (!ehOProprio) {
            throw new SemPermissaoException("Sem permissão para atualizar esse professor!");
        }
    }

    /**
     * Verifica se o usuário logado tem permissão para criar novos usuários.
     * - ADMIN pode criar qualquer permissão.
     * - SECRETARIO pode criar SECRETARIO e PROFESSOR, mas não ADMIN.
     * - PROFESSOR não pode criar ninguém.
     * Lança SemPermissaoException se não tiver permissão.
     */
    public void verificarPermissaoCriarProfessor(TipoUsuario permissaoDoCriando) {
        String role = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities()
                .iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return;
        }

        if (role.equals("ROLE_SECRETARIO")) {
            if (permissaoDoCriando == TipoUsuario.ADMIN) {
                throw new SemPermissaoException("Secretário não pode criar um Admin!");
            }
            return;
        }

        throw new SemPermissaoException("Sem permissão para criar usuários!");
    }
}
