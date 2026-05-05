package com.schefer.agenda.controller;

import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.dto.ProfRequestDTO;
import com.schefer.agenda.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<List<ProfDTO>> exibirProfessores() {
        return ResponseEntity.ok(professorService.exibirProfessores());
    }

    @PostMapping
    public ResponseEntity<ProfDTO> criarProfessor(@RequestBody @Valid ProfRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.salvarProfessor(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProfessor(@PathVariable @Valid Long id) {
        return professorService.deletarProfessor(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProfessor(@PathVariable @Valid Long id, @RequestBody ProfRequestDTO dto) {
        return professorService.atualizarProfessor(id, dto);
    }
}
