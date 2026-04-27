package com.schefer.agenda.controller;

import com.schefer.agenda.dto.TurmaDTO;
import com.schefer.agenda.dto.TurmaRequestDTO;
import com.schefer.agenda.model.Turma;
import com.schefer.agenda.service.TurmaService;
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
    public List<TurmaDTO> exibirTurma() {
        return turmaService.exibirTurma();
    }

    @PostMapping("/criar")
    public Turma criarTurma(@RequestBody TurmaRequestDTO dto) {
        return turmaService.salvarTurma(dto);
    }
}
