package com.schefer.agenda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schefer.agenda.enums.TipoUsuario;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Professor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private List<Agenda> agendamentos = new ArrayList<>();

    public Professor() {}

    public Professor(String name, String senha, TipoUsuario tipoUsuario) {
        this.name = name;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public void atualizarDadosBasicos(String name) {
        this.name = name;
    }

    // --- UserDetails ---

    /** Retorna a role do usuário como authority do Spring Security */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.name()));
    }

    /** Spring Security usa esse método pra validar a senha */
    @Override
    public String getPassword() {
        return senha;
    }

    /** Spring Security usa esse método como identificador único do usuário */
    @Override
    public String getUsername() {
        return name;
    }

    // --- Getters ---

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public List<Agenda> getAgendamentos() {
        return agendamentos;
    }
}