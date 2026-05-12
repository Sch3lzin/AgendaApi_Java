package com.schefer.agenda.service;

import com.schefer.agenda.controller.AuthController.LoginRequest;
import com.schefer.agenda.controller.AuthController.LoginResponse;
import com.schefer.agenda.exception.CredenciaisInvalidasException;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.repository.ProfessorRepository;
import com.schefer.agenda.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ProfessorRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(ProfessorRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Autentica o usuário pelo ID e senha.
     * Retorna um token JWT válido por 8 horas se as credenciais estiverem corretas.
     */
    public LoginResponse login(LoginRequest request) {
        Professor professor = repository.findById(request.id()).orElse(null);

        // roda o BCrypt mesmo quando o usuário não existe
        // isso equaliza o tempo de resposta nos dois casos
        String senhaParaComparar = professor != null ? professor.getPassword() : "$2a$10$dummy.hash.para.evitar.timing.attack.xxxxxxxxxxxxxxxxxx";
        boolean senhaCorreta = passwordEncoder.matches(request.senha(), senhaParaComparar);

        if (professor == null || !senhaCorreta) {
            throw new CredenciaisInvalidasException();
        }

        String token = jwtUtil.gerarToken(
                professor.getId(),
                "ROLE_" + professor.getTipoUsuario().name()
        );

        return new LoginResponse(token);
    }
}