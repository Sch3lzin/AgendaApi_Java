package com.schefer.agenda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materia;

    public Materia() {}

    public Materia(String materia) {
        this.materia = materia;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return materia;
    }
}
