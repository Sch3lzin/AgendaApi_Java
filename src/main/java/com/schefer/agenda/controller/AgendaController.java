package com.schefer.agenda.controller;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.service.AgendaService;
import com.schefer.agenda.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final AgendamentoService agendamentoService;
    private final AgendaService agendaService;

    public AgendaController(AgendamentoService agendamentoService, AgendaService agendaService) {
        this.agendamentoService = agendamentoService;
        this.agendaService = agendaService;
    }

    // METODOS GET

    @GetMapping("/informatica")
    public ResponseEntity<List<AgendaDTO>> exibirAgendaInformatica() {
        return ResponseEntity.ok(agendaService.exibirAgendaInformatica());
    }

    @GetMapping("/auditorio")
    public ResponseEntity<List<AgendaDTO>> exibirAgendaAuditorio() {
        return ResponseEntity.ok(agendaService.exibirAgendaAuditorio());
    }

    @GetMapping("/tablet")
    public ResponseEntity<List<AgendaDTO>> exibirAgendaTablet() {
        return ResponseEntity.ok(agendaService.exibirAgendaTablet());
    }

    // METODOS POST

    @PostMapping
    public ResponseEntity<String> criarAgendamento(@RequestBody @Valid AgendamentoRequestDTO dto) {
        return agendamentoService.salvarAgendamento(dto);
    }
}
