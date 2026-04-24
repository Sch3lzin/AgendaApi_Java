package com.schefer.agenda.model;

import com.schefer.agenda.enums.TipoPeriodo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPeriodo tipoPeriodo;

    @Column(nullable = false)
    @NotNull
    private Integer serie;

    @Column(nullable = false)
    @NotNull
    private Integer turma;

    public Turma() {}

    public Turma(TipoPeriodo tipoPeriodo, Integer serie, Integer turma) {
        this.tipoPeriodo = tipoPeriodo;
        this.serie = serie;
        this.turma = turma;
    }

    public TipoPeriodo getTipoPeriodo() {
        return tipoPeriodo;
    }

    public Long getId() {
        return id;
    }

    public Integer getSerie() {
        return serie;
    }

    public Integer getTurma() {
        return turma;
    }
}
