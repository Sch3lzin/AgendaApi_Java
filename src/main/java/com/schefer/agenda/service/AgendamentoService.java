package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.repository.AgendaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    private final AgendaRepository agendaRepository;
    private final VerificarDados verificarDados;

    public AgendamentoService(AgendaRepository agendaRepository,
                              VerificarDados verificarDados) {
        this.agendaRepository = agendaRepository;
        this.verificarDados = verificarDados;
    }

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

        agendaRepository.save(agenda);

        return ResponseEntity.status(HttpStatus.CREATED).body("Agendamento cadastrado com sucesso!");
    }
}
