package com.schefer.agenda.repository;

import com.schefer.agenda.model.Materia;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    boolean existsByMateria(@NotBlank String materia);
}
