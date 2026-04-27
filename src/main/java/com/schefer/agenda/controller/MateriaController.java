package com.schefer.agenda.controller;

import com.schefer.agenda.dto.MateriaDTO;
import com.schefer.agenda.dto.MateriaRequestDTO;
import com.schefer.agenda.model.Materia;
import com.schefer.agenda.repository.MateriaRepository;
import com.schefer.agenda.service.MateriaService;
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
    public List<MateriaDTO> exibirMateria() {
        return materiaService.exibirMateria();
    }

    @PostMapping("/criar")
    public Materia criarMateria(@RequestBody MateriaRequestDTO dto) {
        return materiaService.salvarMateria(dto);
    }
}
