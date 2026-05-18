package com.schefer.agenda.controller;

import com.schefer.agenda.dto.TurmaDTO;
import com.schefer.agenda.dto.TurmaRequestDTO;
import com.schefer.agenda.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> exibirTurma() {
        return ResponseEntity.ok(turmaService.exibirTurma());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARIO')")
    @PostMapping
    public ResponseEntity<TurmaDTO> criarTurma(@RequestBody @Valid TurmaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaService.salvarTurma(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarTurma(@PathVariable @Valid Long id) {
        return turmaService.deletarTurma(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarTurma(@PathVariable @Valid Long id, @RequestBody @Valid TurmaRequestDTO dto) {
        return turmaService.atualizarTurma(id, dto);
    }
}
