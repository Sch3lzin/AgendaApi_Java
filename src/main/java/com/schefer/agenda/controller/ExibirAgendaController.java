package com.schefer.agenda.controller;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.service.AgendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class ExibirAgendaController {

    private final AgendaService agendaService;

    public ExibirAgendaController(AgendaService agendaService) {
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
}
