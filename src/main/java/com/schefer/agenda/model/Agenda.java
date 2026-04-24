package com.schefer.agenda.model;

import com.schefer.agenda.enums.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @Enumerated(EnumType.STRING)
    private TipoAula tipoAula;

    @Enumerated(EnumType.STRING)
    private TipoAgenda tipoAgenda;

    @Enumerated(EnumType.STRING)
    private TipoPeriodo tipoPeriodo;

    private LocalDate data;
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    public Agenda() {}

    public Agenda(Turma turma, Materia materia, TipoAula tipoAula, TipoAgenda tipoAgenda, TipoPeriodo tipoPeriodo, LocalDate data, Professor professor, String observacao) {
        this.turma = turma;
        this.materia = materia;
        this.tipoAula = tipoAula;
        this.tipoAgenda = tipoAgenda;
        this.tipoPeriodo = tipoPeriodo;
        this.data = data;
        this.professor = professor;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public Turma getTurma() {
        return turma;
    }

    public Materia getMateria() {
        return materia;
    }

    public TipoAula getTipoAula() {
        return tipoAula;
    }

    public TipoAgenda getTipoAgenda() {
        return tipoAgenda;
    }

    public TipoPeriodo getTipoPeriodo() {
        return tipoPeriodo;
    }

    public LocalDate getData() {
        return data;
    }

    public Professor getProfessor() {
        return professor;
    }

    public String getObservacao() {
        return observacao;
    }
}
