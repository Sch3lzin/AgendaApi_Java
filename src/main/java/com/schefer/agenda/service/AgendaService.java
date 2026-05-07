package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.repository.AgendaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AgendaService {

    private final AgendaRepository repository;
    private final VerificarDados verificarDados;

    public AgendaService(AgendaRepository repository, VerificarDados verificarDados) {
        this.repository = repository;
        this.verificarDados = verificarDados;
    }

    /** Converte entidades Agenda para o DTO de resposta */
    private List<AgendaDTO> converteDados(List<Agenda> agendamentos) {
        return agendamentos.stream()
                .map(a -> new AgendaDTO(
                        a.getId(),
                        a.getTurma(),
                        a.getMateria(),
                        a.getTipoAula(),
                        a.getTipoAgenda(),
                        a.getTipoPeriodo(),
                        a.getData(),
                        a.getProfessor(),
                        a.getObservacao()
                ))
                .toList();
    }



    /** Retorna agendamentos futuros (a partir de hoje) da sala de informática */
    public List<AgendaDTO> exibirAgendaInformatica() {
        LocalDate data = LocalDate.now();
        return converteDados(repository.findByTipoAgendaAndDataGreaterThanEqual(TipoAgenda.SALA_INFORMATICA, data));
    }

    /** Retorna agendamentos futuros (a partir de hoje) do auditório */
    public List<AgendaDTO> exibirAgendaAuditorio() {
        LocalDate data = LocalDate.now();
        return converteDados(repository.findByTipoAgendaAndDataGreaterThanEqual(TipoAgenda.AUDITORIO, data));
    }

    /** Retorna agendamentos futuros (a partir de hoje) dos tablets */
    public List<AgendaDTO> exibirAgendaTablet() {
        LocalDate data = LocalDate.now();
        return converteDados(repository.findByTipoAgendaAndDataGreaterThanEqual(TipoAgenda.TABLET, data));
    }

    /**
     * Cria um novo agendamento após validar que não existe conflito
     * de recurso, data e aula no banco.'
     */
    public ResponseEntity<String> salvarAgendamento(AgendamentoRequestDTO dto) {
        VerificarDados.DadosVerificarAgendamento dados = verificarDados.verificarExisteAgendamento(dto);

        Agenda agenda = new Agenda(
                dados.turma(),
                dados.materia(),
                dto.tipoAula(),
                dto.tipoAgenda(),
                dto.tipoPeriodo(),
                dto.data(),
                dados.professor(),
                dto.observacao()
        );

        repository.save(agenda);

        return ResponseEntity.status(HttpStatus.CREATED).body("Agendamento cadastrado com sucesso!");
    }

    /** Remove um agendamento pelo ID; lança exceção se não encontrado */
    public ResponseEntity<String> deletarAgendamento(@Valid Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("Agendamento não encontrado");
        }

        repository.deleteById(id);

        return ResponseEntity.ok("Agendamento deletado com sucesso");
    }

    /**
     *  Atualiza dados de um agendamento, usa id para acha-lo no banco;
     *  Lança exceção se não encontrada;
     *  Persiste dados após validar que não existe outro com os mesmos
     *  dados no banco
     */
    public ResponseEntity<String> atualizarAgendamento(@Valid Long id, @Valid AgendamentoRequestDTO dto) {

        Agenda agenda = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Agendamento não encontrado"));

        VerificarDados.DadosVerificarAgendamento dados = verificarDados.verificarExisteAgendamentoParaEdicao(dto, id);

        agenda.atualizarDados(
                dados.turma(),
                dados.materia(),
                dto.tipoAula(),
                dto.tipoAgenda(),
                dto.tipoPeriodo(),
                dto.data(),
                dados.professor(),
                dto.observacao()
        );

        repository.save(agenda);

        return ResponseEntity.ok("Agendamento atualizado com sucesso!");
    }
}
