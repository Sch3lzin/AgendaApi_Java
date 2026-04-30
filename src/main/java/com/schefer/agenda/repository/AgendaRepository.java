package com.schefer.agenda.repository;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.enums.TipoAula;
import com.schefer.agenda.enums.TipoPeriodo;
import com.schefer.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    List<Agenda> findByTipoAgenda(TipoAgenda tipoAgenda);

    boolean existsByDataAndTipoPeriodoAndTipoAula(LocalDate data, TipoPeriodo tipoPeriodo, TipoAula tipoAula);

    boolean existsByTipoAgendaAndDataAndTipoAula(TipoAgenda tipoAgenda, LocalDate data, TipoAula tipoAula);
}
