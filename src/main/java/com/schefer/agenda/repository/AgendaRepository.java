package com.schefer.agenda.repository;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.enums.TipoAula;
import com.schefer.agenda.enums.TipoPeriodo;
import com.schefer.agenda.model.Agenda;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    boolean existsByTipoAgendaAndDataAndTipoAulaAndIdNot(TipoAgenda tipoAgenda, LocalDate data, TipoAula tipoAula, Long id);

    List<Agenda> findByTipoAgendaAndDataGreaterThanEqual(TipoAgenda tipoAgenda, LocalDate data);

    boolean existsByTipoAgendaAndDataAndTipoAula(@NotNull TipoAgenda tipoAgenda, @NotNull @FutureOrPresent LocalDate data, @NotNull TipoAula tipoAula);
}
