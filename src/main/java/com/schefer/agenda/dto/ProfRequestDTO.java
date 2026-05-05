package com.schefer.agenda.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfRequestDTO(
        @NotBlank String name  // @NotBlank rejeita tanto null quanto strings em branco
) {}
