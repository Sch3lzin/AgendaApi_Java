package com.schefer.agenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MateriaRequestDTO(
        @NotBlank String materia
) {}
