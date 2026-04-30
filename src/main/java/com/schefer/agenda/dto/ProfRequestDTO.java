package com.schefer.agenda.dto;

import jakarta.validation.constraints.NotBlank;

// ProfRequestDTO
public record ProfRequestDTO(
        @NotBlank String name  // @NotBlank é melhor que @NotNull para String, evita " " também
) {}
