package com.schefer.agenda.service;

import com.schefer.agenda.dto.AgendaDTO;
import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.repository.AgendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaService {

    private final AgendaRepository repository;

    public AgendaService(AgendaRepository repository) {
        this.repository = repository;
    }

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
