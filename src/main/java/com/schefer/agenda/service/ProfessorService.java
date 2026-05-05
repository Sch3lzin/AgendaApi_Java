package com.schefer.agenda.service;

import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.dto.ProfRequestDTO;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    /** Converte entidades Professor para o DTO de resposta */
    private List<ProfDTO> converteDados(List<Professor> prof) {
        return prof.stream()
                .map(p -> new ProfDTO(p.getId(), p.getName()))
                .toList();
    }

    /** Retorna todos os professores cadastrados */
    public List<ProfDTO> exibirProfessores() {
        return converteDados(repository.findAll());
    }

    /** Persiste um novo professor e retorna os dados salvos */
    public ProfDTO salvarProfessor(ProfRequestDTO dto) {
        Professor professor = new Professor(dto.name());
        Professor salvo = repository.save(professor);
        return new ProfDTO(salvo.getId(), salvo.getName());
    }

    /** Remove um professor pelo ID; lança exceção se não encontrado */
    public ResponseEntity<String> deletarProfessor(@Valid Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalStateException("Professor não encontrado");
        }

        repository.deleteById(id);
        return ResponseEntity.ok("Professor deletado com sucesso");
    }

    /**
     *  Atualiza dados de um professor, usa id para acha-lo no banco;
     *  Lança exceção se não encontrada;
     *  Persiste dados após validar que não existe outro com o mesmo
     *  nome no banco
     */
    public ResponseEntity<String> atualizarProfessor(@Valid Long id, ProfRequestDTO dto) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado!"));

        professor.atualizarDados(dto.name());

        repository.save(professor);

        return ResponseEntity.ok("Professor atualizado com sucesso!");
    }
}
