package com.schefer.agenda.dto;

import jakarta.validation.constraints.NotBlank;

public record MateriaRequestDTO(
        @NotBlank String materia
) {}
