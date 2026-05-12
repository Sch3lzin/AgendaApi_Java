package com.schefer.agenda.controller;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.dto.AgendamentoRequestDTO;
import com.schefer.agenda.service.AgendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

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

    @PostMapping
    public ResponseEntity<String> criarAgendamento(@RequestBody @Valid AgendamentoRequestDTO dto) {
        return agendaService.salvarAgendamento(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAgendamento(@PathVariable @Valid Long id) {
        return agendaService.deletarAgendamento(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAgendamento(@PathVariable @Valid Long id, @RequestBody @Valid AgendamentoRequestDTO dto) {
        return agendaService.atualizarAgendamento(id, dto);
    }
}
