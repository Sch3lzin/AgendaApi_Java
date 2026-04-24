package com.schefer.agenda.dto;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.enums.TipoAula;
import com.schefer.agenda.enums.TipoPeriodo;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.model.Turma;

import java.time.LocalDate;

public record AgendaDTO(Long id,
                        Turma turma,
                        TipoAula tipoAula,
                        TipoAgenda tipoAgenda,
                        TipoPeriodo tipoPeriodo,
                        LocalDate data,
                        Professor professor) {
}
