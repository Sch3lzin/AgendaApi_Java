package com.schefer.agenda.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private List<Agenda> agendamentos = new ArrayList<>();

    public Professor() {}

    public Professor(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Agenda> getAgendamentos() {
        return agendamentos;
    }
}
