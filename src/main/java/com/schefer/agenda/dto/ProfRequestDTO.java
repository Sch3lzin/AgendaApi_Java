package com.schefer.agenda.dto;

import com.schefer.agenda.enums.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfRequestDTO(
        @NotBlank String name,  // @NotBlank rejeita tanto null quanto strings em branco
        @NotBlank String senha,
        @NotNull TipoUsuario permisao
        ) {}
