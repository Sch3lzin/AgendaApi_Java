package com.schefer.agenda.controller;

import com.schefer.agenda.dto.MateriaDTO;
import com.schefer.agenda.dto.MateriaRequestDTO;
import com.schefer.agenda.service.MateriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }


    @GetMapping
    public ResponseEntity<List<MateriaDTO>> exibirMateria() {
        return ResponseEntity.ok(materiaService.exibirMateria());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARIO')")
    @PostMapping
    public ResponseEntity<MateriaDTO> criarMateria(@RequestBody @Valid MateriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.salvarMateria(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarMateria(@PathVariable @Valid Long id) {
        return materiaService.deletarMateria(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarMateria(@PathVariable @Valid Long id, @RequestBody @Valid MateriaRequestDTO dto) {
        return materiaService.atualizarMateria(id, dto);
    }
}
