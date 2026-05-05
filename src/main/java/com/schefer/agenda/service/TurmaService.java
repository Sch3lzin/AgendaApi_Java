package com.schefer.agenda.service;

import com.schefer.agenda.dto.TurmaDTO;
import com.schefer.agenda.dto.TurmaRequestDTO;
import com.schefer.agenda.model.Turma;
import com.schefer.agenda.repository.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    /** Converte entidades Turma para o DTO de resposta */
    private List<TurmaDTO> converteDados(List<Turma> turma) {
        return turma.stream()
                .map(t -> new TurmaDTO(t.getId(), t.getTipoPeriodo(), t.getSerie(), t.getTurma()))
                .toList();
    }

    /** Retorna todas as turmas cadastradas */
    public List<TurmaDTO> exibirTurma() {
        return converteDados(repository.findAll());
    }

    /**
     * Persiste uma nova turma após validar que não existe outra
     * com a mesma combinação de série e turma.
     */
    public TurmaDTO salvarTurma(TurmaRequestDTO dto) {
        VerificarDados.DadosVerificarTurma dados = verificarDados.verificarExisteTurma(dto);

        Turma turma = new Turma(
                dados.turma().getTipoPeriodo(),
                dados.turma().getSerie(),
                dados.turma().getTurma()
        );

        Turma salva = repository.save(turma);
        return new TurmaDTO(salva.getId(), salva.getTipoPeriodo(), salva.getSerie(), salva.getTurma());
    }

    /** Remove uma turma pelo ID; lança exceção se não encontrada */
    public ResponseEntity<String> deletarTurma(@Valid Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("Turma não encontrado");
        }

        repository.deleteById(id);
        return ResponseEntity.ok("Turma deletado com sucesso");
    }

    /**
     *  Atualiza dados de uma turma, usa id para acha-la no banco;
     *  lança exceção se não encontrada;
     *  Persiste dados após validar que não existe outra com os mesmos
     *  dados no banco
     */
    public ResponseEntity<String> atualizarTurma(@Valid Long id, TurmaRequestDTO dto) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Turma não encontrada!"));

        VerificarDados.DadosVerificarTurma dados = verificarDados.verificarExisteTurma(dto);

        turma.atualizarDados(
                dados.turma().getTipoPeriodo(),
                dados.turma().getSerie(),
                dados.turma().getTurma()
        );

        repository.save(turma);
        return ResponseEntity.ok("Turma atualizada com sucesso!");
    }
}
