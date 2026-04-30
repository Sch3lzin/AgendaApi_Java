package com.schefer.agenda.service;

import com.schefer.agenda.dto.TurmaDTO;
import com.schefer.agenda.dto.TurmaRequestDTO;
import com.schefer.agenda.model.Turma;
import com.schefer.agenda.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository repository;
    private final VerificarDados verificarDados;

    public TurmaService(TurmaRepository repository, VerificarDados verificarDados) {
        this.repository = repository;
        this.verificarDados = verificarDados;
    }

    private List<TurmaDTO> converteDados(List<Turma> turma) {
        return turma.stream()
                .map(t -> new TurmaDTO(t.getId(), t.getTipoPeriodo(), t.getSerie(), t.getTurma()))
                .toList();
    }

    public List<TurmaDTO> exibirTurma() {
        return converteDados(repository.findAll());
    }

    public TurmaDTO salvarTurma(TurmaRequestDTO dto) {
        VerificarDados.DadosVerificarTurma dados = verificarDados.verificarExisteTurma(dto);

        Turma turma = new Turma(dados.turma().getTipoPeriodo(), dados.turma().getSerie(), dados.turma().getTurma());
        Turma salva = repository.save(turma);
        return new TurmaDTO(salva.getId(), salva.getTipoPeriodo(), salva.getSerie(), salva.getTurma());
    }
}
