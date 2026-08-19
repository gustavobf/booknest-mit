package br.edu.infnet.gustavo_figueiredo_api.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.*;

import java.util.*;

public class Usuario implements Entidade {
    @Schema(description = "ID do usuário", example = "1")
    private Integer id;
    @Schema(description = "Nome do usuário", example = "João Silva")
    private String nome;
    @Schema(description = "E-mail do usuário", example = "joao.silva@biblioteca.com")
    private String email;
    @Schema(description = "Matrícula do usuário", example = "MAT2025001")
    private String matricula;
    @Schema(description = "Indica se o usuário está ativo", example = "true")
    private Boolean ativo;
    @JsonIgnore
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
