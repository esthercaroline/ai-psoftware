package com.example.ai_psoftware.curso;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
public class Curso {

    @Id
    private String id;

    private String nome;
    private String descricao;
    private int numeroMaximoAlunos;
    private String professorCpf;

    private List<String> alunos = new ArrayList<>();

    public Curso() {}

    public Curso(String nome, String descricao, int numeroMaximoAlunos, String professorCpf) {
        this.nome = nome;
        this.descricao = descricao;
        this.numeroMaximoAlunos = numeroMaximoAlunos;
        this.professorCpf = professorCpf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNumeroMaximoAlunos() {
        return numeroMaximoAlunos;
    }

    public void setNumeroMaximoAlunos(int numeroMaximoAlunos) {
        this.numeroMaximoAlunos = numeroMaximoAlunos;
    }

    public String getProfessorCpf() {
        return professorCpf;
    }

    public void setProfessorCpf(String professorCpf) {
        this.professorCpf = professorCpf;
    }

    public List<String> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<String> alunos) {
        this.alunos = alunos;
    }
}

