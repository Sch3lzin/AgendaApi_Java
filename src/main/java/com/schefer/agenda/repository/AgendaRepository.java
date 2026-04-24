package com.schefer.agenda.repository;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    List<Agenda> findByTipoAgenda(TipoAgenda tipoAgenda);
}
