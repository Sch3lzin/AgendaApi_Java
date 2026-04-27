package com.schefer.agenda.controller;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.service.AgendamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public ResponseEntity<AgendaDTO> criarAgendamento(@RequestBody AgendamentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.salvarAgendamento(dto));
    }
}
