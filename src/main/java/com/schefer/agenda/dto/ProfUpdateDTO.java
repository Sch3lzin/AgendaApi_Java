package com.schefer.agenda.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfUpdateDTO(
        @NotBlank String name
) {
}
