package com.schefer.agenda.dto;

import com.schefer.agenda.enums.TipoPeriodo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// TurmaRequestDTO
public record TurmaRequestDTO(
        @NotNull TipoPeriodo periodo,
        @NotNull @Min(1) @Max(9) Integer serie,
        @NotNull @Min(1) @Max(10) Integer turma
) {}