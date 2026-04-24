package com.schefer.agenda.controller;

import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.service.AgendamentoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public Agenda criarAgendamento(@RequestBody AgendamentoRequestDTO dto) {
        return agendamentoService.salvarAgendamento(dto);
    }
}
