package com.schefer.agenda.dto;

import com.schefer.agenda.enums.TipoPeriodo;

public record TurmaDTO(
        Long id,
        TipoPeriodo periodo,
        Integer serie,
        Integer turma
) {
}
