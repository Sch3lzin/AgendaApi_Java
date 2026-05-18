package com.schefer.agenda.repository;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.enums.TipoAula;
import com.schefer.agenda.model.Agenda;
import jakarta.validation.constraints.FutureOrPresent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    boolean existsByTipoAgendaAndDataAndTipoAulaAndIdNot(TipoAgenda tipoAgenda, LocalDate data, TipoAula tipoAula, Long id);

    List<Agenda> findByTipoAgendaAndDataGreaterThanEqual(TipoAgenda tipoAgenda, LocalDate data);

    boolean existsByTipoAgendaAndDataAndTipoAula(TipoAgenda tipoAgenda, @FutureOrPresent LocalDate data, TipoAula tipoAula);
}
