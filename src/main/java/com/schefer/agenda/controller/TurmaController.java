package com.schefer.agenda.controller;

import com.schefer.agenda.dto.TurmaDTO;
import com.schefer.agenda.dto.TurmaRequestDTO;
import com.schefer.agenda.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    // METODOS GET

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> exibirTurma() {
        return ResponseEntity.ok(turmaService.exibirTurma());
    }

    // METODOS POST

    @PostMapping
    public ResponseEntity<TurmaDTO> criarTurma(@RequestBody @Valid TurmaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaService.salvarTurma(dto));
    }
}
