package com.schefer.agenda.controller;

import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.dto.ProfRequestDTO;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.service.ProfessorService;
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
    public List<ProfDTO> exibirProfessores() {
        return professorService.exibirProfessores();
    }

    @PostMapping("/criar")
    public Professor criarProfessor(@RequestBody ProfRequestDTO dto) {
        return professorService.salvarProfessor(dto);
    }
}
