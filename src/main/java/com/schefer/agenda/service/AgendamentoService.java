package com.schefer.agenda.service;

import com.schefer.agenda.model.Agenda;
import com.schefer.agenda.repository.AgendamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentosRepository repository;

    public Agenda salvarAgendamento(Agenda agenda) {
        return repository.save(agenda);
    }
}
