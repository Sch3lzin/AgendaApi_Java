package com.schefer.agenda.repository;

import com.schefer.agenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentosRepository extends JpaRepository<Agenda, Long> {
}
