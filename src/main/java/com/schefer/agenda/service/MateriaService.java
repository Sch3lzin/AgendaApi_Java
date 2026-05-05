package com.schefer.agenda.service;

import com.schefer.agenda.dto.MateriaDTO;
import com.schefer.agenda.dto.MateriaRequestDTO;
import com.schefer.agenda.model.Materia;
import com.schefer.agenda.repository.MateriaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository repository;
    private final VerificarDados verificarDados;

    public MateriaService(MateriaRepository repository, VerificarDados verificarDados) {
        this.repository = repository;
        this.verificarDados = verificarDados;
    }

    /** Converte entidades Materia para o DTO de resposta */
    private List<MateriaDTO> converteDados(List<Materia> materia) {
        return materia.stream()
                .map(m -> new MateriaDTO(m.getId(), m.getMateria()))
                .toList();
    }

    /** Retorna todas as matérias cadastradas */
    public List<MateriaDTO> exibirMateria() {
        return converteDados(repository.findAll());
    }

    /**
     * Persiste uma nova matéria após validar que não existe outra
     * com o mesmo nome no banco.
     */
    public MateriaDTO salvarMateria(MateriaRequestDTO dto) {
        VerificarDados.DadosVerificarMateria dados = verificarDados.verificarMateria(dto);

        Materia materia = new Materia(dados.materia().getMateria());
        Materia salva = repository.save(materia);
        return new MateriaDTO(salva.getId(), salva.getMateria());
    }

    /** Remove uma matéria pelo ID; lança exceção se não encontrada */
    public ResponseEntity<String> deletarMateria(@Valid Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("Materia não encontrado");
        }

        repository.deleteById(id);
        return ResponseEntity.ok("Materia deletado com sucesso");
    }

    /**
     *  Atualiza dados de uma matéria, usa id para acha-la no banco;
     *  Lança exceção se não encontrada;
     *  Persiste dados após validar que não existe outra com o mesmo
     *  nome no banco
     */
    public ResponseEntity<String> atualizarMateria(@Valid Long id, MateriaRequestDTO dto) {
        Materia materia = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Materia não encontrada!"));

        VerificarDados.DadosVerificarMateria dados = verificarDados.verificarMateria(dto);

        materia.atualizarDados(dados.materia().getMateria());

        repository.save(materia);

        return ResponseEntity.ok("Materia atulizada com sucesso!");
    }
}
