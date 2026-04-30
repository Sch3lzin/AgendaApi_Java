package com.schefer.agenda.dto;

import jakarta.validation.constraints.NotBlank;

// MateriaRequestDTO
public record MateriaRequestDTO(
        @NotBlank String materia
) {}