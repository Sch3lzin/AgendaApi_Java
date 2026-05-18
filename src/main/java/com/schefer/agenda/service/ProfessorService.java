package com.schefer.agenda.service;

import com.schefer.agenda.dto.ProfDTO;
import com.schefer.agenda.dto.ProfRequestDTO;
import com.schefer.agenda.dto.ProfUpdateDTO;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final VerificarDados verificarDados;

    public ProfessorService(ProfessorRepository repository, PasswordEncoder passwordEncoder, VerificarDados verificarDados) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.verificarDados = verificarDados;
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

    /**
     * Persiste um novo professor com a senha encriptada via bcrypt.
     * A senha nunca é salva em texto puro no banco.
     */
    public ProfDTO salvarProfessor(ProfRequestDTO dto) {
        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        Professor professor = new Professor(dto.name(), senhaCriptografada, dto.permissao());
        Professor salvo = repository.save(professor);
        return new ProfDTO(salvo.getId(), salvo.getName());
    }

    /** Remove um professor pelo ID; lança exceção se não encontrado */
    public ResponseEntity<String> deletarProfessor(@Valid Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Professor não encontrado!");
        }

        repository.deleteById(id);
        return ResponseEntity.ok("Professor deletado com sucesso!");
    }

    /**
     * Atualiza dados básicos de um professor pelo ID.
     * Lança exceção se não encontrado.
     */
    public ResponseEntity<String> atualizarProfessor(@Valid Long id, ProfRequestDTO dto) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado!"));

        String senhaCriptografada = passwordEncoder.encode(dto.senha());
        professor.atualizarDados(dto.name(), senhaCriptografada, dto.permissao());
        repository.save(professor);

        return ResponseEntity.ok("Professor atualizado com sucesso!");
    }

    /**
     * Atualiza dados gerais de um professor pelo id
     * Lança exceção se não encontrado
     */
    public ResponseEntity<String> atualizarNomeProfessor(@Valid Long id, @Valid ProfUpdateDTO dto) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado!"));

        verificarDados.verificarPermissaoProfessor(id);

        professor.atualizarDadosBasicos(dto.name());
        repository.save(professor);

        return ResponseEntity.ok("Professor atualizado com sucesso!");
    }
}