package com.schefer.agenda.controller;

import com.schefer.agenda.dto.MateriaDTO;
import com.schefer.agenda.dto.MateriaRequestDTO;
import com.schefer.agenda.service.MateriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    // METODOS GET

    @GetMapping
    public ResponseEntity<List<MateriaDTO>> exibirMateria() {
        return ResponseEntity.ok(materiaService.exibirMateria());
    }

    // METODOS POST

    @PostMapping
    public ResponseEntity<MateriaDTO> criarMateria(@RequestBody @Valid MateriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.salvarMateria(dto));
    }
}
