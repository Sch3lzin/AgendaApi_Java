package com.schefer.agenda.controller;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class ExibirAgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/informatica")
    public List<AgendaDTO> exibirAgendaInformatica() {
        return agendaService.exibirAgendaInformatica();
    }

    @GetMapping("/auditorio")
    public List<AgendaDTO> exibirAgendaAuditorio() {
        return agendaService.exibirAgendaAuditorio();
    }

    @GetMapping("/tablet")
    public List<AgendaDTO> exibirAgendaTablet() {
        return agendaService.exibirAgendaTablet();
    }
}
