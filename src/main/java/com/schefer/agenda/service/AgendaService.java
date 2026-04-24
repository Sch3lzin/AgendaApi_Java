package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository repository;

    private List<AgendaDTO> converteDados(List<Agenda> series) {
        return series.stream()
                .map(s -> new AgendaDTO(s.getId(), s.getTurma(), s.getTipoAula(), s.getTipoAgenda(), s.getTipoPeriodo(), s.getData(), s.getProfessor()))
                .collect(Collectors.toList());
    }

    public List<AgendaDTO> exibirAgendaInfomatica() {
        return converteDados(repository.exibirAgendaInformatica());
    }


    public List<AgendaDTO> exibirAgendaAuditorio() {
        return converteDados(repository.exibirAgendaAuditorio());
    }

    public List<AgendaDTO> exibirAgendaTablet() {
        return converteDados(repository.exibirAgendaTablet());
    }
}
