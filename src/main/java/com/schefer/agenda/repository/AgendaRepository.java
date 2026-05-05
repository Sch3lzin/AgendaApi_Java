package com.schefer.agenda.repository;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.enums.TipoAula;
import com.schefer.agenda.enums.TipoPeriodo;
import com.schefer.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    boolean existsByTipoAgendaAndDataAndTipoAula(TipoAgenda tipoAgenda, LocalDate data, TipoAula tipoAula);

    List<Agenda> findByTipoAgendaAndDataGreaterThanEqual(TipoAgenda tipoAgenda, LocalDate data);
}
