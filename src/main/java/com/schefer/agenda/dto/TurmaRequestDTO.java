package com.schefer.agenda.dto;

import com.schefer.agenda.enums.TipoPeriodo;

public record TurmaRequestDTO(
        TipoPeriodo periodo,
        Integer serie,
        Integer turma
) {
}
