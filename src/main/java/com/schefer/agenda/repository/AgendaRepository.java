package com.schefer.agenda.repository;

import com.schefer.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query(value = "SELECT * FROM agenda WHERE tipo_agenda = 'SALA_INFORMATICA'", nativeQuery = true)
    List<Agenda> exibirAgendaInformatica();

    @Query(value = "SELECT * FROM agenda WHERE tipo_agenda = 'AUDITORIO'", nativeQuery = true)
    List<Agenda> exibirAgendaAuditorio();

    @Query(value = "SELECT * FROM agenda WHERE tipo_agenda = 'TABLET'", nativeQuery = true)
    List<Agenda> exibirAgendaTablet();
}
