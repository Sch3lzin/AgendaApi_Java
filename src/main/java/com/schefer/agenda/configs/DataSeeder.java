package com.schefer.agenda.configs;

import com.schefer.agenda.enums.TipoUsuario;
import com.schefer.agenda.model.Professor;
import com.schefer.agenda.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    /**
     * Roda na inicialização do projeto e insere um usuário ADMIN padrão
     * se não existir nenhum no banco. Evita o ciclo de precisar de auth
     * pra criar o primeiro usuário.
     */
    @Bean
    public CommandLineRunner seedAdmin(ProfessorRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            boolean existeAdmin = repository.existsByTipoUsuario(TipoUsuario.ADMIN);

            if (!existeAdmin) {
                String senhaCriptografada = passwordEncoder.encode("admin123");
                Professor admin = new Professor("admin", senhaCriptografada, TipoUsuario.ADMIN);
                repository.save(admin);
                System.out.println("Usuário admin criado — troque a senha após o primeiro login!");
            }
        };
    }
}