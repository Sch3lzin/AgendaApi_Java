package com.schefer.agenda.dto;

import com.schefer.agenda.enums.TipoAgenda;
import com.schefer.agenda.enums.TipoAula;
import com.schefer.agenda.enums.TipoPeriodo;

import java.time.LocalDate;

public record AgendaDTO(Long id,
                        TurmaResponseDTO turma,
                        MateriaResponseDTO materia,
                        TipoAula tipoAula,
                        TipoAgenda tipoAgenda,
                        TipoPeriodo tipoPeriodo,
                        LocalDate data,
                        ProfResponseDTO professor,
                        String observacao) {
}
