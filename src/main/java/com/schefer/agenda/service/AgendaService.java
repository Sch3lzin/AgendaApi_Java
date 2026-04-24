package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.enums.TipoAgenda;
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
                .collect(Collectors.toList());
    }

    public List<AgendaDTO> exibirAgendaInformatica() {
        return converteDados(repository.findByTipoAgenda(TipoAgenda.SALA_INFORMATICA));
    }

    public List<AgendaDTO> exibirAgendaAuditorio() {
        return converteDados(repository.findByTipoAgenda(TipoAgenda.AUDITORIO));
    }

    public List<AgendaDTO> exibirAgendaTablet() {
        return converteDados(repository.findByTipoAgenda(TipoAgenda.TABLET));
    }
}
