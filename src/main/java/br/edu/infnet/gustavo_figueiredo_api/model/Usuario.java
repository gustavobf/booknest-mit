package br.edu.infnet.gustavo_figueiredo_api.model;

import java.util.*;

public class Usuario implements Entidade {
    private Integer id;
    private String nome;
    private String email;
    private String matricula;
    private Boolean ativo;
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    public Usuario () {
    }

    public Usuario (Integer id, String nome, String email, String matricula, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.ativo = ativo;
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    @Override
    public String getNome () {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getMatricula () {
        return matricula;
    }

    public void setMatricula (String matricula) {
        this.matricula = matricula;
    }

    public Boolean getAtivo () {
        return ativo;
    }

    public void setAtivo (Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Emprestimo> getEmprestimos () {
        return emprestimos;
    }

    public void adicionarEmprestimo (Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

    public long getEmprestimosEmAberto () {
        return emprestimos.stream().filter(emprestimo -> !emprestimo.estaDevolvido()).count();
    }

    @Override
    public void exibir () {
        System.out.println(this);
    }

    @Override
    public String toString () {
        return "Usuario{" + "id=" + id + ", nome='" + nome + '\'' + ", email='" + email + '\'' + ", matricula='" + matricula + '\'' + ", ativo=" + ativo + ", emprestimos=" + emprestimos.size() + ", emAberto=" + getEmprestimosEmAberto() + '}';
    }
}
