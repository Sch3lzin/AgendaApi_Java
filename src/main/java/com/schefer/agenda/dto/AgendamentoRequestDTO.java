package com.schefer.agenda.dto;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.enums.TipoAula;
import com.schefer.agenda.enums.TipoPeriodo;

import java.time.LocalDate;

public record AgendamentoRequestDTO(
        Long turmaId,
        Long materiaId,
        Long professorId,
        TipoAula tipoAula,
        TipoAgenda tipoAgenda,
        TipoPeriodo tipoPeriodo,
        LocalDate data,
        String observacao
) {
}
