package com.schefer.agenda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schefer.agenda.dto.ProfResponseDTO;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipoUsuario.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public ProfResponseDTO exibirDados() {
        return new ProfResponseDTO(getId(), getName());
    }
}