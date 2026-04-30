package com.schefer.agenda.repository;

import com.schefer.agenda.model.Turma;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    boolean existsBySerieAndTurma(@NotNull @Min(1) @Max(9) Integer serie, @NotNull @Min(1) @Max(10) Integer turma);
}
