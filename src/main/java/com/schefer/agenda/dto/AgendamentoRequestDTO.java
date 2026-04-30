package com.schefer.agenda.dto;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.enums.TipoAula;
import com.schefer.agenda.enums.TipoPeriodo;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

// AgendamentoRequestDTO
public record AgendamentoRequestDTO(
        @NotNull Long turmaId,
        @NotNull Long materiaId,
        @NotNull Long professorId,
        @NotNull TipoAula tipoAula,
        @NotNull TipoAgenda tipoAgenda,
        @NotNull TipoPeriodo tipoPeriodo,
        @NotNull @FutureOrPresent LocalDate data,
        @Size(max = 255) String observacao  // opcional, sem @NotNull
) {}
